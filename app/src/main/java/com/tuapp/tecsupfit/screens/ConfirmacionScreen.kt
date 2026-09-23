package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import com.tuapp.tecsupfit.components.IconoCheck
import com.tuapp.tecsupfit.components.IconosBarraEstado
import com.tuapp.tecsupfit.data.DatosClases
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.Superficie
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.VerdeClaro
import com.tuapp.tecsupfit.ui.theme.VerdeExito

@Composable
fun ConfirmacionScreen(
    claseId: Int,
    onVerReservasClick: () -> Unit
) {
    IconosBarraEstado(oscuros = true)

    val clase = DatosClases.buscar(claseId)

    Scaffold(
        containerColor = Fondo,
        topBar = {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(VerdeClaro),
                contentAlignment = Alignment.Center
            ) {
                IconoCheck(tamano = 38.dp, color = VerdeExito)
            }
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "¡Cupo reservado!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = clase.nombre,
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${clase.dia}, ${clase.hora} · ${clase.sala}",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(36.dp))
            Box(
                modifier = Modifier
                    .size(width = 240.dp, height = 56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Superficie)
                    .clickable { onVerReservasClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ver mis reservas",
                    fontSize = 14.sp,
                    color = TextoPrincipal
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
