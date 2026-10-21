package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.EstadoCita
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

/** Agenda diaria del veterinario. */
class AgendaViewModel(private val repo: VetRepository) : ViewModel() {

    private val _fechaSeleccionada = MutableStateFlow(LocalDate.now())
    val fechaSeleccionada: StateFlow<LocalDate> = _fechaSeleccionada.asStateFlow()

    /** Hoy y los 6 dias siguientes (chips de acceso rapido). */
    val dias: List<LocalDate> = List(7) { LocalDate.now().plusDays(it.toLong()) }

    /** Citas del dia elegido; el orden por hora lo hace la consulta. */
    @OptIn(ExperimentalCoroutinesApi::class)
    val citas: StateFlow<List<CitaConDetalle>> = _fechaSeleccionada
        .flatMapLatest { repo.observarCitasPorFecha(it.toString()) }
        .comoEstado(this, emptyList())

    fun seleccionar(fecha: LocalDate) {
        _fechaSeleccionada.value = fecha
        _mes.value = YearMonth.from(fecha)
    }

    /** Solo PENDIENTE pasa a CONFIRMADA. */
    fun confirmar(cita: Cita) {
        if (cita.estado != EstadoCita.PENDIENTE.name) return
        viewModelScope.launch { repo.cambiarEstadoCita(cita.id, EstadoCita.CONFIRMADA) }
    }

    /** Se cancela desde PENDIENTE o CONFIRMADA; ATENDIDA y CANCELADA son estados finales. */
    fun cancelar(cita: Cita) {
        val activa = cita.estado == EstadoCita.PENDIENTE.name || cita.estado == EstadoCita.CONFIRMADA.name
        if (!activa) return
        viewModelScope.launch { repo.cambiarEstadoCita(cita.id, EstadoCita.CANCELADA) }
    }

    // ---- Calendario mensual ----
    private val _mes = MutableStateFlow(YearMonth.now())
    val mes: StateFlow<YearMonth> = _mes.asStateFlow()

    /** Dias del mes visible que tienen al menos una cita no cancelada (se marcan con un punto). */
    @OptIn(ExperimentalCoroutinesApi::class)
    val diasConCitas: StateFlow<Set<LocalDate>> = _mes
        .flatMapLatest { m -> repo.observarFechasConCitas(m.atDay(1).toString(), m.atEndOfMonth().toString()) }
        .map { fechas -> fechas.map { LocalDate.parse(it) }.toSet() }
        .comoEstado(this, emptySet())

    fun mesSiguiente() { _mes.value = _mes.value.plusMonths(1) }

    fun mesAnterior() { _mes.value = _mes.value.minusMonths(1) }
}
