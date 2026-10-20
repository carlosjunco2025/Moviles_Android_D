package com.junco.huellitasvet.ui.components.graficos

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

/** Barras verticales; la altura de cada una es proporcional al valor mayor. */
@Composable
fun GraficoBarras(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val base = MaterialTheme.colorScheme.primary
    val paleta = listOf(base, MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.secondary)
    val maximo = (datos.maxOfOrNull { it.valor } ?: 0f).coerceAtLeast(1f)
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        datos.forEachIndexed { i, dato ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(dato.valor.toInt().toString(), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                val color = colorDe(dato, i, paleta)
                Canvas(Modifier.width(46.dp).height(96.dp)) {
                    val alto = size.height * (dato.valor / maximo)
                    drawRoundRect(
                        brush = Brush.verticalGradient(listOf(color, color.copy(alpha = 0.55f))),
                        topLeft = Offset(0f, size.height - alto),
                        size = Size(size.width, alto),
                        cornerRadius = CornerRadius(14f, 14f)
                    )
                }
                Text(dato.etiqueta, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
