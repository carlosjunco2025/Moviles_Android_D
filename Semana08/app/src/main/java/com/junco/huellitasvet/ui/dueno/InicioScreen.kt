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
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.EstadoCitaChip
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.iconoServicio
import com.junco.huellitasvet.viewmodel.InicioViewModel
import com.junco.huellitasvet.ui.components.TarjetaVet

/** Inicio del dueño: saludo, proxima cita, sus mascotas y accesos rapidos. */
@Composable
fun InicioScreen(
    nombre: String,
    vm: InicioViewModel,
    onVerMascota: (Int) -> Unit,
    onAgregar: () -> Unit,
    onVerTodas: () -> Unit,
    onPedirCita: () -> Unit
) {
    val mascotas by vm.mascotas.collectAsState()
    val proxima by vm.proximaCita.collectAsState()
    val control by vm.proximoControl.collectAsState()

    Column(Modifier.verticalScroll(rememberScrollState())) {
        CabeceraDegradada(
            titulo = "Hola, ${nombre.substringBefore(' ')} 👋",
            subtitulo = "¿Cómo están tus mascotas hoy?"
        )
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaProximaCita(proxima)
            // let evita el smart cast sobre una propiedad delegada (by collectAsState)
            control?.let { c -> AvisoControl(c) }

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.weight(1f)) { TituloSeccion("Mis mascotas") }
                TextButton(onClick = onVerTodas) { Text("Ver todas") }
            }
            Row(
                Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                mascotas.forEach { m -> TarjetaMascota(m) { onVerMascota(m.id) } }
                BotonAgregar(onAgregar)
            }

            TituloSeccion("Servicios")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AccesoRapido("Consulta", "heart", Modifier.weight(1f), onPedirCita)
                AccesoRapido("Vacunas", "syringe", Modifier.weight(1f), onPedirCita)
                AccesoRapido("Baño", "scissors", Modifier.weight(1f), onPedirCita)
            }

            VetButton("Pedir una cita", onClick = onPedirCita)
        }
    }
}

@Composable
private fun TarjetaProximaCita(proxima: CitaConDetalle?) {
    val textoSuave = Color.White.copy(alpha = 0.82f)
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.secondary,
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (proxima == null) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Próxima cita", color = textoSuave, style = MaterialTheme.typography.labelMedium)
                Text("Aún no tienes citas", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Próxima cita", color = textoSuave, style = MaterialTheme.typography.labelMedium)
                    Text(
                        "${proxima.mascota.mascota.nombre} · ${proxima.servicio.nombre}",
                        color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Filled.Schedule, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Text(
                            Formato.fechaHora(proxima.cita.fecha, proxima.cita.hora),
                            color = Color.White, fontWeight = FontWeight.SemiBold
                        )
                    }
                    EstadoCitaChip(EstadoCita.valueOf(proxima.cita.estado))
                }
                Surface(shape = CircleShape, color = Color.White) {
                    MascotaAvatar(proxima.mascota.mascota.especie, Modifier.padding(4.dp), tamano = 56.dp)
                }
            }
        }
    }
}

/** Recordatorio del proximo control que dejo indicado el veterinario. */
@Composable
private fun AvisoControl(c: CitaConDetalle) {
    TarjetaVet {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondaryContainer, modifier = Modifier.size(44.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.EventRepeat, null, tint = MaterialTheme.colorScheme.secondary)
                }
            }
            Column {
                Text("Próximo control de ${c.mascota.mascota.nombre}", fontWeight = FontWeight.Bold)
                Text(
                    c.cita.fechaControl?.let { Formato.fechaCorta(it) } ?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun TarjetaMascota(m: Mascota, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            Modifier.padding(vertical = 14.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MascotaAvatar(m.especie, tamano = 54.dp)
            Text(m.nombre, fontWeight = FontWeight.Bold, maxLines = 1)
            Text("Ver ficha", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun BotonAgregar(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(Icons.Filled.Add, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
            Text("Agregar", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun AccesoRapido(texto: String, icono: String, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
    ) {
        Column(
            Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(iconoServicio(icono), null, tint = MaterialTheme.colorScheme.primary)
            Text(texto, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
    }
}
