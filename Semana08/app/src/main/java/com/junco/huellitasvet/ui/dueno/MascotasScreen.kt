package com.junco.huellitasvet.ui.dueno

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.platform.*
import androidx.compose.foundation.text.*
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.etiquetaEspecie
import com.junco.huellitasvet.viewmodel.InicioViewModel

/** Lista de las mascotas del dueño con boton flotante para agregar otra. */
@Composable
fun MascotasScreen(
    vm: InicioViewModel,
    onVerMascota: (Int) -> Unit,
    onAgregar: () -> Unit
) {
    val mascotas by vm.mascotas.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregar,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ) { Icon(Icons.Filled.Add, contentDescription = "Agregar mascota") }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(Modifier.padding(padding)) {
            CabeceraDegradada(titulo = "Mis mascotas", subtitulo = "${mascotas.size} registradas")
            if (mascotas.isEmpty()) {
                Text(
                    "Aún no tienes mascotas registradas",
                    Modifier.padding(24.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(mascotas, key = { it.id }) { m ->
                    TarjetaVet(onClick = { onVerMascota(m.id) }) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            MascotaAvatar(m.especie, tamano = 58.dp)
                            Column(Modifier.weight(1f)) {
                                Text(m.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "${etiquetaEspecie(m.especie)} · ${m.raza} · ${Formato.edad(m.anioNacimiento)} años",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
        }
    }
}
