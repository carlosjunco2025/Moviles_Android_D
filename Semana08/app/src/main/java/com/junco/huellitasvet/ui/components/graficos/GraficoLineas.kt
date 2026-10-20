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
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.model.PuntoPeso

/**
 * Linea de evolucion del peso. Dibuja area suave bajo la linea, un punto por atencion
 * y, debajo, la fecha de la primera y la ultima medicion.
 */
@Composable
fun GraficoLineas(puntos: List<PuntoPeso>, modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    val cuadricula = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    if (puntos.size < 2) {
        Text(
            "Se necesitan al menos 2 atenciones con peso para dibujar la evolución.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier
        )
        return
    }
    val minimo = puntos.minOf { it.peso }
    val maximo = puntos.maxOf { it.peso }
    // Si todos los pesos son iguales se evita dividir entre cero
    val rango = (maximo - minimo).takeIf { it > 0.0 } ?: 1.0

    Column(modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Máx ${Formato.peso(maximo)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Mín ${Formato.peso(minimo)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Canvas(Modifier.fillMaxWidth().height(130.dp).padding(vertical = 8.dp)) {
            val margen = 10f
            val ancho = size.width - 2 * margen
            val alto = size.height - 2 * margen
            fun posicion(indice: Int, peso: Double) = Offset(
                x = margen + ancho * indice / (puntos.size - 1),
                y = margen + alto * (1f - ((peso - minimo) / rango).toFloat())
            )
            // Lineas guia horizontales
            for (k in 0..2) {
                val y = margen + alto * k / 2f
                drawLine(cuadricula, Offset(margen, y), Offset(size.width - margen, y), strokeWidth = 1.5f)
            }
            val trazo = Path()
            val relleno = Path()
            puntos.forEachIndexed { i, p ->
                val o = posicion(i, p.peso)
                if (i == 0) {
                    trazo.moveTo(o.x, o.y)
                    relleno.moveTo(o.x, size.height - margen)
                    relleno.lineTo(o.x, o.y)
                } else {
                    trazo.lineTo(o.x, o.y)
                    relleno.lineTo(o.x, o.y)
                }
            }
            relleno.lineTo(posicion(puntos.lastIndex, puntos.last().peso).x, size.height - margen)
            relleno.close()
            drawPath(relleno, Brush.verticalGradient(listOf(color.copy(alpha = 0.28f), Color.Transparent)))
            drawPath(trazo, color, style = Stroke(width = 6f, cap = StrokeCap.Round, join = StrokeJoin.Round))
            puntos.forEachIndexed { i, p ->
                val o = posicion(i, p.peso)
                drawCircle(Color.White, radius = 11f, center = o)
                drawCircle(color, radius = 7f, center = o)
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(Formato.fechaDiaMes(puntos.first().fecha), style = MaterialTheme.typography.labelSmall)
            Text(Formato.fechaDiaMes(puntos.last().fecha), style = MaterialTheme.typography.labelSmall)
        }
    }
}
