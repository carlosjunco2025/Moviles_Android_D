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
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.CitaCard
import com.junco.huellitasvet.ui.components.OpcionChip
import com.junco.huellitasvet.viewmodel.MisCitasViewModel

/** Citas del dueño en dos pestañas (Proximas / Historial) con cancelacion y reprogramacion. */
@Composable
fun MisCitasScreen(
    vm: MisCitasViewModel,
    onPedirCita: () -> Unit,
    onReprogramar: (Int) -> Unit
) {
    val proximas by vm.proximas.collectAsState()
    val historial by vm.historial.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    var pestana by remember { mutableIntStateOf(0) }
    var citaACancelar by remember { mutableStateOf<Cita?>(null) }
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(mensaje) {
        mensaje?.let { snackbar.showSnackbar(it); vm.limpiarMensaje() }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPedirCita,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ) { Icon(Icons.Filled.Add, contentDescription = "Pedir cita") }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(Modifier.padding(padding)) {
            CabeceraDegradada(titulo = "Mis citas", subtitulo = "${proximas.size} próximas")
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OpcionChip("Próximas", pestana == 0, { pestana = 0 }, Modifier.weight(1f))
                OpcionChip("Historial", pestana == 1, { pestana = 1 }, Modifier.weight(1f))
            }

            val lista = if (pestana == 0) proximas else historial
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (lista.isEmpty()) {
                    item {
                        Text(
                            if (pestana == 0) "No tienes citas próximas" else "Aún no tienes citas en el historial",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                items(lista, key = { it.cita.id }) { c ->
                    val estado = EstadoCita.valueOf(c.cita.estado)
                    val puedeCancelar = pestana == 0 && vm.puedeCancelar(c.cita)
                    CitaCard(
                        servicio = c.servicio.nombre,
                        icono = c.servicio.icono,
                        detalle = "${c.mascota.mascota.nombre} · ${Formato.fechaHora(c.cita.fecha, c.cita.hora)}",
                        estado = estado
                    ) {
                        if (puedeCancelar) {
                            Row {
                                TextButton(onClick = { onReprogramar(c.cita.id) }) {
                                    Text("Reprogramar", fontWeight = FontWeight.Bold)
                                }
                                TextButton(onClick = { citaACancelar = c.cita }) {
                                    Text("Cancelar", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else if (pestana == 0) {
                            Text(
                                "Ya no puedes modificar esta cita",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                if (pestana == 0 && proximas.isNotEmpty()) {
                    item {
                        Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.fillMaxWidth()) {
                            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Schedule, null, tint = MaterialTheme.colorScheme.primary)
                                Text("  Puedes cancelar hasta 2 horas antes", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }

        citaACancelar?.let { cita ->
            AlertDialog(
                onDismissRequest = { citaACancelar = null },
                title = { Text("Cancelar cita") },
                text = { Text("¿Seguro que quieres cancelar esta cita?") },
                confirmButton = {
                    TextButton(onClick = { vm.cancelar(cita); citaACancelar = null }) {
                        Text("Sí, cancelar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = { TextButton(onClick = { citaACancelar = null }) { Text("No") } }
            )
        }
    }
}
