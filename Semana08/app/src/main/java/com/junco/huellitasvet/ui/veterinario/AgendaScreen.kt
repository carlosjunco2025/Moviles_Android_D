package com.junco.huellitasvet.ui.veterinario

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
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.EstadoCitaChip
import com.junco.huellitasvet.ui.components.FechaChip
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.viewmodel.AgendaViewModel
import com.junco.huellitasvet.ui.components.CalendarioMes

/** Agenda diaria: elegir un dia y confirmar, atender o cancelar sus citas. */
@Composable
fun AgendaScreen(vm: AgendaViewModel, onAtender: (Int) -> Unit) {
    val fecha by vm.fechaSeleccionada.collectAsState()
    val citas by vm.citas.collectAsState()
    val mes by vm.mes.collectAsState()
    val diasConCitas by vm.diasConCitas.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                CabeceraDegradada(titulo = "Agenda", subtitulo = Formato.fechaCorta(fecha.toString()))
            }
            item {
                CalendarioMes(
                    mes = mes,
                    seleccionada = fecha,
                    diasConCitas = diasConCitas,
                    onSeleccionar = vm::seleccionar,
                    onAnterior = vm::mesAnterior,
                    onSiguiente = vm::mesSiguiente,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            if (citas.isEmpty()) {
                item {
                    Text(
                        "No hay citas para este día",
                        Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            items(citas, key = { it.cita.id }) { c ->
                ItemAgenda(
                    c,
                    onConfirmar = { vm.confirmar(c.cita) },
                    onAtender = { onAtender(c.cita.id) },
                    onCancelar = { vm.cancelar(c.cita) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

/** Una cita en la linea de tiempo: la hora a la izquierda y una tarjeta con los datos. */
@Composable
private fun ItemAgenda(
    c: CitaConDetalle,
    onConfirmar: () -> Unit,
    onAtender: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val estado = EstadoCita.valueOf(c.cita.estado)
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Text(
            c.cita.hora, Modifier.width(54.dp).padding(top = 16.dp),
            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.labelLarge
        )
        TarjetaVet(Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MascotaAvatar(c.mascota.mascota.especie, tamano = 46.dp)
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("${c.mascota.mascota.nombre} · ${c.servicio.nombre}", fontWeight = FontWeight.Bold)
                    Text(
                        "Dueño: ${c.mascota.dueno.nombre}", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    EstadoCitaChip(estado)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        when (estado) {
                            EstadoCita.PENDIENTE -> Button(onClick = onConfirmar) { Text("Confirmar") }
                            EstadoCita.CONFIRMADA -> Button(onClick = onAtender) { Text("Atender") }
                            else -> {}
                        }
                        if (estado.esActiva) {
                            TextButton(onClick = onCancelar) {
                                Text("Cancelar", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}
