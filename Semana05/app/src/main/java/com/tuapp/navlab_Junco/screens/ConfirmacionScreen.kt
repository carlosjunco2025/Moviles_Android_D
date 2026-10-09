package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.navlab_Junco.components.*
import com.tuapp.navlab_Junco.data.DatosMedicos
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun ConfirmacionScreen(
    medicoId: Int,
    fecha: String,
    hora: String,
    onVerMisCitas: () -> Unit
) {
    IconosBarraEstado(oscuros = true)
    val medico = DatosMedicos.buscar(medicoId)

    Scaffold(
        containerColor = Color.White,
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
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(VerdeExitoFondo),
                contentAlignment = Alignment.Center
            ) {
                IconoCheck(tamano = 44.dp, color = VerdeExito)
            }
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "¡Cita agendada!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = medico.nombre,
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$fecha, $hora",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(36.dp))
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Superficie)
                    .clickable { onVerMisCitas() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ver mis citas",
                    fontSize = 14.sp,
                    color = TextoPrincipal
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
