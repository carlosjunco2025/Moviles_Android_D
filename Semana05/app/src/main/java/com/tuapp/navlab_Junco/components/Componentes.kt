package com.tuapp.navlab_Junco.components

import android.app.Activity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun IconosBarraEstado(oscuros: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? Activity)?.window
        window?.let {
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = oscuros
        }
    }
}

@Composable
fun IconoMancuerna(ancho: Dp, alto: Dp, color: Color = VerdePrincipal) {
    Canvas(modifier = Modifier.size(width = ancho, height = alto)) {
        val w = size.width
        val h = size.height
        val barHeight = h * 0.4f
        val barY = (h - barHeight) / 2f
        val barStartX = w * 0.15f
        val barEndX = w * 0.85f

        // Barra central
        drawRect(
            color = color,
            topLeft = Offset(barStartX, barY),
            size = Size(barEndX - barStartX, barHeight)
        )

        // Dos discos
        val diskWidth = w * 0.17f
        val diskRadius = diskWidth * 0.25f

        // Disco izquierdo
        drawRoundRect(
            color = color,
            topLeft = Offset(0f, 0f),
            size = Size(diskWidth, h),
            cornerRadius = CornerRadius(diskRadius, diskRadius)
        )

        // Disco derecho
        drawRoundRect(
            color = color,
            topLeft = Offset(w - diskWidth, 0f),
            size = Size(diskWidth, h),
            cornerRadius = CornerRadius(diskRadius, diskRadius)
        )
    }
}

@Composable
fun IconoCheck(tamano: Dp, color: Color) {
    Canvas(modifier = Modifier.size(tamano)) {
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

@Composable
fun BarraSuperiorBlanca(
    titulo: String,
    tamanoTitulo: TextUnit = 17.sp,
    onBack: (() -> Unit)? = null
) {
    IconosBarraEstado(oscuros = true)
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
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
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

@Composable
fun BotonPrincipal(texto: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, bottom = 22.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal),
            elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = texto,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
