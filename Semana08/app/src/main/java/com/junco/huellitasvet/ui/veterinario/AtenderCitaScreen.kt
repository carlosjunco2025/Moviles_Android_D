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
import androidx.compose.ui.text.input.KeyboardType
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.AppTopBar
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.VetTextField
import com.junco.huellitasvet.ui.components.etiquetaEspecie
import com.junco.huellitasvet.viewmodel.AtenderCitaViewModel

/** Registro clinico de una cita: peso, diagnostico, tratamiento (y proximo control). */
@Composable
fun AtenderCitaScreen(vm: AtenderCitaViewModel, onBack: () -> Unit) {
    val detalle by vm.cita.collectAsState()
    val errores by vm.errores.collectAsState()

    var peso by remember { mutableStateOf("") }
    var diagnostico by remember { mutableStateOf("") }
    var tratamiento by remember { mutableStateOf("") }
    var control by remember { mutableStateOf("") }

    // Propone el peso anterior de la mascota, una sola vez cuando llega la cita
    LaunchedEffect(detalle?.cita?.id) {
        detalle?.let { peso = it.mascota.mascota.peso.toString() }
    }
    DisposableEffect(Unit) { onDispose { vm.limpiarErrores() } }

    Scaffold(
        topBar = { AppTopBar("Atender cita", onBack) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        val d = detalle ?: return@Scaffold
        val m = d.mascota.mascota
        val pesoNuevo = peso.replace(',', '.').toDoubleOrNull() ?: 0.0

        Column(
            Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaVet {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MascotaAvatar(m.especie, tamano = 62.dp)
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("${m.nombre} · ${etiquetaEspecie(m.especie)} ${m.raza}", fontWeight = FontWeight.Bold)
                        Text(
                            "Dueño: ${d.mascota.dueno.nombre}", style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${d.servicio.nombre} · ${Formato.fechaHora(d.cita.fecha, d.cita.hora)}",
                            style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            errores["general"]?.let {
                Text(it, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }

            TituloSeccion("Registro clínico")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VetTextField(
                    peso, { peso = it }, "Peso (kg)", Modifier.weight(1f),
                    error = errores["peso"], teclado = KeyboardType.Decimal
                )
                Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.weight(1f)) {
                    Column(Modifier.padding(14.dp)) {
                        Text("Peso anterior", style = MaterialTheme.typography.labelSmall)
                        Text(Formato.peso(m.peso), fontWeight = FontWeight.Bold)
                    }
                }
            }
            VetTextField(diagnostico, { diagnostico = it }, "Diagnóstico", error = errores["diagnostico"], lineas = 3)
            VetTextField(tratamiento, { tratamiento = it }, "Tratamiento", lineas = 3)
            VetTextField(
                control, { control = it }, "Próximo control (aaaa-mm-dd, opcional)",
                error = errores["control"], icono = Icons.Filled.EventRepeat, teclado = KeyboardType.Number
            )

            if (pesoNuevo > 0.0) {
                Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Se actualizará el peso de ${m.nombre} a ${Formato.peso(pesoNuevo)}",
                        Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            VetButton("Registrar atención", onClick = { vm.registrar(diagnostico, tratamiento, pesoNuevo, control, onBack) })
            VetButton("Cancelar cita", onClick = { vm.cancelar(onBack) }, secundario = true, peligro = true)
        }
    }
}
