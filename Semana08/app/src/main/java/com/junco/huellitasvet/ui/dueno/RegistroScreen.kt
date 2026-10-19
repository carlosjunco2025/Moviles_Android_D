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

/** Crear cuenta nueva como dueño o como veterinario; al terminar se entra directo. */
@Composable
fun RegistroScreen(vm: SesionViewModel, onRegistrado: (Rol) -> Unit, onVolver: () -> Unit) {
    val oscuro by vm.modoOscuro.collectAsState()
    val error by vm.error.collectAsState()
    var rol by remember { mutableStateOf(Rol.CLIENTE) }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var repetida by remember { mutableStateOf("") }

    VetTheme(rol = rol, oscuro = oscuro) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                        .background(degradadoMarca())
                        .padding(start = 8.dp, end = 24.dp, top = 40.dp, bottom = 34.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { vm.limpiarError(); onVolver() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                    Column {
                        Text("Crear cuenta", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Elige tu perfil y completa tus datos", color = Color.White.copy(alpha = 0.85f))
                    }
                }

                Column(
                    Modifier.padding(horizontal = 24.dp, vertical = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SelectorRol(rol) { rol = it; vm.limpiarError() }
                    VetTextField(nombre, { nombre = it; vm.limpiarError() }, "Nombre completo", icono = Icons.Filled.Person)
                    VetTextField(
                        correo, { correo = it; vm.limpiarError() }, "Correo",
                        icono = Icons.Filled.Email, teclado = KeyboardType.Email
                    )
                    VetTextField(
                        clave, { clave = it; vm.limpiarError() }, "Contraseña (mínimo 4)",
                        icono = Icons.Filled.Lock, password = true, teclado = KeyboardType.Password
                    )
                    VetTextField(
                        repetida, { repetida = it; vm.limpiarError() }, "Repite la contraseña",
                        icono = Icons.Filled.Lock, password = true, teclado = KeyboardType.Password
                    )
                    error?.let { mensaje ->
                        Text(mensaje, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                    VetButton("Crear cuenta", onClick = { vm.registrar(nombre, correo, clave, repetida, rol, onRegistrado) })
                    VetButton("Ya tengo cuenta", onClick = { vm.limpiarError(); onVolver() }, secundario = true)
                }
            }
        }
    }
}
