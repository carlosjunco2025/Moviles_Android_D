package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun RutinasScreen(
    rutaActual: String?,
    onTabClick: (String) -> Unit
) {
    val rutinas = listOf(
        Pair("Tren superior", "5 ejercicios · 30 min"),
        Pair("Piernas y glúteos", "6 ejercicios · 40 min"),
        Pair("Core y abdomen", "4 ejercicios · 20 min")
    )

    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(titulo = "Rutinas", tamanoTitulo = 22.sp)
        },
        bottomBar = {
            BarraInferior(rutaActual = rutaActual, onTabClick = onTabClick)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(rutinas) { (nombre, subtitulo) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Superficie)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(VerdeClaro),
                        contentAlignment = Alignment.Center
                    ) {
                        IconoMancuerna(ancho = 36.dp, alto = 16.dp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = nombre,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtitulo,
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }
    }
}
