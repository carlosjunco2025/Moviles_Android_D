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
import androidx.compose.ui.text.input.KeyboardType
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.AppTopBar
import com.junco.huellitasvet.ui.components.HistorialItem
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.OpcionChip
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.VetTextField
import com.junco.huellitasvet.ui.components.etiquetaEspecie
import com.junco.huellitasvet.ui.components.etiquetaSexo
import com.junco.huellitasvet.ui.theme.degradadoMarca
import com.junco.huellitasvet.viewmodel.MascotaViewModel

/** Ficha de la mascota con pestañas: Datos, Historial clinico y (Fase 2) Vacunas. */
@Composable
fun FichaMascotaScreen(
    id: Int,
    vm: MascotaViewModel,
    onBack: () -> Unit,
    onPedirCita: (Int) -> Unit,
    onEditar: (Int) -> Unit,
    soloLectura: Boolean = false // el veterinario solo consulta
) {
    val flujo = remember(id) { vm.mascota(id) }
    val mascota by flujo.collectAsState(initial = null)
    val historial by vm.historial(id).collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val snackbar = remember { SnackbarHostState() }
    var pestana by remember { mutableIntStateOf(0) }
    var confirmarBorrado by remember { mutableStateOf(false) }

    LaunchedEffect(mensaje) {
        mensaje?.let { snackbar.showSnackbar(it); vm.limpiarMensaje() }
    }

    Scaffold(
        topBar = { AppTopBar(mascota?.nombre ?: "Mascota", onBack) },
        snackbarHost = { SnackbarHost(snackbar) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        val m = mascota ?: return@Scaffold
        Column(
            Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Tarjeta de presentacion con degradado
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(degradadoMarca())
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(shape = CircleShape, color = Color.White) {
                    MascotaAvatar(m.especie, Modifier.padding(4.dp), tamano = 76.dp)
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(m.nombre, color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                    Text(
                        "${etiquetaEspecie(m.especie)} · ${m.raza} · ${Formato.edad(m.anioNacimiento)} años",
                        color = Color.White.copy(alpha = 0.88f), style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "Peso actual: ${Formato.peso(m.peso)}",
                        color = Color.White, style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            if (!soloLectura) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { onPedirCita(m.id) }, Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                        Icon(Icons.Filled.CalendarMonth, null)
                        Text("  Pedir cita", fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(onClick = { onEditar(m.id) }, Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                        Icon(Icons.Filled.Edit, null)
                        Text("  Editar", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OpcionChip("Datos", pestana == 0, { pestana = 0 }, Modifier.weight(1f))
                OpcionChip("Historial", pestana == 1, { pestana = 1 }, Modifier.weight(1f))
            }

            when (pestana) {
                0 -> {
                    DatosMascota(m)
                    if (!soloLectura) {
                        VetButton("Eliminar mascota", onClick = { confirmarBorrado = true }, secundario = true, peligro = true)
                    }
                }
                1 -> {
                    if (historial.isEmpty()) {
                        Text(
                            "Aún no tiene atenciones",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    historial.forEachIndexed { i, c ->
                        HistorialItem(
                            fecha = c.cita.fecha,
                            servicio = c.servicio.nombre,
                            diagnostico = c.cita.diagnostico ?: "Sin diagnóstico",
                            peso = c.cita.pesoRegistrado,
                            esUltimo = i == historial.lastIndex
                        )
                    }
                }
            }
        }

        if (confirmarBorrado) {
            AlertDialog(
                onDismissRequest = { confirmarBorrado = false },
                title = { Text("Eliminar mascota") },
                text = { Text("¿Seguro que quieres eliminar a ${m.nombre}?") },
                confirmButton = {
                    TextButton(onClick = {
                        confirmarBorrado = false
                        vm.eliminar(m, onBack)
                    }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = { TextButton(onClick = { confirmarBorrado = false }) { Text("Cancelar") } }
            )
        }
    }
}

@Composable
private fun DatosMascota(m: Mascota) {
    TarjetaVet {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Fila("Especie", etiquetaEspecie(m.especie))
            Fila("Raza", m.raza.ifBlank { "—" })
            Fila("Sexo", etiquetaSexo(m.sexo))
            Fila("Año de nacimiento", m.anioNacimiento.toString())
            Fila("Peso", Formato.peso(m.peso))
        }
    }
}

@Composable
private fun Fila(etiqueta: String, valor: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(valor, fontWeight = FontWeight.SemiBold)
    }
}
