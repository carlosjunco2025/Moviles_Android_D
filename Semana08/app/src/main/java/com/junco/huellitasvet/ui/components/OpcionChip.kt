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

/** Chip de seleccion simple (especie, sexo, duracion, filtros). */
@Composable
fun OpcionChip(
    texto: String,
    seleccionada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = if (seleccionada) colores.primary else colores.surface,
        border = if (seleccionada) null else BorderStroke(1.dp, colores.outline)
    ) {
        Text(
            texto,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.labelLarge,
            color = if (seleccionada) colores.onPrimary else colores.onSurface
        )
    }
}
