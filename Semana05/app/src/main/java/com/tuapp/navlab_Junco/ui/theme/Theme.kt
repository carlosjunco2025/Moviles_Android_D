package com.tuapp.navlab_Junco.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = MoradoPrincipal,
    onPrimary = Color.White,
    background = Fondo,
    surface = Fondo,
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal
)

@Composable
fun Semana05Theme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
