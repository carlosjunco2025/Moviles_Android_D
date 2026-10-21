package com.junco.huellitasvet.ui.veterinario

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
import com.junco.huellitasvet.model.Especie
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.OpcionChip
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.VetTextField
import com.junco.huellitasvet.ui.components.etiquetaEspecie
import com.junco.huellitasvet.viewmodel.PacientesViewModel

/** Pacientes de la clinica con buscador y filtro por especie. */
@Composable
fun PacientesScreen(vm: PacientesViewModel, onVerMascota: (Int) -> Unit) {
    val pacientes by vm.pacientes.collectAsState()
    val texto by vm.texto.collectAsState()
    val especie by vm.especie.collectAsState()
    val contadores by vm.contadores.collectAsState()

    // Cada filtro: texto del chip + especie que representa (null = todos)
    val filtros: List<Pair<String, Especie?>> = listOf(
        "Todos" to null,
        "Perros" to Especie.PERRO,
        "Gatos" to Especie.GATO,
        "Otros" to Especie.OTRO
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(Modifier.padding(padding)) {
            CabeceraDegradada(titulo = "Pacientes", subtitulo = "${pacientes.size} en la lista")
            VetTextField(
                texto, vm::cambiarTexto, "Buscar mascota o dueño…",
                Modifier.padding(horizontal = 16.dp, vertical = 10.dp), icono = Icons.Filled.Search
            )
            Row(
                Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filtros.forEach { (nombre, valor) ->
                    OpcionChip("$nombre (${contadores[valor] ?: 0})", especie == valor, { vm.cambiarEspecie(valor) })
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (pacientes.isEmpty()) {
                    item {
                        Text(
                            "No se encontraron pacientes con \"${texto.trim()}\"",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                items(pacientes, key = { it.mascota.id }) { p ->
                    TarjetaVet(onClick = { onVerMascota(p.mascota.id) }) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MascotaAvatar(p.mascota.especie, tamano = 54.dp)
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(p.mascota.nombre, fontWeight = FontWeight.Bold)
                                Text(
                                    "${etiquetaEspecie(p.mascota.especie)} · ${p.mascota.raza} · ${Formato.edad(p.mascota.anioNacimiento)} años",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    "Dueño: ${p.dueno.nombre}", style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
