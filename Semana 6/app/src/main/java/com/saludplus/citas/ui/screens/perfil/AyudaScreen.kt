package com.saludplus.citas.ui.screens.perfil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.TarjetaSuave
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.AzulPrimario
import com.saludplus.citas.ui.theme.GrisTexto

private data class PreguntaFrecuente(
    val pregunta: String,
    val respuesta: String
)

private val preguntas = listOf(
    PreguntaFrecuente(
        "¿Cómo agendar una cita médica?",
        "Dirígete a la sección 'Agendar cita' o explora las 'Especialidades'. Selecciona la especialidad de tu interés, elige un médico disponible, selecciona la fecha y hora que más te convenga y presiona 'Confirmar cita'."
    ),
    PreguntaFrecuente(
        "¿Cómo cancelar una cita?",
        "Ve a la sección 'Mis citas', selecciona la cita que deseas cancelar para ver sus detalles y presiona el botón 'Cancelar cita'. El horario volverá a quedar disponible."
    ),
    PreguntaFrecuente(
        "¿Cómo reprogramar una cita?",
        "Abre el detalle de la cita que deseas cambiar desde 'Mis citas' y presiona 'Reprogramar cita'. Podrás elegir una nueva fecha y horario disponible sin perder tu cita."
    ),
    PreguntaFrecuente(
        "¿Cómo marcar médicos favoritos?",
        "En el listado de médicos de cualquier especialidad, toca el ícono de corazón en la tarjeta del médico. Tus médicos favoritos aparecerán en la pantalla principal para un acceso rápido."
    ),
    PreguntaFrecuente(
        "¿Por qué un horario no aparece disponible?",
        "Los horarios no se muestran si ya han sido reservados por otro paciente o si ya tienes otra cita agendada exactamente a esa misma fecha y hora."
    ),
    PreguntaFrecuente(
        "¿Por qué mis datos se pierden al cerrar la aplicación?",
        "Esta versión es una aplicación de práctica y demostración. Los datos se almacenan únicamente en la memoria temporal del dispositivo mientras la aplicación permanece abierta."
    )
)

@Composable
fun AyudaScreen(
    onAtras: () -> Unit
) {
    var indiceAbierto by rememberSaveable { mutableStateOf<Int?>(null) }

    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior(titulo = "Ayuda y preguntas frecuentes", onAtras = onAtras) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Encuentra respuestas a las dudas más comunes sobre el uso de la aplicación.",
                    fontSize = 14.sp,
                    color = GrisTexto,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            itemsIndexed(preguntas) { index, item ->
                val estaAbierto = indiceAbierto == index
                val anguloRotacion by animateFloatAsState(
                    targetValue = if (estaAbierto) 180f else 0f,
                    label = "RotacionFlecha"
                )

                TarjetaSuave(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        indiceAbierto = if (estaAbierto) null else index
                    }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.pregunta,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = AzulOscuro,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = AzulPrimario,
                                modifier = Modifier.rotate(anguloRotacion)
                            )
                        }

                        AnimatedVisibility(
                            visible = estaAbierto,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column {
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = item.respuesta,
                                    fontSize = 14.sp,
                                    color = GrisTexto,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
