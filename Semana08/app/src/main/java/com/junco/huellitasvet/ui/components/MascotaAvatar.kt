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
import com.junco.huellitasvet.model.Especie

/** Carita dibujada con Canvas para perro, gato u otro animal. [especie] recibe Especie.name. */
@Composable
fun MascotaAvatar(especie: String, modifier: Modifier = Modifier, tamano: Dp = 56.dp) {
    val fondo = MaterialTheme.colorScheme.primaryContainer
    val aro = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
    Canvas(
        modifier
            .size(tamano)
            .clip(CircleShape)
            .border(2.dp, aro, CircleShape)
    ) {
        val w = size.width
        val h = size.height
        drawRect(fondo)
        val ojo = Color(0xFF2B2118)
        when (especie) {
            Especie.PERRO.name -> {
                val oreja = Color(0xFF8A5A2B)
                drawOval(oreja, Offset(w * 0.12f, h * 0.22f), Size(w * 0.24f, h * 0.46f))
                drawOval(oreja, Offset(w * 0.64f, h * 0.22f), Size(w * 0.24f, h * 0.46f))
                drawCircle(Color(0xFFB7793F), radius = w * 0.30f, center = Offset(w * 0.5f, h * 0.56f))
                drawOval(Color(0xFFF1D9B5), Offset(w * 0.36f, h * 0.58f), Size(w * 0.28f, h * 0.2f))
                drawCircle(ojo, w * 0.04f, Offset(w * 0.40f, h * 0.50f))
                drawCircle(ojo, w * 0.04f, Offset(w * 0.60f, h * 0.50f))
                drawOval(ojo, Offset(w * 0.44f, h * 0.60f), Size(w * 0.12f, h * 0.08f))
            }
            Especie.GATO.name -> {
                val pelo = Color(0xFF8E9AAB)
                val izquierda = Path().apply {
                    moveTo(w * 0.20f, h * 0.45f); lineTo(w * 0.24f, h * 0.14f); lineTo(w * 0.46f, h * 0.32f); close()
                }
                val derecha = Path().apply {
                    moveTo(w * 0.80f, h * 0.45f); lineTo(w * 0.76f, h * 0.14f); lineTo(w * 0.54f, h * 0.32f); close()
                }
                drawPath(izquierda, pelo)
                drawPath(derecha, pelo)
                drawCircle(pelo, radius = w * 0.31f, center = Offset(w * 0.5f, h * 0.57f))
                drawCircle(ojo, w * 0.04f, Offset(w * 0.40f, h * 0.53f))
                drawCircle(ojo, w * 0.04f, Offset(w * 0.60f, h * 0.53f))
                drawOval(Color(0xFFE88CA0), Offset(w * 0.46f, h * 0.62f), Size(w * 0.08f, h * 0.06f))
            }
            else -> {
                // Huella generica para otras especies
                val c = Color(0xFF6B7280)
                drawOval(c, Offset(w * 0.30f, h * 0.50f), Size(w * 0.40f, h * 0.32f))
                drawCircle(c, w * 0.08f, Offset(w * 0.28f, h * 0.40f))
                drawCircle(c, w * 0.08f, Offset(w * 0.42f, h * 0.28f))
                drawCircle(c, w * 0.08f, Offset(w * 0.58f, h * 0.28f))
                drawCircle(c, w * 0.08f, Offset(w * 0.72f, h * 0.40f))
            }
        }
    }
}

/** "PERRO" -> "Perro" */
fun etiquetaEspecie(especie: String): String = especie.lowercase().replaceFirstChar { it.uppercase() }

fun etiquetaSexo(sexo: String): String = sexo.lowercase().replaceFirstChar { it.uppercase() }
