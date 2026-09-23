package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.tuapp.tecsupfit.components.BarraInferior
import com.tuapp.tecsupfit.components.BarraSuperiorBlanca
import com.tuapp.tecsupfit.components.IconosBarraEstado
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.Superficie
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.VerdeClaro
import com.tuapp.tecsupfit.ui.theme.VerdePrincipal

@Composable
fun PerfilScreen(
    rutaActual: String?,
    onTabClick: (String) -> Unit
) {
    IconosBarraEstado(oscuros = true)

    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Mi perfil",
                tamanoTitulo = 22.sp
            )
        },
        bottomBar = {
            BarraInferior(
                rutaActual = rutaActual,
                onTabClick = onTabClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(VerdeClaro),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "DR",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePrincipal
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Diego Ramos",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Plan Premium",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Superficie),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "14",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Clases",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Superficie),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "3",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rachas",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }
    }
}
