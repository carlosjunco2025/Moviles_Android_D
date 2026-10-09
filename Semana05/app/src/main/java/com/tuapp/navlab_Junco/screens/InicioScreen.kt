package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
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
import com.tuapp.navlab_Junco.components.IconoMas
import com.tuapp.navlab_Junco.components.IconosBarraEstado
import com.tuapp.navlab_Junco.data.DatosMedicos
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun InicioScreen(
    onMedicoClick: (Int) -> Unit,
    onOpenDrawer: () -> Unit
) {
    IconosBarraEstado(oscuros = false)

    var seleccionada by rememberSaveable { mutableStateOf("Cardiología") }
    val medicosOrdenados = remember(seleccionada) {
        DatosMedicos.listaMedicos.sortedByDescending { it.especialidad == seleccionada }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MoradoPrincipal)
                    .statusBarsPadding()
                    .padding(start = 20.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Clínica Salud+",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hola, Juan",
                        fontSize = 13.sp,
                        color = TextoSubtituloBar
                    )
                }
                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menú",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(DatosMedicos.especialidades) { esp ->
                    val esSeleccionada = esp == seleccionada
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (esSeleccionada) MoradoPrincipal else Superficie)
                            .clickable { seleccionada = esp }
                            .padding(horizontal = 22.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = esp,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (esSeleccionada) Color.White else TextoItemDrawer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Médicos disponibles",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(medicosOrdenados) { medico ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(82.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Superficie)
                            .clickable { onMedicoClick(medico.id) }
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MoradoClaro),
                            contentAlignment = Alignment.Center
                        ) {
                            IconoMas(tamano = 30.dp, color = MoradoPrincipal)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = medico.nombre,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = medico.titulo,
                                fontSize = 13.sp,
                                color = TextoSecundario
                            )
                        }
                        Row(
                            modifier = Modifier.offset(y = (-8).dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Estrella",
                                tint = Estrella,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = medico.rating.toString(),
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
