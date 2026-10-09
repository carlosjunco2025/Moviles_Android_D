package com.saludplus.citas.ui.screens.citas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.ui.components.AvatarMedico
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.BotonAzul
import com.saludplus.citas.ui.components.SelectorFechaHora
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.GrisTexto
import com.saludplus.citas.ui.theme.RojoError

@Composable
fun ReprogramarCitaScreen(
    citaId: Int,
    onAtras: () -> Unit,
    onReprogramada: () -> Unit
) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }
    val especialidad = cita?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }

    var fechaSeleccionada by remember { mutableStateOf<String?>(null) }
    var horaSeleccionada by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val puedeConfirmar = !fechaSeleccionada.isNullOrEmpty() && !horaSeleccionada.isNullOrEmpty()

    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior(titulo = "Reprogramar cita", onAtras = onAtras) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            if (medico != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFEFF4FC))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarMedico(nombre = medico.nombre, foto = medico.foto)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = medico.nombre,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        Text(
                            text = especialidad?.nombre ?: "",
                            fontSize = 14.sp,
                            color = GrisTexto
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            if (medico != null) {
                SelectorFechaHora(
                    medicoId = medico.id,
                    fechaSeleccionada = fechaSeleccionada,
                    horaSeleccionada = horaSeleccionada,
                    onFechaSeleccionada = { fecha ->
                        fechaSeleccionada = if (fecha.isEmpty()) null else fecha
                        horaSeleccionada = null
                        error = null
                    },
                    onHoraSeleccionada = { hora ->
                        horaSeleccionada = hora
                        error = null
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            if (error != null) {
                Text(
                    text = error ?: "",
                    fontSize = 14.sp,
                    color = RojoError,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            BotonAzul(
                texto = "Confirmar nuevo horario",
                habilitado = puedeConfirmar,
                onClick = {
                    val fecha = fechaSeleccionada
                    val hora = horaSeleccionada
                    if (!fecha.isNullOrEmpty() && !hora.isNullOrEmpty()) {
                        val exito = Repositorio.reprogramarCita(citaId, fecha, hora)
                        if (exito) {
                            onReprogramada()
                        } else {
                            error = "El horario seleccionado no está disponible o entra en conflicto con otra cita."
                        }
                    }
                }
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}
