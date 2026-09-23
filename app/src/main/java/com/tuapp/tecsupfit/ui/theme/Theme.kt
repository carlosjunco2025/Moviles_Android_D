package com.tuapp.tecsupfit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    onPrimary = Blanco,
    background = Fondo,
    surface = Fondo,
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal
)

@Composable
fun TECSUPFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
