package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
fun AgendarCitaScreen(
    medicoId: Int,
    onBack: () -> Unit,
    onConfirmar: (String, String, String) -> Unit
) {
    IconosBarraEstado(oscuros = true)
    val medico = DatosMedicos.buscar(medicoId)

    val fechas = listOf(
        Triple("Jue", "26", "Jueves 26"),
        Triple("Vie", "27", "Viernes 27"),
        Triple("Sáb", "28", "Sábado 28")
    )

    val horas = listOf(
        Pair("9:00", "9:00 am"),
        Pair("10:30", "10:30 am"),
        Pair("3:00", "3:00 pm")
    )

    var indiceFechaSeleccionada by rememberSaveable { mutableStateOf(1) }
    var indiceHoraSeleccionada by rememberSaveable { mutableStateOf(1) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            BarraSuperiorBlanca(
                titulo = "Agendar cita",
                onBack = onBack
            )
        },
        bottomBar = {
            BotonPrincipal(
                texto = "Confirmar cita",
                onClick = {
                    val fechaTexto = fechas[indiceFechaSeleccionada].third
                    val horaTexto = horas[indiceHoraSeleccionada].second
                    onConfirmar(fechaTexto, horaTexto, medico.nombre)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Selecciona fecha",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                fechas.forEachIndexed { index, (dia, num, _) ->
                    val seleccionada = index == indiceFechaSeleccionada
                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(62.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (seleccionada) MoradoPrincipal else Superficie)
                            .clickable { indiceFechaSeleccionada = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dia,
                                fontSize = 12.sp,
                                color = if (seleccionada) Color.White else TextoSecundario
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = num,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (seleccionada) Color.White else TextoPrincipal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Selecciona hora",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                horas.forEachIndexed { index, (horaCorta, _) ->
                    val seleccionada = index == indiceHoraSeleccionada
                    Box(
                        modifier = Modifier
                            .width(94.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (seleccionada) MoradoPrincipal else Superficie)
                            .clickable { indiceHoraSeleccionada = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = horaCorta,
                            fontSize = 13.sp,
                            fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Normal,
                            color = if (seleccionada) Color.White else TextoItemDrawer
                        )
                    }
                }
            }
        }
    }
}
