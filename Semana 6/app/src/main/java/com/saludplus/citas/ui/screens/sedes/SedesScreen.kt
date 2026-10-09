package com.saludplus.citas.ui.screens.sedes

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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.data.model.Cita
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.AvatarMedico
import com.saludplus.citas.ui.components.BarraNavegacion
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.BotonAzul
import com.saludplus.citas.ui.components.TarjetaSuave
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.AzulPrimario
import com.saludplus.citas.ui.theme.GrisTexto
import com.saludplus.citas.ui.theme.VerdeSedes
import com.saludplus.citas.ui.theme.VerdeSedesFondo
import com.saludplus.citas.util.fechaEnTexto
import java.time.LocalDate

private fun esProxima(fechaIso: String): Boolean {
    return try {
        val fecha = LocalDate.parse(fechaIso)
        !fecha.isBefore(LocalDate.now())
    } catch (e: Exception) {
        true
    }
}

@Composable
fun SedesScreen(
    onNavegar: (String) -> Unit,
    onAgendar: () -> Unit,
    onCita: (Int) -> Unit
) {
    val totalCitas = Repositorio.cantidadCitasUsuario()
    val citasPorSede = Repositorio.citasPorSede()

    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior(titulo = "Sedes") },
        bottomBar = { BarraNavegacion(rutaActual = Rutas.SEDES, onNavegar = onNavegar) }
    ) { padding ->
        if (totalCitas == 0) {
            // Estado vacío global cuando no hay ninguna cita
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(VerdeSedesFondo),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = VerdeSedes,
                        modifier = Modifier.size(56.dp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Aún no tienes citas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Elige una sede y agenda tu primera cita.",
                    fontSize = 14.sp,
                    color = GrisTexto,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                BotonAzul(texto = "Agendar cita", onClick = onAgendar)
            }
        } else {
            val totalSedes = citasPorSede.size
            val textoCitas = if (totalCitas == 1) "1 cita" else "$totalCitas citas"
            val textoSedes = if (totalSedes == 1) "1 sede" else "$totalSedes sedes"

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "$textoCitas en $textoSedes",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = GrisTexto
                    )
                }

                citasPorSede.forEach { (sede, listaCitas) ->
                    item(key = "header_${sede.id}") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(VerdeSedesFondo),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = VerdeSedes,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = sede.nombre,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AzulOscuro
                                )
                                Text(
                                    text = sede.direccion,
                                    fontSize = 12.sp,
                                    color = GrisTexto
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            val numCitasSede = listaCitas.size
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (numCitasSede > 0) VerdeSedesFondo else Color(0xFFF1F5FB))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (numCitasSede > 0) {
                                        if (numCitasSede == 1) "1 cita" else "$numCitasSede citas"
                                    } else {
                                        "Sin citas"
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (numCitasSede > 0) VerdeSedes else GrisTexto
                                )
                            }
                        }
                    }

                    if (listaCitas.isEmpty()) {
                        item(key = "empty_${sede.id}") {
                            Text(
                                text = "Aún no tienes citas en esta sede",
                                fontSize = 13.sp,
                                color = GrisTexto,
                                modifier = Modifier.padding(start = 52.dp, bottom = 8.dp)
                            )
                        }
                    } else {
                        items(listaCitas, key = { "cita_${it.id}" }) { cita ->
                            TarjetaCitaSede(
                                cita = cita,
                                onClick = { onCita(cita.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaCitaSede(
    cita: Cita,
    onClick: () -> Unit
) {
    val medico = Repositorio.obtenerMedico(cita.medicoId)
    val especialidad = Repositorio.obtenerEspecialidad(cita.especialidadId)
    val esProximaCita = esProxima(cita.fecha)

    TarjetaSuave(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (medico != null) {
                AvatarMedico(nombre = medico.nombre, foto = medico.foto)
                Spacer(Modifier.width(14.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medico?.nombre ?: "Médico",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Text(
                    text = especialidad?.nombre ?: "",
                    fontSize = 13.sp,
                    color = GrisTexto
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (esProximaCita) Color(0xFFDDF7E8) else Color(0xFFF1F5FB))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (esProximaCita) "Próxima" else "Pasada",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (esProximaCita) Color(0xFF1E9E5A) else GrisTexto
                )
            }
        }

        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE6EBF3))
        )
        Spacer(Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DatoCita(
                icono = Icons.Default.DateRange,
                texto = fechaEnTexto(cita.fecha),
                modifier = Modifier.weight(1.2f)
            )
            DatoCita(
                icono = Icons.Default.AccessTime,
                texto = cita.hora,
                modifier = Modifier.weight(0.8f)
            )
        }
    }
}

@Composable
private fun DatoCita(
    icono: ImageVector,
    texto: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = AzulPrimario,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = texto,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = AzulOscuro
        )
    }
}
