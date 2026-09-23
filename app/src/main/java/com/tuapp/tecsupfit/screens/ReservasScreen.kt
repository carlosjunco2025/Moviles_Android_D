package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.tecsupfit.components.BarraInferior
import com.tuapp.tecsupfit.components.BarraSuperiorBlanca
import com.tuapp.tecsupfit.components.IconosBarraEstado
import com.tuapp.tecsupfit.data.Reserva
import com.tuapp.tecsupfit.ui.theme.Fondo
import com.tuapp.tecsupfit.ui.theme.GrisBadge
import com.tuapp.tecsupfit.ui.theme.Superficie
import com.tuapp.tecsupfit.ui.theme.TextoPrincipal
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.VerdeClaro
import com.tuapp.tecsupfit.ui.theme.VerdeExito
import com.tuapp.tecsupfit.ui.theme.VerdePrincipal

@Composable
fun ReservasScreen(
    reservas: List<Reserva>,
    rutaActual: String?,
    onTabClick: (String) -> Unit
) {
    IconosBarraEstado(oscuros = true)

    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Mis reservas",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reservas) { reserva ->
                val confirmada = reserva.estado == "Confirmada"
                val forma = if (confirmada) {
                    RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 14.dp, bottomEnd = 14.dp)
                } else {
                    RoundedCornerShape(14.dp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(106.dp)
                        .clip(forma)
                        .background(Superficie)
                ) {
                    if (confirmada) {
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .fillMaxHeight()
                                .background(VerdePrincipal)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                start = if (confirmada) 16.dp else 22.dp,
                                end = 16.dp
                            ),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = reserva.clase,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = reserva.fecha,
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .size(width = 118.dp, height = 30.dp)
                                .clip(RoundedCornerShape(50))
                                .background(if (confirmada) VerdeClaro else GrisBadge),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = reserva.estado,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (confirmada) VerdeExito else TextoSecundario,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
