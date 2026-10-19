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

/** Una atencion dentro de la linea de tiempo del historial clinico. */
@Composable
fun HistorialItem(
    fecha: String,
    servicio: String,
    diagnostico: String,
    peso: Double?,
    esUltimo: Boolean,
    modifier: Modifier = Modifier
) {
    val primario = MaterialTheme.colorScheme.primary
    val linea = MaterialTheme.colorScheme.outline
    Row(modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Canvas(Modifier.width(18.dp).fillMaxHeight()) {
            val x = size.width / 2
            if (!esUltimo) drawLine(linea, Offset(x, 20f), Offset(x, size.height), strokeWidth = 3f)
            drawCircle(primario.copy(alpha = 0.25f), radius = 12f, center = Offset(x, 20f))
            drawCircle(primario, radius = 7f, center = Offset(x, 20f))
        }
        Surface(
            Modifier.weight(1f).padding(bottom = 10.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 1.dp,
            border = BorderStroke(1.dp, linea.copy(alpha = 0.5f))
        ) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(servicio, fontWeight = FontWeight.Bold)
                    Text(Formato.fechaCorta(fecha), color = primario, style = MaterialTheme.typography.labelMedium)
                }
                Text(diagnostico, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (peso != null) {
                    Text("Peso: ${Formato.peso(peso)}", style = MaterialTheme.typography.labelMedium, color = primario)
                }
            }
        }
    }
}
