package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.navlab_Junco.components.*
import com.tuapp.navlab_Junco.data.Cita
import com.tuapp.navlab_Junco.ui.theme.*

data class PerfilItemInfo(
    val icono: ImageVector,
    val etiqueta: String,
    val valor: String
)

@Composable
fun PerfilScreen(
    citas: List<Cita>,
    onOpenDrawer: () -> Unit
) {
    IconosBarraEstado(oscuros = true)

    val citasCompletadas = citas.count { it.estado == "Completada" }

    val itemsPerfil = listOf(
        PerfilItemInfo(Icons.Filled.Email, "Correo", "juan.perez@gmail.com"),
        PerfilItemInfo(Icons.Filled.Phone, "Teléfono", "+51 987 654 321"),
        PerfilItemInfo(Icons.Filled.Person, "DNI", "71234567"),
        PerfilItemInfo(Icons.Filled.DateRange, "Citas realizadas", citasCompletadas.toString())
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Perfil",
                tamanoTitulo = 22.sp,
                accion = { BotonMenu(onClick = onOpenDrawer) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MoradoClaro),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JP",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoradoPrincipal
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Juan Pérez",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Paciente",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Superficie)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                itemsPerfil.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icono,
                            contentDescription = item.etiqueta,
                            tint = MoradoPrincipal,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = item.etiqueta,
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.valor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoPrincipal
                            )
                        }
                    }
                }
            }
        }
    }
}
