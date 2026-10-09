package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
fun PerfilMedicoScreen(
    medicoId: Int,
    onBack: () -> Unit,
    onAgendarClick: () -> Unit
) {
    IconosBarraEstado(oscuros = true)
    val medico = DatosMedicos.buscar(medicoId)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Perfil del médico",
                onBack = onBack
            )
        },
        bottomBar = {
            BotonPrincipal(
                texto = "Agendar cita",
                onClick = onAgendarClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MoradoClaro),
                contentAlignment = Alignment.Center
            ) {
                IconoMas(tamano = 52.dp, color = MoradoPrincipal)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = medico.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${medico.titulo} · ${medico.experiencia} años exp.",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Estrella",
                    tint = Estrella,
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${medico.rating} (${medico.resenas} reseñas)",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = medico.descripcion,
                    fontSize = 13.sp,
                    lineHeight = 21.sp,
                    color = TextoCuerpo,
                    modifier = Modifier.fillMaxWidth(0.72f)
                )
            }
        }
    }
}
