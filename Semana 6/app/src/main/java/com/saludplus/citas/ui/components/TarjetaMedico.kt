package com.saludplus.citas.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.data.model.Medico
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.CorazonActivo
import com.saludplus.citas.ui.theme.GrisTexto
import com.saludplus.citas.ui.theme.VerdeSedes

@Composable
fun TarjetaMedico(
    medico: Medico,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mostrarSede: Boolean = false,
    onFavoritoCambiado: ((Boolean) -> Unit)? = null
) {
    val especialidad = Repositorio.obtenerEspecialidad(medico.especialidadId)
    val nombreEspecialidad = especialidad?.nombre ?: "Especialidad"
    val sede = if (mostrarSede) Repositorio.sedeDelMedico(medico.id) else null

    val esFav = Repositorio.esFavorito(medico.id)

    TarjetaSuave(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
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
                    text = "$nombreEspecialidad · ${medico.aniosExperiencia} años exp.",
                    fontSize = 13.sp,
                    color = GrisTexto
                )
                if (sede != null) {
                    Spacer(Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = VerdeSedes,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = sede.nombre,
                            fontSize = 12.sp,
                            color = GrisTexto
                        )
                    }
                }
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

            // Botón de corazón favorito con animación de escala
            val escala by animateFloatAsState(
                targetValue = if (esFav) 1.2f else 1.0f,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                label = "EscalaFavorito"
            )
            IconButton(
                onClick = {
                    val nuevoEstado = Repositorio.alternarFavorito(medico.id)
                    onFavoritoCambiado?.invoke(nuevoEstado)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (esFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (esFav) "Quitar de Mis doctores" else "Agregar a Mis doctores",
                    tint = if (esFav) CorazonActivo else GrisTexto,
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
