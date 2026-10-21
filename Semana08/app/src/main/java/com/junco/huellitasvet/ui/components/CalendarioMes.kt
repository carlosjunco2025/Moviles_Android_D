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
import java.time.LocalDate
import java.time.YearMonth

/**
 * Calendario de un mes. Los dias con citas llevan un punto debajo del numero.
 * La semana empieza el lunes (igual que Formato.diaSemana).
 */
@Composable
fun CalendarioMes(
    mes: YearMonth,
    seleccionada: LocalDate,
    diasConCitas: Set<LocalDate>,
    onSeleccionar: (LocalDate) -> Unit,
    onAnterior: () -> Unit,
    onSiguiente: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = MaterialTheme.colorScheme
    val hoy = LocalDate.now()
    // Huecos antes del dia 1: lunes = 0 ... domingo = 6
    val huecos = mes.atDay(1).dayOfWeek.value - 1
    val celdas: List<LocalDate?> = List(huecos) { null } + (1..mes.lengthOfMonth()).map { mes.atDay(it) }

    TarjetaVet(modifier) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onAnterior) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Mes anterior") }
            Text(
                Formato.mesAnio(mes).replaceFirstChar { it.uppercase() },
                Modifier.weight(1f), textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = onSiguiente) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Mes siguiente") }
        }
        Row(Modifier.fillMaxWidth()) {
            listOf("L", "M", "M", "J", "V", "S", "D").forEach { letra ->
                Text(
                    letra, Modifier.weight(1f), textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall, color = colores.onSurfaceVariant
                )
            }
        }
        celdas.chunked(7).forEach { semana ->
            Row(Modifier.fillMaxWidth().padding(top = 4.dp)) {
                semana.forEach { dia -> CeldaDia(dia, dia == seleccionada, dia == hoy, dia != null && dia in diasConCitas, onSeleccionar, Modifier.weight(1f)) }
                // La ultima semana se completa con huecos para que las columnas no se estiren
                repeat(7 - semana.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun CeldaDia(
    dia: LocalDate?,
    elegido: Boolean,
    esHoy: Boolean,
    conCitas: Boolean,
    onSeleccionar: (LocalDate) -> Unit,
    modifier: Modifier
) {
    if (dia == null) {
        Spacer(modifier.height(40.dp))
        return
    }
    val colores = MaterialTheme.colorScheme
    Column(
        modifier
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (elegido) colores.primary else Color.Transparent)
            .then(if (esHoy && !elegido) Modifier.border(1.5.dp, colores.primary, RoundedCornerShape(12.dp)) else Modifier)
            .clickable { onSeleccionar(dia) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            dia.dayOfMonth.toString(),
            color = if (elegido) colores.onPrimary else colores.onSurface,
            fontWeight = if (esHoy || elegido) FontWeight.Bold else FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            Modifier
                .size(5.dp)
                .background(
                    if (conCitas) (if (elegido) colores.onPrimary else colores.secondary) else Color.Transparent,
                    CircleShape
                )
        )
    }
}
