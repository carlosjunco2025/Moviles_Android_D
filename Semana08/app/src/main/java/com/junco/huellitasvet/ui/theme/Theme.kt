package com.junco.huellitasvet.ui.theme

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.junco.huellitasvet.model.Rol

private val FormasVet = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

/** Paleta clara u oscura segun el perfil: dueño = verde bosque, veterinario = indigo. */
@Composable
fun VetTheme(rol: Rol, oscuro: Boolean, content: @Composable () -> Unit) {
    val dueno = rol == Rol.CLIENTE
    val esquema = if (oscuro) {
        darkColorScheme(
            primary = if (dueno) Color(0xFF4FD6B0) else Color(0xFFA5B4FC),
            onPrimary = Color(0xFF05201A),
            primaryContainer = if (dueno) Color(0xFF0B4A44) else Color(0xFF26356F),
            onPrimaryContainer = if (dueno) MentaClaro else IndigoClaro,
            secondary = Color(0xFFFF9C82),
            tertiary = if (dueno) VerdeMenta else IndigoVivo,
            background = if (dueno) Color(0xFF0C1412) else Color(0xFF0D1120),
            onBackground = Color(0xFFE6EEEB),
            surface = if (dueno) Color(0xFF131E1B) else Color(0xFF151B30),
            onSurface = Color(0xFFE6EEEB),
            surfaceVariant = if (dueno) Color(0xFF1B2B27) else Color(0xFF1E2744),
            onSurfaceVariant = Color(0xFFB4C2BD),
            outline = Color(0xFF4F605A)
        )
    } else {
        lightColorScheme(
            primary = if (dueno) VerdeBosque else IndigoProfundo,
            onPrimary = Color.White,
            primaryContainer = if (dueno) MentaClaro else IndigoClaro,
            onPrimaryContainer = if (dueno) Color(0xFF053B36) else Color(0xFF1A2766),
            secondary = Coral,
            secondaryContainer = CoralClaro,
            tertiary = if (dueno) VerdeMenta else IndigoVivo,
            background = if (dueno) FondoDueno else FondoVet,
            onBackground = Color(0xFF1B2623),
            surface = Color.White,
            onSurface = Color(0xFF1B2623),
            surfaceVariant = if (dueno) Color(0xFFE7F4EE) else Color(0xFFE9EDFF),
            onSurfaceVariant = Color(0xFF5B6B66),
            outline = Color(0xFFCCD8D3)
        )
    }

    // La barra de estado toma el color con que empieza el degradado, asi la cabecera se ve continua.
    val vista = LocalView.current
    if (!vista.isInEditMode) {
        SideEffect {
            val ventana = (vista.context as Activity).window
            ventana.statusBarColor = inicioDegradado(esquema).toArgb()
            WindowCompat.getInsetsController(ventana, vista).isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(colorScheme = esquema, typography = VetTypography, shapes = FormasVet, content = content)
}

/** Color inicial del degradado: en tema oscuro se usa el contenedor, que es mas profundo que primary. */
private fun inicioDegradado(c: ColorScheme): Color =
    if (c.background.luminance() < 0.5f) c.primaryContainer else c.primary

/** Degradado de marca para cabeceras y pantallas de bienvenida. */
@Composable
fun degradadoMarca(): Brush {
    val c = MaterialTheme.colorScheme
    return Brush.linearGradient(listOf(inicioDegradado(c), c.tertiary))
}
