package com.tuapp.navlab_Junco.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun DrawerContent(
    rutaActual: String?,
    onOpcionClick: (String) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(300.dp)
            .border(2.dp, MoradoPrincipal, RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)),
        drawerShape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
        drawerContainerColor = Color.White
    ) {
        // Cabecera
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MoradoClaro),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JP",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoradoPrincipal
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = "Juan Pérez",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Paciente",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp),
            thickness = 1.dp,
            color = Divisor
        )
        Spacer(modifier = Modifier.height(12.dp))

        val opciones = listOf(
            Triple("Inicio", "inicio", false),
            Triple("Mis citas", "mis_citas", false),
            Triple("Historial médico", "historial", false),
            Triple("Perfil", "perfil", false)
        )

        opciones.forEach { (titulo, ruta, _) ->
            val seleccionado = rutaActual == ruta
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (seleccionado) MoradoClaro else Color.Transparent)
                    .clickable { onOpcionClick(ruta) }
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(
                            width = 1.5.dp,
                            color = if (seleccionado) MoradoPrincipal else TextoPrincipal,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = titulo,
                    fontSize = 15.sp,
                    fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                    color = if (seleccionado) MoradoPrincipal else TextoItemDrawer
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
