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
import com.junco.huellitasvet.data.local.entity.Usuario
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.DatoDestacado
import com.junco.huellitasvet.ui.components.FilaSwitch
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.VetButton

/** Perfil del dueño: datos, contadores, preferencias y cierre de sesion. */
@Composable
fun PerfilScreen(
    usuario: Usuario,
    mascotas: Int,
    proximas: Int,
    atenciones: Int,
    oscuro: Boolean,
    notificaciones: Boolean,
    onOscuro: (Boolean) -> Unit,
    onNotificaciones: (Boolean) -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        CabeceraDegradada(titulo = "Mi perfil", subtitulo = "Cuenta de dueño")
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            TarjetaVet {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(60.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                usuario.nombre.take(1).uppercase(), fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Column {
                        Text(usuario.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(usuario.correo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DatoDestacado(mascotas.toString(), "Mascotas", Modifier.weight(1f))
                DatoDestacado(proximas.toString(), "Próximas", Modifier.weight(1f), color = MaterialTheme.colorScheme.secondary)
                DatoDestacado(atenciones.toString(), "Atenciones", Modifier.weight(1f))
            }

            VetButton("Cambiar de perfil", onClick = onCambiarPerfil)

            TituloSeccion("Preferencias")
            TarjetaVet {
                FilaSwitch("Modo oscuro", "Cambia el tema de la app", oscuro, onOscuro)
                FilaSwitch("Recordatorios", "Avisar antes de mis citas", notificaciones, onNotificaciones)
            }

            VetButton("Cerrar sesión", onClick = onCerrarSesion, secundario = true, peligro = true)
        }
    }
}
