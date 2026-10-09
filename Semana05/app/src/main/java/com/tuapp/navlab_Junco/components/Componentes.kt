package com.tuapp.navlab_Junco.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.tuapp.navlab_Junco.ui.theme.MoradoPrincipal
import com.tuapp.navlab_Junco.ui.theme.TextoPrincipal

@Composable
fun IconosBarraEstado(oscuros: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = oscuros
        }
    }
}

@Composable
fun IconoMas(tamano: Dp, color: Color = MoradoPrincipal) {
    Box(
        modifier = Modifier.size(tamano),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val thickness = w * 0.2f
            // Barra vertical
            drawRect(
                color = color,
                topLeft = Offset((w - thickness) / 2f, 0f),
                size = Size(thickness, h)
            )
            // Barra horizontal
            drawRect(
                color = color,
                topLeft = Offset(0f, (h - thickness) / 2f),
                size = Size(w, thickness)
            )
        }
    }
}

@Composable
fun IconoCheck(tamano: Dp, color: Color) {
    Box(
        modifier = Modifier.size(tamano),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val strokeWidth = w * 0.14f
            val path = Path().apply {
                moveTo(w * 0.06f, h * 0.52f)
                lineTo(w * 0.38f, h * 0.84f)
                lineTo(w * 0.96f, h * 0.14f)
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Butt,
                    join = StrokeJoin.Miter
                )
            )
        }
    }
}

@Composable
fun BarraSuperiorBlanca(
    titulo: String,
    tamanoTitulo: TextUnit = 17.sp,
    onBack: (() -> Unit)? = null,
    accion: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 8.dp, top = 20.dp, bottom = 12.dp)
            .heightIn(min = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            Text(
                text = "←",
                fontSize = tamanoTitulo,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onBack()
                    }
                    .padding(end = 6.dp)
            )
        }
        Text(
            text = titulo,
            fontSize = tamanoTitulo,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal,
            modifier = Modifier.weight(1f)
        )
        if (accion != null) {
            accion()
        }
    }
}

@Composable
fun BotonMenu(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menú",
            tint = TextoPrincipal
        )
    }
}
