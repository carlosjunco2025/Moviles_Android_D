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
import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.iconoServicio
import com.junco.huellitasvet.viewmodel.ServiciosViewModel

/** Catalogo de servicios: la pantalla solo observa el ViewModel y le avisa de los clics. */
@Composable
fun ServiciosScreen(
    vm: ServiciosViewModel,
    onEditar: (Int) -> Unit = {},
    onNuevo: () -> Unit = {}
) {
    val servicios by vm.servicios.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Column(Modifier.padding(padding)) {
            CabeceraDegradada(titulo = "Servicios", subtitulo = "${servicios.size} en el catálogo")
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(servicios, key = { it.id }) { s ->
                    ServicioItem(s, onAlternar = { vm.alternar(s) }, onEditar = { onEditar(s.id) })
                }
            }
        }
    }
}

@Composable
private fun ServicioItem(s: Servicio, onAlternar: () -> Unit, onEditar: () -> Unit) {
    TarjetaVet {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(iconoServicio(s.icono), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
            Column(Modifier.weight(1f)) {
                Text(s.nombre, fontWeight = FontWeight.Bold)
                Text(
                    "${s.duracionMin} min · ${Formato.soles(s.precio)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(checked = s.activo, onCheckedChange = { onAlternar() })
        }
    }
}
