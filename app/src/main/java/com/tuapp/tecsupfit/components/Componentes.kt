package com.tuapp.tecsupfit.components

import android.app.Activity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
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
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.VerdePrincipal

@Composable
fun IconosBarraEstado(oscuros: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? Activity)?.window
        if (window != null) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = oscuros
        }
    }
}

@Composable
fun IconoMancuerna(ancho: Dp, alto: Dp, color: Color = VerdePrincipal) {
    Canvas(modifier = Modifier.size(width = ancho, height = alto)) {
        val width = size.width
        val height = size.height

        val barHeight = height * 0.4f
        val barLeft = width * 0.15f
        val barRight = width * 0.85f
        val barTop = (height - barHeight) / 2f

        drawRect(
            color = color,
            topLeft = Offset(barLeft, barTop),
            size = Size(barRight - barLeft, barHeight)
        )

        val diskWidth = width * 0.17f
        val cornerRadius = CornerRadius(diskWidth * 0.25f, diskWidth * 0.25f)

        drawRoundRect(
            color = color,
            topLeft = Offset(0f, 0f),
            size = Size(diskWidth, height),
            cornerRadius = cornerRadius
        )

        drawRoundRect(
            color = color,
            topLeft = Offset(width - diskWidth, 0f),
            size = Size(diskWidth, height),
            cornerRadius = cornerRadius
        )
    }
}

@Composable
fun IconoCheck(tamano: Dp, color: Color) {
    Canvas(modifier = Modifier.size(tamano)) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(width * 0.06f, height * 0.52f)
            lineTo(width * 0.38f, height * 0.84f)
            lineTo(width * 0.96f, height * 0.14f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = width * 0.14f,
                cap = StrokeCap.Butt,
                join = StrokeJoin.Miter
            )
        )
    }
}

@Composable
fun BarraSuperiorBlanca(
    titulo: String,
    tamanoTitulo: TextUnit = 17.sp,
    onBack: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Fondo)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)
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
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onBack() }
                    .padding(end = 6.dp)
            )
        }
        Text(
            text = titulo,
            fontSize = tamanoTitulo,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )
    }
}
