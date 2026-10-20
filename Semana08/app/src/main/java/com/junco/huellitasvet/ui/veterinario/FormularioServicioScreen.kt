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
import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.ui.components.AppTopBar
import com.junco.huellitasvet.ui.components.FilaSwitch
import com.junco.huellitasvet.ui.components.OpcionChip
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.ui.components.VetTextField
import com.junco.huellitasvet.ui.components.iconoServicio
import com.junco.huellitasvet.viewmodel.ServiciosViewModel

/** Crea (id = null) o edita un servicio. */
@Composable
fun FormularioServicioScreen(id: Int?, vm: ServiciosViewModel, onBack: () -> Unit) {
    val servicios by vm.servicios.collectAsState()
    val errores by vm.errores.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val snackbar = remember { SnackbarHostState() }
    val existente = servicios.firstOrNull { it.id == id }

    var nombre by remember { mutableStateOf("") }
    var duracion by remember { mutableIntStateOf(30) }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var icono by remember { mutableStateOf("heart") }
    var activo by remember { mutableStateOf(true) }

    // Al editar, los campos se rellenan una sola vez cuando llega el servicio
    LaunchedEffect(existente?.id) {
        existente?.let {
            nombre = it.nombre; duracion = it.duracionMin; precio = it.precio.toString()
            descripcion = it.descripcion; icono = it.icono; activo = it.activo
        }
    }
    LaunchedEffect(mensaje) {
        mensaje?.let { snackbar.showSnackbar(it); vm.limpiarMensaje() }
    }
    DisposableEffect(Unit) { onDispose { vm.limpiarErrores() } }

    Scaffold(
        topBar = { AppTopBar(if (id == null) "Nuevo servicio" else "Editar servicio", onBack) },
        snackbarHost = { SnackbarHost(snackbar) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VetTextField(nombre, { nombre = it }, "Nombre del servicio", error = errores["nombre"])

            Text("Duración", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(15, 30, 45, 60).forEach { minutos ->
                    OpcionChip("$minutos min", duracion == minutos, { duracion = minutos }, Modifier.weight(1f))
                }
            }

            VetTextField(precio, { precio = it }, "Precio (S/)", error = errores["precio"], teclado = KeyboardType.Decimal)
            VetTextField(descripcion, { descripcion = it }, "Descripción", lineas = 3)

            Text("Ícono", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("heart", "syringe", "scissors").forEach { clave ->
                    val elegido = icono == clave
                    Surface(
                        onClick = { icono = clave },
                        shape = RoundedCornerShape(16.dp),
                        color = if (elegido) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.5.dp, if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
                        modifier = Modifier.weight(1f).height(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(iconoServicio(clave), null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxWidth()) {
                FilaSwitch("Servicio activo", "Los dueños pueden pedirlo", activo, { activo = it }, Modifier.padding(horizontal = 14.dp))
            }

            VetButton("Guardar servicio", onClick = {
                vm.guardar(
                    Servicio(
                        id = existente?.id ?: 0,
                        nombre = nombre,
                        descripcion = descripcion,
                        duracionMin = duracion,
                        precio = precio.replace(',', '.').toDoubleOrNull() ?: 0.0,
                        activo = activo,
                        icono = icono
                    ),
                    onBack
                )
            })
            VetButton("Cancelar", onClick = onBack, secundario = true)
            if (existente != null) {
                VetButton("Eliminar servicio", onClick = { vm.eliminar(existente, onBack) }, secundario = true, peligro = true)
            }
        }
    }
}
