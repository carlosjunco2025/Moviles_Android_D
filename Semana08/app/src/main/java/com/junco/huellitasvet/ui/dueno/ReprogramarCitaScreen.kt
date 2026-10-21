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
import com.junco.huellitasvet.ui.components.AppTopBar
import com.junco.huellitasvet.ui.components.SelectorFechaHora
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.VetButton
import com.junco.huellitasvet.viewmodel.ReprogramarCitaViewModel

/** Elegir nueva fecha y hora para una cita existente. Los horarios ocupados salen en gris. */
@Composable
fun ReprogramarCitaScreen(
    vm: ReprogramarCitaViewModel,
    onBack: () -> Unit,
    onListo: () -> Unit
) {
    val cita by vm.cita.collectAsState()
    val fecha by vm.fecha.collectAsState()
    val hora by vm.hora.collectAsState()
    val ocupadas by vm.horasOcupadas.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(mensaje) {
        mensaje?.let { snackbar.showSnackbar(it); vm.limpiarMensaje() }
    }

    Scaffold(
        topBar = { AppTopBar("Reprogramar cita", onBack) },
        snackbarHost = { SnackbarHost(snackbar) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        // let evita el smart cast sobre una propiedad delegada
        val actual = cita ?: return@Scaffold
        Column(
            Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            TarjetaVet {
                Text("Cita actual", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    "${actual.mascota.mascota.nombre} · ${actual.servicio.nombre}",
                    fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    Formato.fechaHora(actual.cita.fecha, actual.cita.hora),
                    color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold
                )
            }

            TituloSeccion("Nueva fecha y hora")
            SelectorFechaHora(
                dias = vm.dias,
                fecha = fecha,
                hora = hora,
                noDisponible = { h -> (h in ocupadas && !(actual.cita.fecha == fecha.toString() && actual.cita.hora == h)) || vm.horaPasada(fecha, h) },
                onFecha = vm::elegirFecha,
                onHora = vm::elegirHora
            )

            VetButton("Confirmar cambio", onClick = { vm.confirmar(onListo) }, enabled = hora != null)
            VetButton("Cancelar", onClick = onBack, secundario = true)
        }
    }
}
