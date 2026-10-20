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
import com.junco.huellitasvet.model.Formato
import com.junco.huellitasvet.ui.components.CabeceraDegradada
import com.junco.huellitasvet.ui.components.DatoDestacado
import com.junco.huellitasvet.ui.components.TarjetaVet
import com.junco.huellitasvet.ui.components.TituloSeccion
import com.junco.huellitasvet.ui.components.graficos.GraficoBarras
import com.junco.huellitasvet.ui.components.graficos.GraficoDona
import com.junco.huellitasvet.ui.theme.ColorAtendida
import com.junco.huellitasvet.ui.theme.ColorPendiente
import com.junco.huellitasvet.viewmodel.PanelViewModel
import java.time.LocalDate

/** Panel del veterinario: contadores, ingresos y dos graficos hechos con Canvas. */
@Composable
fun PanelScreen(
    vm: PanelViewModel,
    onVerPendientes: () -> Unit
) {
    val citasHoy by vm.citasHoy.collectAsState()
    val pendientes by vm.pendientes.collectAsState()
    val atendidas by vm.atendidas.collectAsState()
    val ingresos by vm.ingresosMes.collectAsState()
    val porEstado by vm.porEstado.collectAsState()
    val porServicio by vm.porServicio.collectAsState()

    Column(Modifier.verticalScroll(rememberScrollState())) {
        CabeceraDegradada(
            titulo = "Panel de la clínica",
            subtitulo = "Hoy · ${Formato.fechaCorta(LocalDate.now().toString())}"
        ) {
            Spacer(Modifier.height(14.dp))
            Surface(shape = RoundedCornerShape(20.dp), color = Color.White.copy(alpha = 0.16f), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Ingresos del mes", color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.labelMedium)
                    Text(Formato.soles(ingresos), color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                    Text("Suma de las citas atendidas", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DatoDestacado(citasHoy.toString(), "Citas hoy", Modifier.weight(1f))
                DatoDestacado(pendientes.toString(), "Pendientes", Modifier.weight(1f), color = ColorPendiente)
                DatoDestacado(atendidas.toString(), "Atendidas", Modifier.weight(1f), color = ColorAtendida)
            }

            TituloSeccion("Citas por estado")
            TarjetaVet { GraficoDona(porEstado) }

            TituloSeccion("Citas por servicio")
            TarjetaVet { GraficoBarras(porServicio) }

            if (pendientes > 0) {
                Surface(shape = RoundedCornerShape(20.dp), color = ColorPendiente.copy(alpha = 0.15f), modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Warning, null, tint = ColorPendiente)
                        Column(Modifier.weight(1f).padding(horizontal = 10.dp)) {
                            Text("$pendientes citas por confirmar", fontWeight = FontWeight.Bold)
                            Text("Revisa la agenda", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Button(
                            onClick = onVerPendientes,
                            colors = ButtonDefaults.buttonColors(containerColor = ColorPendiente, contentColor = Color.White)
                        ) { Text("Ver") }
                    }
                }
            }
        }
    }
}
