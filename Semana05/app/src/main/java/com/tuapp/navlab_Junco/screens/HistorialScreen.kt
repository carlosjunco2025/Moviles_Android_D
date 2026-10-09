package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.navlab_Junco.components.*
import com.tuapp.navlab_Junco.ui.theme.*

data class HistorialItem(
    val titulo: String,
    val fecha: String,
    val detalle: String
)

@Composable
fun HistorialScreen(
    onOpenDrawer: () -> Unit
) {
    IconosBarraEstado(oscuros = true)

    val itemsHistorial = listOf(
        HistorialItem("Control cardiológico", "10 de agosto", "Presión arterial estable"),
        HistorialItem("Consulta pediátrica", "02 de julio", "Control general sin observaciones"),
        HistorialItem("Evaluación dermatológica", "15 de mayo", "Dermatitis leve, tratamiento tópico")
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Historial médico",
                tamanoTitulo = 22.sp,
                accion = { BotonMenu(onClick = onOpenDrawer) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            items(itemsHistorial) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Superficie)
                        .padding(horizontal = 22.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = item.titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.fecha,
                        fontSize = 13.sp,
                        color = TextoSecundario
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.detalle,
                        fontSize = 13.sp,
                        color = TextoCuerpo
                    )
                }
            }
        }
    }
}
