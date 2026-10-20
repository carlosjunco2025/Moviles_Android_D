package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Formato
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

/** Citas del dueño separadas en Proximas e Historial, con la regla de cancelacion de 2 horas. */
class MisCitasViewModel(
    private val repo: VetRepository,
    duenoId: Int
) : ViewModel() {

    private val activas = setOf(EstadoCita.PENDIENTE.name, EstadoCita.CONFIRMADA.name)

    private fun esProxima(c: CitaConDetalle, ahora: LocalDateTime): Boolean =
        c.cita.estado in activas && !Formato.fechaHoraDe(c.cita.fecha, c.cita.hora).isBefore(ahora)

    private val todas = repo.observarCitasDeDueno(duenoId)

    /** PENDIENTE o CONFIRMADA desde ahora; la mas cercana primero. */
    val proximas: StateFlow<List<CitaConDetalle>> = todas
        .map { lista -> LocalDateTime.now().let { ahora -> lista.filter { esProxima(it, ahora) } } }
        .comoEstado(this, emptyList())

    /** ATENDIDA, CANCELADA o ya vencidas; la mas reciente primero. */
    val historial: StateFlow<List<CitaConDetalle>> = todas
        .map { lista -> LocalDateTime.now().let { ahora -> lista.filterNot { esProxima(it, ahora) }.asReversed() } }
        .comoEstado(this, emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    /** Solo se cancela una cita PENDIENTE o CONFIRMADA y unicamente si faltan MAS de 2 horas. */
    fun puedeCancelar(cita: Cita): Boolean {
        val faltan = Duration.between(LocalDateTime.now(), Formato.fechaHoraDe(cita.fecha, cita.hora))
        return cita.estado in activas && faltan > Duration.ofHours(2)
    }

    fun cancelar(cita: Cita) {
        if (!puedeCancelar(cita)) {
            _mensaje.value = "Ya no puedes cancelar esta cita"
            return
        }
        viewModelScope.launch { repo.cambiarEstadoCita(cita.id, EstadoCita.CANCELADA) }
    }

    fun limpiarMensaje() { _mensaje.value = null }
}
