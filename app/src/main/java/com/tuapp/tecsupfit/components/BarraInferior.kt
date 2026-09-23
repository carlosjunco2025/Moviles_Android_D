package com.tuapp.tecsupfit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.tecsupfit.ui.theme.Divisor
import com.tuapp.tecsupfit.ui.theme.TextoInactivo
import com.tuapp.tecsupfit.ui.theme.TextoSecundario
import com.tuapp.tecsupfit.ui.theme.VerdePrincipal

@Composable
fun BarraInferior(
    rutaActual: String?,
    onTabClick: (String) -> Unit
) {
    val tabs = listOf(
        "Inicio" to "inicio",
        "Reservas" to "reservas",
        "Rutinas" to "rutinas",
        "Perfil" to "perfil"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                .padding(top = 14.dp, bottom = 12.dp)
        ) {
            tabs.forEach { (label, route) ->
                val activa = rutaActual == route
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabClick(route) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .border(
                                width = if (activa) 2.dp else 1.5.dp,
                                color = if (activa) VerdePrincipal else TextoInactivo,
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = if (activa) FontWeight.Bold else FontWeight.Normal,
                        color = if (activa) VerdePrincipal else TextoSecundario
                    )
                }
            }
        }
    }
}
