package com.tuapp.navlab_Junco.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun BarraInferior(rutaActual: String?, onTabClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Fondo)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 12.dp),
            thickness = 1.dp,
            color = Divisor
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val tabs = listOf(
                Pair("Inicio", "inicio"),
                Pair("Reservas", "reservas"),
                Pair("Rutinas", "rutinas"),
                Pair("Perfil", "perfil")
            )

            tabs.forEach { (nombre, ruta) ->
                val activa = (rutaActual == ruta)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onTabClick(ruta)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (activa) 2.dp else 1.5.dp,
                                color = if (activa) VerdePrincipal else TextoInactivo,
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = nombre,
                        fontSize = 12.sp,
                        fontWeight = if (activa) FontWeight.Bold else FontWeight.Normal,
                        color = if (activa) VerdePrincipal else TextoSecundario
                    )
                }
            }
        }
    }
}
