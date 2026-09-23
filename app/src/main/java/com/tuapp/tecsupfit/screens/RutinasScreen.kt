package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.tuapp.tecsupfit.components.BarraInferior
import com.tuapp.tecsupfit.components.BarraSuperiorBlanca
import com.tuapp.tecsupfit.components.IconoMancuerna
import com.tuapp.tecsupfit.components.IconosBarraEstado
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.Superficie
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.VerdeClaro

data class Rutina(val nombre: String, val subtitulo: String)

@Composable
fun RutinasScreen(
    rutaActual: String?,
    onTabClick: (String) -> Unit
) {
    IconosBarraEstado(oscuros = true)

    val rutinas = listOf(
        Rutina("Tren superior", "5 ejercicios · 30 min"),
        Rutina("Piernas y glúteos", "6 ejercicios · 40 min"),
        Rutina("Core y abdomen", "4 ejercicios · 20 min")
    )

    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Rutinas",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(rutinas) { rutina ->
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
                    Column {
                        Text(
                            text = rutina.nombre,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = rutina.subtitulo,
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }
    }
}
