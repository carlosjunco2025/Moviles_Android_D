package com.junco.huellitasvet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.junco.huellitasvet.ui.theme.ColorCancelada

/** Boton a todo el ancho. [secundario] = solo borde; [peligro] lo pinta en rojo. */
@Composable
fun VetButton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    secundario: Boolean = false,
    peligro: Boolean = false
) {
    val forma = RoundedCornerShape(16.dp)
    val medidas = modifier.fillMaxWidth().height(52.dp)
    if (secundario) {
        val color = if (peligro) ColorCancelada else MaterialTheme.colorScheme.primary
        OutlinedButton(
            onClick = onClick, modifier = medidas, enabled = enabled, shape = forma,
            border = BorderStroke(1.5.dp, color),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = color)
        ) { Text(texto, fontWeight = FontWeight.Bold) }
    } else {
        Button(
            onClick = onClick, modifier = medidas, enabled = enabled, shape = forma,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
        ) { Text(texto, fontWeight = FontWeight.Bold) }
    }
}
