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

/**
 * Tarjeta de cita. Recibe textos simples y no el objeto de Room,
 * para que el componente no conozca la capa de datos.
 */
@Composable
fun CitaCard(
    servicio: String,
    icono: String,
    detalle: String,
    estado: EstadoCita,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    acciones: @Composable () -> Unit = {}
) {
    val color = colorEstado(estado)
    TarjetaVet(modifier = modifier, onClick = onClick) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Franja lateral con el color del estado
            Box(
                Modifier
                    .width(5.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(iconoServicio(icono), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(servicio, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                EstadoCitaChip(estado)
                acciones()
            }
        }
    }
}
