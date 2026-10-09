package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.tuapp.navlab_Junco.components.*
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun PerfilScreen(
    rutaActual: String?,
    onTabClick: (String) -> Unit
) {
    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(titulo = "Mi perfil", tamanoTitulo = 22.sp)
        },
        bottomBar = {
            BarraInferior(rutaActual = rutaActual, onTabClick = onTabClick)
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
                // Estadística 1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Superficie),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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
                // Estadística 2
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Superficie),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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
