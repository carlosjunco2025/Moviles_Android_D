package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.ui.components.AvatarMedico
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.TarjetaSuave
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.AzulPrimario
import com.saludplus.citas.ui.theme.GrisTexto
import com.saludplus.citas.ui.theme.VerdeSedes
import com.saludplus.citas.ui.theme.VerdeSedesFondo

@Composable
fun MedicosScreen(
    especialidadId: Int,
    sedeId: Int? = null,
    onAtras: () -> Unit,
    onMedico: (Int) -> Unit
) {
    val especialidad = Repositorio.obtenerEspecialidad(especialidadId)
    val nombreEspecialidad = especialidad?.nombre ?: "Especialidad"
    val sede = sedeId?.let { Repositorio.obtenerSede(it) }

    var buscando by remember { mutableStateOf(false) }
    var busqueda by remember { mutableStateOf("") }
    var soloFavoritos by remember { mutableStateOf(false) }

    // Médicos de la especialidad (y sede si aplica), ordenados por calificación y filtrados por el buscador
    val medicosEspecialidad = if (sedeId != null) {
        Repositorio.medicosPorSedeYEspecialidad(sedeId, especialidadId)
    } else {
        Repositorio.buscarMedicos(especialidadId, "")
    }

    val medicosBase = if (busqueda.trim().isNotEmpty()) {
        medicosEspecialidad.filter { it.nombre.contains(busqueda.trim(), ignoreCase = true) }
    } else {
        medicosEspecialidad
    }

    val medicos = if (soloFavoritos) {
        medicosBase.filter { Repositorio.esFavorito(it.id) }
    } else {
        medicosBase
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            BarraSuperior(
                titulo = "Médicos de $nombreEspecialidad",
                onAtras = onAtras,
                acciones = {
                    IconButton(onClick = {
                        buscando = !buscando
                        if (!buscando) busqueda = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar médico",
                            tint = AzulOscuro
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Franja indicadora de Sede (si sedeId no es nulo)
            if (sede != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VerdeSedesFondo)
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = VerdeSedes,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Sede ${sede.nombre}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AzulOscuro
                    )
                }
            }

            // Buscador (aparece al tocar la lupa)
            if (buscando) {
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    placeholder = { Text("Buscar médico...", color = Color(0xFF9CA3AF)) },
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
            }

            // Chip "Solo favoritos"
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (soloFavoritos) AzulPrimario else Color(0xFFF1F5FB))
                        .clickable { soloFavoritos = !soloFavoritos }
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (soloFavoritos) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (soloFavoritos) Color.White else GrisTexto,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Solo favoritos",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (soloFavoritos) Color.White else AzulOscuro
                    )
                }
            }

            if (medicos.isEmpty()) {
                val mensajeVacio = when {
                    soloFavoritos && sede != null -> "No tienes médicos favoritos en esta sede"
                    soloFavoritos -> "No tienes médicos favoritos en esta especialidad"
                    sede != null -> "No se encontraron médicos en la ${sede.nombre}"
                    else -> "No se encontraron médicos"
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = mensajeVacio,
                        fontSize = 15.sp,
                        color = GrisTexto,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(medicos, key = { it.id }) { medico ->
                        TarjetaSuave(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onMedico(medico.id) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AvatarMedico(nombre = medico.nombre, foto = medico.foto)
                                Spacer(Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = medico.nombre,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AzulOscuro
                                    )
                                    Text(
                                        text = nombreEspecialidad,
                                        fontSize = 13.sp,
                                        color = GrisTexto
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFF5B301),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            text = "${medico.calificacion} (${medico.resenas})",
                                            fontSize = 13.sp,
                                            color = GrisTexto
                                        )
                                    }
                                }

                                // Botón de favorito con animación de escala
                                val esFav = Repositorio.esFavorito(medico.id)
                                val escala by animateFloatAsState(
                                    targetValue = if (esFav) 1.2f else 1.0f,
                                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                                    label = "EscalaFavorito"
                                )
                                IconButton(
                                    onClick = { Repositorio.alternarFavorito(medico.id) },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        imageVector = if (esFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = if (esFav) "Quitar de favoritos" else "Marcar como favorito",
                                        tint = if (esFav) Color(0xFFE53935) else GrisTexto,
                                        modifier = Modifier.scale(escala)
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))

                            // Etiqueta verde de disponibilidad, abajo a la derecha
                            Box(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFDDF7E8))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = medico.disponibilidad,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E9E5A)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
