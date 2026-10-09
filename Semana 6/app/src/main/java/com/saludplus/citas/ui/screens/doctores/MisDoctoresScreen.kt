package com.saludplus.citas.ui.screens.doctores

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.saludplus.citas.ui.components.BotonAzul
import com.saludplus.citas.ui.components.TarjetaMedico
import com.saludplus.citas.ui.components.estiloEspecialidad
import com.saludplus.citas.ui.theme.AzulClaro
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.AzulPrimario
import com.saludplus.citas.ui.theme.GrisTexto
import kotlinx.coroutines.launch

@Composable
fun MisDoctoresScreen(
    onAtras: () -> Unit,
    onMedico: (Int) -> Unit,
    onVerEspecialidades: () -> Unit
) {
    val gruposFavoritos = Repositorio.medicosFavoritosPorEspecialidad()
    val totalDoctores = Repositorio.cantidadFavoritos()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White,
        topBar = { BarraSuperior(titulo = "Mis doctores", onAtras = onAtras) }
    ) { padding ->
        if (totalDoctores == 0) {
            // Estado vacío cuando no hay médicos favoritos
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
                        .background(AzulClaro),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = AzulPrimario,
                        modifier = Modifier.size(56.dp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Aún no tienes doctores favoritos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Marca el corazón en la lista de médicos de cada especialidad para verlos aquí.",
                    fontSize = 14.sp,
                    color = GrisTexto,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                BotonAzul(texto = "Ver especialidades", onClick = onVerEspecialidades)
            }
        } else {
            val totalEspecialidades = gruposFavoritos.size
            val textoDoctores = if (totalDoctores == 1) "1 doctor" else "$totalDoctores doctores"
            val textoEspecialidades = if (totalEspecialidades == 1) "1 especialidad" else "$totalEspecialidades especialidades"

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "$textoDoctores en $textoEspecialidades",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = GrisTexto
                    )
                }

                gruposFavoritos.forEach { (especialidad, medicosGrupo) ->
                    val estilo = estiloEspecialidad(especialidad.id)
                    item(key = "header_esp_${especialidad.id}") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(estilo.fondo),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = estilo.icono,
                                    contentDescription = null,
                                    tint = estilo.color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = especialidad.nombre,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AzulOscuro,
                                modifier = Modifier.weight(1f)
                            )
                            val cantMedicos = medicosGrupo.size
                            val textoCant = if (cantMedicos == 1) "1 doctor" else "$cantMedicos doctores"
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(estilo.fondo)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = textoCant,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = estilo.color
                                )
                            }
                        }
                    }

                    items(medicosGrupo, key = { "doc_${it.id}" }) { medico ->
                        TarjetaMedico(
                            medico = medico,
                            onClick = { onMedico(medico.id) },
                            mostrarSede = true,
                            onFavoritoCambiado = {
                                scope.launch {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    val result = snackbarHostState.showSnackbar(
                                        message = "${medico.nombre} se quitó de Mis doctores",
                                        actionLabel = "Deshacer"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        Repositorio.alternarFavorito(medico.id)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
