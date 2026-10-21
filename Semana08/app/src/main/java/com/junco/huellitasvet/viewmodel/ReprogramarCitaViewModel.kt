package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Formato
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/** Mover una cita existente a otra fecha y hora respetando las mismas reglas que al pedirla. */
class ReprogramarCitaViewModel(
    private val repo: VetRepository,
    private val citaId: Int
) : ViewModel() {

    val cita: StateFlow<CitaConDetalle?> = repo.observarCita(citaId).comoEstado(this, null)

    private val _fecha = MutableStateFlow(LocalDate.now())
    val fecha: StateFlow<LocalDate> = _fecha.asStateFlow()

    private val _hora = MutableStateFlow<String?>(null)
    val hora: StateFlow<String?> = _hora.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    val dias: List<LocalDate> = List(8) { LocalDate.now().plusDays(it.toLong()) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val horasOcupadas: StateFlow<List<String>> = _fecha
        .flatMapLatest { repo.observarHorasOcupadas(it.toString()) }
        .comoEstado(this, emptyList())

    init {
        // Arranca en la fecha actual de la cita para que el cambio sea visible.
        viewModelScope.launch {
            repo.observarCita(citaId).first()?.let { _fecha.value = LocalDate.parse(it.cita.fecha) }
        }
    }

    fun elegirFecha(nueva: LocalDate) {
        _fecha.value = nueva
        _hora.value = null
    }

    fun elegirHora(hora: String) { _hora.value = hora }

    fun horaPasada(fecha: LocalDate, hora: String): Boolean =
        fecha == LocalDate.now() && !LocalTime.parse(hora).isAfter(LocalTime.now())

    /** El horario actual de la propia cita se muestra como libre: elegirlo no cambia nada. */
    fun ocupadaPorOtra(hora: String): Boolean {
        val actual = cita.value?.cita
        val esLaMisma = actual != null && actual.fecha == _fecha.value.toString() && actual.hora == hora
        return hora in horasOcupadas.value && !esLaMisma
    }

    fun limpiarMensaje() { _mensaje.value = null }

    fun confirmar(alMover: () -> Unit) {
        val hora = _hora.value ?: return
        val fecha = _fecha.value
        viewModelScope.launch {
            val actual = repo.observarCita(citaId).first()?.cita
            val reprogramable = actual != null &&
                (actual.estado == EstadoCita.PENDIENTE.name || actual.estado == EstadoCita.CONFIRMADA.name) &&
                Duration.between(LocalDateTime.now(), Formato.fechaHoraDe(actual.fecha, actual.hora)) > Duration.ofHours(2)
            when {
                !reprogramable -> _mensaje.value = "Esta cita ya no se puede reprogramar"
                horaPasada(fecha, hora) -> {
                    _mensaje.value = "Esa hora ya pasó. Elige otra."
                    _hora.value = null
                }
                repo.reprogramarCita(citaId, fecha.toString(), hora) -> alMover()
                else -> {
                    _mensaje.value = "Ese horario ya está ocupado. Elige otro."
                    _hora.value = null
                }
            }
        }
    }
}
