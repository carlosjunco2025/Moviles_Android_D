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
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.model.Especie
import com.junco.huellitasvet.model.Sexo
import com.junco.huellitasvet.ui.components.AppTopBar
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.OpcionChip
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.VetTextField
import com.junco.huellitasvet.ui.components.etiquetaEspecie
import com.junco.huellitasvet.ui.components.etiquetaSexo
import com.junco.huellitasvet.viewmodel.MascotaViewModel

/** Crea (id = null) o edita una mascota. */
@Composable
fun FormularioMascotaScreen(
    id: Int?,
    duenoId: Int,
    vm: MascotaViewModel,
    onBack: () -> Unit
) {
    val errores by vm.errores.collectAsState()
    val flujoExistente = remember(id) { vm.mascota(id ?: -1) }
    val existente by flujoExistente.collectAsState(initial = null)

    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }

    // Al editar, los campos se rellenan una sola vez cuando llega la mascota
    LaunchedEffect(existente?.id) {
        existente?.let {
            nombre = it.nombre; especie = it.especie; raza = it.raza
            anio = it.anioNacimiento.toString(); peso = it.peso.toString(); sexo = it.sexo
        }
    }
    DisposableEffect(Unit) { onDispose { vm.limpiarErrores() } }

    Scaffold(
        topBar = { AppTopBar(if (id == null) "Nueva mascota" else "Editar mascota", onBack) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                MascotaAvatar(especie, tamano = 96.dp) // cambia al elegir la especie
            }
            VetTextField(nombre, { nombre = it }, "Nombre", error = errores["nombre"])

            Text("Especie", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Especie.entries.forEach { e ->
                    OpcionChip(etiquetaEspecie(e.name), especie == e.name, { especie = e.name }, Modifier.weight(1f))
                }
            }
            errores["especie"]?.let { TextoError(it) }

            VetTextField(raza, { raza = it }, "Raza")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VetTextField(
                    anio, { anio = it.filter(Char::isDigit).take(4) }, "Año de nacimiento",
                    Modifier.weight(1f), error = errores["anio"], teclado = KeyboardType.Number
                )
                VetTextField(
                    peso, { peso = it }, "Peso (kg)",
                    Modifier.weight(1f), error = errores["peso"], teclado = KeyboardType.Decimal
                )
            }

            Text("Sexo", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Sexo.entries.forEach { s ->
                    OpcionChip(etiquetaSexo(s.name), sexo == s.name, { sexo = s.name }, Modifier.weight(1f))
                }
            }
            errores["sexo"]?.let { TextoError(it) }

            VetButton("Guardar mascota", onClick = {
                vm.guardar(
                    Mascota(
                        id = id ?: 0,
                        duenoId = existente?.duenoId ?: duenoId,
                        nombre = nombre,
                        especie = especie,
                        raza = raza,
                        anioNacimiento = anio.toIntOrNull() ?: 0,
                        peso = peso.replace(',', '.').toDoubleOrNull() ?: 0.0,
                        sexo = sexo
                    ),
                    onBack
                )
            })
            VetButton("Cancelar", onClick = onBack, secundario = true)
        }
    }
}

@Composable
private fun TextoError(texto: String) {
    Text(texto, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
}
