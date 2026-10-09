package com.saludplus.citas.ui.screens.agendamiento

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.TarjetaSuave
import com.saludplus.citas.ui.components.estiloEspecialidad
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.AzulPrimario
import com.saludplus.citas.ui.theme.GrisTexto
import com.saludplus.citas.ui.theme.VerdeSedes
import com.saludplus.citas.ui.theme.VerdeSedesFondo

@Composable
fun EspecialidadesSedeScreen(
    sedeId: Int,
    onAtras: () -> Unit,
    onEspecialidad: (Int) -> Unit
) {
    val sede = Repositorio.obtenerSede(sedeId)
    var busqueda by remember { mutableStateOf("") }

    val especialidadesSede = Repositorio.especialidadesPorSede(sedeId)
    val especialidadesFiltradas = especialidadesSede.filter {
        it.nombre.contains(busqueda.trim(), ignoreCase = true)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior(titulo = "Especialidades", onAtras = onAtras) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Franja verde con información de la sede
            if (sede != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VerdeSedesFondo)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = VerdeSedes,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = sede.nombre,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        Text(
                            text = sede.direccion,
                            fontSize = 12.sp,
                            color = GrisTexto
                        )
                    }
                }
            }

            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar especialidad...", color = Color(0xFF9CA3AF)) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = GrisTexto)
                },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        IconButton(onClick = { busqueda = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar", tint = GrisTexto)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F5FB),
                    unfocusedContainerColor = Color(0xFFF1F5FB),
                    focusedBorderColor = AzulPrimario,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = AzulPrimario
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (especialidadesFiltradas.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No se encontraron resultados",
                                fontSize = 15.sp,
                                color = GrisTexto,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                } else {
                    items(especialidadesFiltradas, key = { "esp_${it.id}" }) { especialidad ->
                        val estilo = estiloEspecialidad(especialidad.id)
                        val numDoctores = Repositorio.medicosPorSedeYEspecialidad(sedeId, especialidad.id).size
                        val textoDoctores = if (numDoctores == 1) "1 doctor" else "$numDoctores doctores"

                        TarjetaSuave(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEspecialidad(especialidad.id) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(estilo.fondo),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = estilo.icono,
                                        contentDescription = null,
                                        tint = estilo.color,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Spacer(Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = especialidad.nombre,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = AzulOscuro,
                                            modifier = Modifier.weight(1f, fill = false)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(VerdeSedesFondo)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = textoDoctores,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = VerdeSedes
                                            )
                                        }
                                    }
                                    Spacer(Modifier.height(2.dp))
                                    Text(
                                        text = especialidad.descripcion,
                                        fontSize = 13.sp,
                                        color = GrisTexto
                                    )
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = GrisTexto
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
