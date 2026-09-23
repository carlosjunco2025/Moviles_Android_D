package com.tuapp.navlab_Junco.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PurpleMain,
    secondary = PurpleSecondary,
    background = Color(0xFF121016),
    surface = Color(0xFF1D1B20),
    onPrimary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PurpleMain,
    onPrimary = Color.White,
    secondary = PurpleSecondary,
    onSecondary = Color.White,
    background = BackgroundGeneral,
    surface = CardWhite,
    onSurface = TextPrimary,
    error = LogoutRed
)

@Composable
fun Semana05Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
