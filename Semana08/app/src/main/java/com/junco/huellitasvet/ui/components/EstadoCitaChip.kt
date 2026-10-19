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
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.ui.theme.ColorAtendida
import com.junco.huellitasvet.ui.theme.ColorCancelada
import com.junco.huellitasvet.ui.theme.ColorConfirmada
import com.junco.huellitasvet.ui.theme.ColorPendiente

fun colorEstado(estado: EstadoCita): Color = when (estado) {
    EstadoCita.PENDIENTE -> ColorPendiente
    EstadoCita.CONFIRMADA -> ColorConfirmada
    EstadoCita.ATENDIDA -> ColorAtendida
    EstadoCita.CANCELADA -> ColorCancelada
}

fun etiquetaEstado(estado: EstadoCita): String = when (estado) {
    EstadoCita.PENDIENTE -> "Pendiente"
    EstadoCita.CONFIRMADA -> "Confirmada"
    EstadoCita.ATENDIDA -> "Atendida"
    EstadoCita.CANCELADA -> "Cancelada"
}

/** Pastilla con un punto de color y el nombre del estado. */
@Composable
fun EstadoCitaChip(estado: EstadoCita, modifier: Modifier = Modifier) {
    val color = colorEstado(estado)
    Surface(modifier = modifier, color = color.copy(alpha = 0.14f), shape = RoundedCornerShape(50)) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(7.dp).background(color, CircleShape))
            Spacer(Modifier.width(6.dp))
            Text(
                etiquetaEstado(estado), color = color, fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
