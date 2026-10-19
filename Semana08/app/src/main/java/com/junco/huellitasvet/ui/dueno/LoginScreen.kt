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
import androidx.compose.ui.text.input.KeyboardType
import com.junco.huellitasvet.model.Rol
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.VetTextField
import com.junco.huellitasvet.ui.theme.VetTheme
import com.junco.huellitasvet.ui.theme.degradadoMarca
import com.junco.huellitasvet.viewmodel.SesionViewModel

/** Una sola pantalla para los dos perfiles: la pestaña elegida cambia los colores. */
@Composable
fun LoginScreen(vm: SesionViewModel, onLogin: (Rol) -> Unit, onRegistro: () -> Unit) {
    val oscuro by vm.modoOscuro.collectAsState()
    val error by vm.error.collectAsState()
    var rol by remember { mutableStateOf(Rol.CLIENTE) }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    VetTheme(rol = rol, oscuro = oscuro) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                // Franja superior con degradado y logo
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                        .background(degradadoMarca())
                        .padding(start = 24.dp, end = 24.dp, top = 56.dp, bottom = 44.dp)
                ) {
                    Surface(shape = RoundedCornerShape(18.dp), color = Color.White, modifier = Modifier.size(52.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.Pets, null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Bienvenido", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                    Text("Ingresa para continuar", color = Color.White.copy(alpha = 0.85f))
                }

                Column(
                    Modifier.padding(horizontal = 24.dp, vertical = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    SelectorRol(rol) { rol = it; vm.limpiarError() }

                    VetTextField(
                        valor = correo, onCambio = { correo = it; vm.limpiarError() },
                        etiqueta = "Correo", icono = Icons.Filled.Email, teclado = KeyboardType.Email
                    )
                    VetTextField(
                        valor = clave, onCambio = { clave = it; vm.limpiarError() },
                        etiqueta = "Contraseña", icono = Icons.Filled.Lock,
                        password = true, teclado = KeyboardType.Password
                    )

                    error?.let { mensaje ->
                        Text(mensaje, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    VetButton("Ingresar", onClick = { vm.login(correo, clave, rol, onLogin) })

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text("¿No tienes cuenta?", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        TextButton(onClick = { vm.limpiarError(); onRegistro() }) {
                            Text("Regístrate", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

/** Interruptor de dos opciones: Dueño | Veterinario. Se reutiliza en el registro. */
@Composable
fun SelectorRol(seleccionado: Rol, onElegir: (Rol) -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(4.dp)) {
            listOf(Rol.CLIENTE to "Dueño", Rol.ADMIN to "Veterinario").forEach { (opcion, texto) ->
                val activo = opcion == seleccionado
                Surface(
                    onClick = { onElegir(opcion) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        texto,
                        modifier = Modifier.padding(vertical = 11.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = if (activo) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
