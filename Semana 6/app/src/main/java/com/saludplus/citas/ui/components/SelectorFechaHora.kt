package com.saludplus.citas.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.AzulPrimario
import com.saludplus.citas.ui.theme.GrisTexto
import com.saludplus.citas.util.mesYAnio
import com.saludplus.citas.util.nombreDiaCorto
import com.saludplus.citas.util.semanaDeCalendario
import java.time.LocalDate

@Composable
fun SelectorFechaHora(
    medicoId: Int,
    fechaSeleccionada: String?,
    horaSeleccionada: String?,
    onFechaSeleccionada: (String) -> Unit,
    onHoraSeleccionada: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val hoy = remember { LocalDate.now() }
    var indiceSemana by remember { mutableIntStateOf(0) }

    val diasVisibles = remember(hoy, indiceSemana) {
        semanaDeCalendario(hoy, indiceSemana)
    }

    val tituloMesAnio = remember(diasVisibles) {
        diasVisibles.firstOrNull()?.let { mesYAnio(it) } ?: ""
    }

    // Horas libres de este médico en el día elegido
    val horarios = fechaSeleccionada
        ?.takeIf { it.isNotEmpty() }
        ?.let { Repositorio.horariosDisponibles(medicoId, it) }
        ?: emptyList()

    Column(modifier = modifier) {
        // Mes y año con flechas de navegación por semana
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (indiceSemana > 0) {
                        indiceSemana--
                        onFechaSeleccionada("")
                    }
                },
                enabled = indiceSemana > 0
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Semana anterior",
                    tint = if (indiceSemana > 0) GrisTexto else GrisTexto.copy(alpha = 0.3f)
                )
            }
            Text(
                text = tituloMesAnio,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AzulOscuro
            )
            IconButton(
                onClick = {
                    indiceSemana++
                    onFechaSeleccionada("")
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Semana siguiente",
                    tint = GrisTexto
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Selector de día con animación por semana
        AnimatedContent(
            targetState = indiceSemana,
            label = "CambioSemana",
            transitionSpec = {
                if (targetState > initialState) {
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                } else {
                    slideInHorizontally { width -> -width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> width } + fadeOut()
                }.using(SizeTransform(clip = false))
            }
        ) { targetIndice ->
            val dias = semanaDeCalendario(hoy, targetIndice)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dias.forEach { fecha ->
                    val fechaIso = fecha.toString()
                    val esHoy = fecha == hoy
                    DiaChip(
                        fecha = fecha,
                        esHoy = esHoy,
                        seleccionado = fechaIso == fechaSeleccionada,
                        onClick = {
                            onFechaSeleccionada(fechaIso)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Cuadrícula de horarios disponibles
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                fechaSeleccionada.isNullOrEmpty() -> MensajeCentrado("Selecciona un día para ver los horarios")
                horarios.isEmpty() -> MensajeCentrado("No hay horarios disponibles para este día")
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(horarios, key = { it }) { hora ->
                        HoraChip(
                            hora = hora,
                            seleccionada = hora == horaSeleccionada,
                            onClick = { onHoraSeleccionada(hora) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MensajeCentrado(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            color = GrisTexto,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun DiaChip(
    fecha: LocalDate,
    esHoy: Boolean,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val fondo = if (seleccionado) AzulPrimario else Color(0xFFF1F5FB)
    val textoDia = if (seleccionado) Color.White else if (esHoy) AzulPrimario else GrisTexto
    val textoNumero = if (seleccionado) Color.White else AzulOscuro

    Column(
        modifier = modifier
            .height(72.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(fondo)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (esHoy) "Hoy" else nombreDiaCorto(fecha),
            fontSize = 12.sp,
            fontWeight = if (esHoy) FontWeight.Bold else FontWeight.Normal,
            color = textoDia
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = fecha.dayOfMonth.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textoNumero
        )
    }
}

@Composable
private fun HoraChip(
    hora: String,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    val fondo = if (seleccionada) AzulPrimario else Color(0xFFF1F5FB)
    val colorTexto = if (seleccionada) Color.White else AzulOscuro

    Box(
        modifier = Modifier
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(fondo)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = hora,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorTexto
        )
    }
}
