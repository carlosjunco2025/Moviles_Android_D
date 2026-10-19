package com.junco.huellitasvet.ui.components

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
import com.junco.huellitasvet.model.Horarios
import java.time.LocalDate

/** Chip de dia: abreviatura de la semana arriba y numero del dia abajo. */
@Composable
fun FechaChip(fecha: LocalDate, seleccionada: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colores = MaterialTheme.colorScheme
    val texto = if (seleccionada) colores.onPrimary else colores.onSurface
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = if (seleccionada) colores.primary else colores.surface,
        shadowElevation = if (seleccionada) 4.dp else 0.dp,
        border = if (seleccionada) null else BorderStroke(1.dp, colores.outline)
    ) {
        Column(
            Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(Formato.diaSemana(fecha), style = MaterialTheme.typography.labelSmall, color = texto)
            Text(fecha.dayOfMonth.toString(), fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleMedium, color = texto)
        }
    }
}

/** Chip de hora; si esta [ocupada] se ve gris, tachada y no se puede tocar. */
@Composable
fun HoraChip(
    hora: String,
    seleccionada: Boolean,
    ocupada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        enabled = !ocupada,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = when {
            ocupada -> colores.surfaceVariant
            seleccionada -> colores.primary
            else -> colores.surface
        },
        border = if (seleccionada || ocupada) null else BorderStroke(1.dp, colores.outline)
    ) {
        Text(
            text = hora,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            textDecoration = if (ocupada) TextDecoration.LineThrough else TextDecoration.None,
            color = when {
                ocupada -> colores.onSurfaceVariant.copy(alpha = 0.6f)
                seleccionada -> colores.onPrimary
                else -> colores.onSurface
            }
        )
    }
}

/**
 * Fila de dias + cuadricula de horarios (3 por fila). Lo usan "Pedir cita" y "Reprogramar".
 * [noDisponible] decide si una hora se deshabilita (ocupada o ya pasada).
 */
@Composable
fun SelectorFechaHora(
    dias: List<LocalDate>,
    fecha: LocalDate,
    hora: String?,
    noDisponible: (String) -> Boolean,
    onFecha: (LocalDate) -> Unit,
    onHora: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(dias) { dia ->
                FechaChip(dia, seleccionada = dia == fecha, onClick = { onFecha(dia) })
            }
        }
        Horarios.todos.chunked(3).forEach { fila ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                fila.forEach { h ->
                    HoraChip(
                        hora = h,
                        seleccionada = h == hora,
                        ocupada = noDisponible(h),
                        onClick = { onHora(h) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
