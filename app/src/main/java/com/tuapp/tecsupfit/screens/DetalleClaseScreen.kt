package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.tecsupfit.components.BarraSuperiorBlanca
import com.tuapp.tecsupfit.components.BotonPrincipal
import com.tuapp.tecsupfit.components.IconoMancuerna
import com.tuapp.tecsupfit.components.IconosBarraEstado
import com.tuapp.tecsupfit.data.DatosClases
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.TextoCuerpo
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.VerdeClaro

@Composable
fun DetalleClaseScreen(
    claseId: Int,
    onBack: () -> Unit,
    onReservar: () -> Unit
) {
    IconosBarraEstado(oscuros = true)

    val clase = DatosClases.buscar(claseId)

    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Detalle de clase",
                onBack = onBack
            )
        },
        bottomBar = {
            BotonPrincipal(
                texto = "Reservar cupo",
                onClick = onReservar
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(126.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(VerdeClaro),
                contentAlignment = Alignment.Center
            ) {
                IconoMancuerna(ancho = 104.dp, alto = 38.dp)
            }
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = clase.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${clase.hora} · ${clase.sala} · ${clase.duracion} min",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = clase.descripcion,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = TextoCuerpo,
                modifier = Modifier.fillMaxWidth(0.78f)
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
                fontSize = 13.sp,
                color = TextoCuerpo
            )
        }
    }
}
