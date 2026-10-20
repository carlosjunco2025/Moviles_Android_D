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
import com.junco.huellitasvet.data.local.entity.Usuario
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.FilaSwitch
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.VetButton

/** Ajustes del veterinario: preferencias guardadas en DataStore y cierre de sesion. */
@Composable
fun AjustesScreen(
    usuario: Usuario,
    oscuro: Boolean,
    notificaciones: Boolean,
    aceptarCitas: Boolean,
    onOscuro: (Boolean) -> Unit,
    onNotificaciones: (Boolean) -> Unit,
    onAceptarCitas: (Boolean) -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        CabeceraDegradada(titulo = "Ajustes", subtitulo = "Cuenta de veterinario")
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            TarjetaVet {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(58.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.Person, null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Column {
                        Text(usuario.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(usuario.correo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Perfil activo", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }

            Surface(
                onClick = onCambiarPerfil,
                shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.primary,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.SwapHoriz, null, tint = MaterialTheme.colorScheme.onPrimary)
                    Column(Modifier.padding(start = 12.dp)) {
                        Text("Cambiar de perfil", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                        Text(
                            "Ir a la vista Dueño",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            TituloSeccion("Preferencias")
            TarjetaVet {
                FilaSwitch("Modo oscuro", "Cambia el tema de la app", oscuro, onOscuro)
                FilaSwitch("Notificaciones", "Avisar citas nuevas", notificaciones, onNotificaciones)
                FilaSwitch("Aceptar citas", "Abrir o cerrar la agenda", aceptarCitas, onAceptarCitas)
            }

            Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Estas preferencias se guardan en el dispositivo con DataStore.",
                    Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall
                )
            }

            VetButton("Cerrar sesión", onClick = onCerrarSesion, secundario = true, peligro = true)
        }
    }
}
