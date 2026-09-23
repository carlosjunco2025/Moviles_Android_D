package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.tecsupfit.components.BarraInferior
import com.tuapp.tecsupfit.components.IconoMancuerna
import com.tuapp.tecsupfit.components.IconosBarraEstado
import com.tuapp.tecsupfit.data.DatosClases
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.Superficie
import com.tuapp.tecsupfit.ui.theme.TextoInactivo
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.TextoSubtituloBar
import com.tuapp.tecsupfit.ui.theme.VerdeClaro
import com.tuapp.tecsupfit.ui.theme.VerdePrincipal

@Composable
fun InicioScreen(
    rutaActual: String?,
    onTabClick: (String) -> Unit,
    onClaseClick: (Int) -> Unit
) {
    IconosBarraEstado(oscuros = false)

    var filtroSeleccionado by rememberSaveable { mutableStateOf("Hoy") }

    val clasesFiltradas = if (filtroSeleccionado == "Hoy") {
        DatosClases.listaClases.filter { it.dia == "Hoy" }
    } else {
        DatosClases.listaClases
    }

    Scaffold(
        containerColor = Fondo,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerdePrincipal)
                    .statusBarsPadding()
                    .padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 18.dp)
            ) {
                Text(
                    text = "TECSUP Fit",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hola, Diego",
                    fontSize = 13.sp,
                    color = TextoSubtituloBar
                )
            }
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
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DatosClases.filtros) { filtro ->
                    val seleccionado = filtro == filtroSeleccionado
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (seleccionado) VerdePrincipal else Superficie)
                            .clickable { filtroSeleccionado = filtro }
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filtro,
                            fontSize = 12.sp,
                            fontWeight = if (seleccionado) FontWeight.Medium else FontWeight.Normal,
                            color = if (seleccionado) Color.White else TextoInactivo
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Clases disponibles",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                items(clasesFiltradas) { clase ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(78.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Superficie)
                            .clickable { onClaseClick(clase.id) }
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
                                text = clase.nombre,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            val subtitulo = if (clase.dia != "Hoy") {
                                "${clase.dia} · ${clase.hora} · ${clase.sala}"
                            } else {
                                "${clase.hora} · ${clase.sala}"
                            }
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
}
