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

internal fun colorDe(dato: DatoGrafico, indice: Int, paleta: List<Color>): Color =
    if (dato.color == Color.Unspecified) paleta[indice % paleta.size] else dato.color

/** Dona con leyenda a la derecha; el total aparece en el centro. */
@Composable
fun GraficoDona(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    textoCentro: String = "citas"
) {
    val paleta = listOf(MaterialTheme.colorScheme.primary, Color(0xFFE08A00), Color(0xFF16A34A), Color(0xFF52606D), Color(0xFFDC2626))
    val total = datos.sumOf { it.valor.toDouble() }.toFloat()
    val vacio = MaterialTheme.colorScheme.outline
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(124.dp), contentAlignment = Alignment.Center) {
            Canvas(Modifier.size(114.dp)) {
                val grosor = 26f
                val lado = size.minDimension - grosor
                val esquina = Offset(grosor / 2, grosor / 2)
                if (total <= 0f) {
                    drawArc(vacio, 0f, 360f, false, esquina, Size(lado, lado), style = Stroke(grosor))
                } else {
                    var inicio = -90f
                    datos.forEachIndexed { i, dato ->
                        val barrido = dato.valor / total * 360f
                        if (barrido > 0f) {
                            drawArc(
                                colorDe(dato, i, paleta), inicio, barrido - 1.5f, false, esquina,
                                Size(lado, lado), style = Stroke(grosor, cap = StrokeCap.Butt)
                            )
                        }
                        inicio += barrido
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(total.toInt().toString(), fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleLarge)
                Text(textoCentro, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            datos.forEachIndexed { i, dato ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(10.dp).background(colorDe(dato, i, paleta), CircleShape))
                    Spacer(Modifier.width(8.dp))
                    Text(dato.etiqueta, Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                    Text(dato.valor.toInt().toString(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
