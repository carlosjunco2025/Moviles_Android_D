package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.ui.components.graficos.DatoGrafico
import com.junco.huellitasvet.ui.theme.ColorAtendida
import com.junco.huellitasvet.ui.theme.ColorCancelada
import com.junco.huellitasvet.ui.theme.ColorConfirmada
import com.junco.huellitasvet.ui.theme.ColorPendiente
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate

/** Panel del veterinario: contadores, ingresos del mes y datos para los dos graficos. */
class PanelViewModel(repo: VetRepository) : ViewModel() {

    private val hoy = LocalDate.now()

    val citasHoy: StateFlow<Int> = repo.observarCitasDeFecha(hoy.toString()).comoEstado(this, 0)

    private val pendientesFlow = repo.observarCantidadPorEstado(EstadoCita.PENDIENTE)
    private val confirmadasFlow = repo.observarCantidadPorEstado(EstadoCita.CONFIRMADA)
    private val atendidasFlow = repo.observarCantidadPorEstado(EstadoCita.ATENDIDA)
    private val canceladasFlow = repo.observarCantidadPorEstado(EstadoCita.CANCELADA)

    val pendientes: StateFlow<Int> = pendientesFlow.comoEstado(this, 0)
    val atendidas: StateFlow<Int> = atendidasFlow.comoEstado(this, 0)

    /** Suma de precios de las citas ATENDIDAS desde el dia 1 del mes. */
    val ingresosMes: StateFlow<Double> =
        repo.observarIngresosDesde(hoy.withDayOfMonth(1).toString()).comoEstado(this, 0.0)

    /** Conteos por estado convertidos a porciones para el grafico de dona. */
    val porEstado: StateFlow<List<DatoGrafico>> =
        combine(pendientesFlow, confirmadasFlow, atendidasFlow, canceladasFlow) { p, c, a, x ->
            listOf(
                DatoGrafico("Pendiente", p.toFloat(), ColorPendiente),
                DatoGrafico("Confirmada", c.toFloat(), ColorConfirmada),
                DatoGrafico("Atendida", a.toFloat(), ColorAtendida),
                DatoGrafico("Cancelada", x.toFloat(), ColorCancelada)
            )
        }.comoEstado(this, emptyList())

    /** Resultado del GROUP BY convertido a barras (se usa la primera palabra del servicio). */
    val porServicio: StateFlow<List<DatoGrafico>> = repo.observarCitasPorServicio()
        .map { filas -> filas.map { DatoGrafico(it.nombre.substringBefore(' '), it.total.toFloat()) } }
        .comoEstado(this, emptyList())
}
