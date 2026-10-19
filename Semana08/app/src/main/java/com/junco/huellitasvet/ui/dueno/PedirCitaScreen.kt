package com.junco.huellitasvet.ui.dueno

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.platform.*
import androidx.compose.foundation.text.*
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.AppTopBar
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.SelectorFechaHora
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.iconoServicio
import com.junco.huellitasvet.viewmodel.PedirCitaViewModel

/** Pedir cita en cuatro pasos: mascota, servicio, fecha y hora. */
@Composable
fun PedirCitaScreen(
    vm: PedirCitaViewModel,
    onBack: () -> Unit,
    onCreada: () -> Unit
) {
    val mascotas by vm.mascotas.collectAsState()
    val servicios by vm.servicios.collectAsState()
    val mascotaId by vm.mascotaId.collectAsState()
    val servicioId by vm.servicioId.collectAsState()
    val fecha by vm.fecha.collectAsState()
    val hora by vm.hora.collectAsState()
    val ocupadas by vm.horasOcupadas.collectAsState()
    val puedeConfirmar by vm.puedeConfirmar.collectAsState()
    val acepta by vm.aceptaCitas.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(mensaje) {
        mensaje?.let { snackbar.showSnackbar(it); vm.limpiarMensaje() }
    }

    val mascotaElegida = mascotas.firstOrNull { it.id == mascotaId }
    val servicioElegido = servicios.firstOrNull { it.id == servicioId }

    Scaffold(
        topBar = { AppTopBar("Pedir cita", onBack) },
        snackbarHost = { SnackbarHost(snackbar) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!acepta) {
                Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.errorContainer) {
                    Text(
                        "La clínica no está recibiendo citas",
                        Modifier.padding(12.dp).fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold
                    )
                }
            }

            TituloSeccion("¿Quién viene?")
            if (mascotas.isEmpty()) {
                Text("Primero registra una mascota.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                mascotas.forEach { m ->
                    val elegida = m.id == mascotaId
                    Surface(
                        onClick = { vm.elegirMascota(m.id) },
                        shape = RoundedCornerShape(50),
                        color = if (elegida) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.5.dp, if (elegida) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                    ) {
                        Row(Modifier.padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                            MascotaAvatar(m.especie, tamano = 30.dp)
                            Text("  ${m.nombre}", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            TituloSeccion("Servicio")
            servicios.forEach { s ->
                val elegido = s.id == servicioId
                Surface(
                    onClick = { vm.elegirServicio(s.id) },
                    shape = RoundedCornerShape(18.dp),
                    color = if (elegido) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.5.dp, if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(iconoServicio(s.icono), null, tint = MaterialTheme.colorScheme.primary)
                        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                            Text(s.nombre, fontWeight = FontWeight.Bold)
                            Text(
                                "${s.duracionMin} min", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(Formato.soles(s.precio), fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            TituloSeccion("Fecha y hora")
            SelectorFechaHora(
                dias = vm.dias,
                fecha = fecha,
                hora = hora,
                noDisponible = { h -> h in ocupadas || vm.horaPasada(fecha, h) },
                onFecha = vm::elegirFecha,
                onHora = vm::elegirHora
            )

            // Resumen de lo elegido
            val horaElegida = hora
            if (mascotaElegida != null && servicioElegido != null && horaElegida != null) {
                Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            "${mascotaElegida.nombre} · ${servicioElegido.nombre}",
                            fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary
                        )
                        Text("${Formato.fechaCorta(fecha.toString())} · $horaElegida · ${Formato.soles(servicioElegido.precio)}")
                    }
                }
            }

            VetButton("Confirmar cita", onClick = { vm.confirmar(onCreada) }, enabled = puedeConfirmar)
        }
    }
}
