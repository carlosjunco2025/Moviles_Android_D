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
import com.junco.huellitasvet.ui.theme.degradadoMarca

/**
 * Cabecera con degradado y esquinas inferiores redondeadas.
 * El [contenido] se dibuja debajo del titulo (por ejemplo un saludo o un buscador).
 */
@Composable
fun CabeceraDegradada(
    titulo: String,
    modifier: Modifier = Modifier,
    subtitulo: String? = null,
    acciones: @Composable RowScope.() -> Unit = {},
    contenido: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(degradadoMarca())
            .statusBarsPadding()
            .padding(start = 20.dp, end = 12.dp, top = 14.dp, bottom = 22.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                if (subtitulo != null) {
                    Text(subtitulo, color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.bodyMedium)
                }
                Text(
                    titulo, color = Color.White, fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            acciones()
        }
        contenido()
    }
}

/** Tarjeta blanca con borde suave: base visual de casi todas las listas. */
@Composable
fun TarjetaVet(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contenido: @Composable ColumnScope.() -> Unit
) {
    val forma = RoundedCornerShape(20.dp)
    val colores = MaterialTheme.colorScheme
    val borde = BorderStroke(1.dp, colores.outline.copy(alpha = 0.5f))
    if (onClick != null) {
        Surface(
            onClick = onClick, modifier = modifier.fillMaxWidth(), shape = forma,
            color = colores.surface, shadowElevation = 2.dp, border = borde
        ) { Column(Modifier.padding(14.dp), content = contenido) }
    } else {
        Surface(
            modifier = modifier.fillMaxWidth(), shape = forma,
            color = colores.surface, shadowElevation = 2.dp, border = borde
        ) { Column(Modifier.padding(14.dp), content = contenido) }
    }
}

/** Numero grande con una etiqueta debajo (panel del veterinario y resumenes). */
@Composable
fun DatoDestacado(
    valor: String,
    etiqueta: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = color.copy(alpha = 0.10f)
    ) {
        Column(Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
            Text(valor, color = color, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.headlineSmall)
            Text(etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
        }
    }
}

/** Titulo de seccion con una barrita de color a la izquierda. */
@Composable
fun TituloSeccion(texto: String, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(width = 4.dp, height = 18.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.secondary)
        )
        Spacer(Modifier.width(8.dp))
        Text(texto, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
    }
}
