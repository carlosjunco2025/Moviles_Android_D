package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.data.prefs.PreferenciasStore
import com.junco.huellitasvet.data.repository.VetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

/** Estado y reglas de la pantalla "Pedir cita". */
class PedirCitaViewModel(
    private val repo: VetRepository,
    prefs: PreferenciasStore,
    duenoId: Int,
    mascotaInicial: Int? = null
) : ViewModel() {

    val mascotas: StateFlow<List<Mascota>> =
        repo.observarMascotasDeDueno(duenoId).comoEstado(this, emptyList())

    // Solo servicios activos: los inactivos no se ofrecen al cliente.
    val servicios: StateFlow<List<Servicio>> =
        repo.observarServiciosActivos().comoEstado(this, emptyList())

    val aceptaCitas: StateFlow<Boolean> = prefs.aceptarCitas.comoEstado(this, true)

    private val _mascotaId = MutableStateFlow(mascotaInicial)
    val mascotaId: StateFlow<Int?> = _mascotaId.asStateFlow()

    private val _servicioId = MutableStateFlow<Int?>(null)
    val servicioId: StateFlow<Int?> = _servicioId.asStateFlow()

    private val _fecha = MutableStateFlow(LocalDate.now())
    val fecha: StateFlow<LocalDate> = _fecha.asStateFlow()

    private val _hora = MutableStateFlow<String?>(null)
    val hora: StateFlow<String?> = _hora.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    /** Hoy y los 7 dias siguientes. */
    val dias: List<LocalDate> = List(8) { LocalDate.now().plusDays(it.toLong()) }

    /** Horas tomadas del dia elegido; se recalcula solo cuando cambia la fecha. */
    @OptIn(ExperimentalCoroutinesApi::class)
    val horasOcupadas: StateFlow<List<String>> = _fecha
        .flatMapLatest { repo.observarHorasOcupadas(it.toString()) }
        .comoEstado(this, emptyList())

    /** "Confirmar cita" se habilita cuando ya hay mascota, servicio y hora, y la clinica acepta citas. */
    val puedeConfirmar: StateFlow<Boolean> =
        combine(_mascotaId, _servicioId, _hora, aceptaCitas) { m, s, h, abierta ->
            m != null && s != null && h != null && abierta
        }.comoEstado(this, false)

    fun elegirMascota(id: Int) { _mascotaId.value = id }

    fun elegirServicio(id: Int) { _servicioId.value = id }

    /** Al cambiar de dia se limpia la hora, porque podria no estar libre en el nuevo dia. */
    fun elegirFecha(nueva: LocalDate) {
        _fecha.value = nueva
        _hora.value = null
    }

    fun elegirHora(hora: String) { _hora.value = hora }

    /** Si la fecha es hoy, las horas que ya pasaron no se pueden elegir. */
    fun horaPasada(fecha: LocalDate, hora: String): Boolean =
        fecha == LocalDate.now() && !LocalTime.parse(hora).isAfter(LocalTime.now())

    fun limpiarMensaje() { _mensaje.value = null }

    fun confirmar(alCrear: () -> Unit) {
        val mascota = _mascotaId.value ?: return
        val servicio = _servicioId.value ?: return
        val hora = _hora.value ?: return
        val fecha = _fecha.value
        viewModelScope.launch {
            when {
                !aceptaCitas.value -> _mensaje.value = "La clínica no está recibiendo citas"
                horaPasada(fecha, hora) -> {
                    _mensaje.value = "Esa hora ya pasó. Elige otra."
                    _hora.value = null
                }
                // Ultima comprobacion: el horario pudo ocuparse mientras el usuario decidia.
                hora in repo.observarHorasOcupadas(fecha.toString()).first() -> {
                    _mensaje.value = "Ese horario se acaba de ocupar. Elige otro."
                    _hora.value = null
                }
                else -> {
                    val id = repo.crearCita(mascota, servicio, fecha.toString(), hora, "")
                    if (id > 0) {
                        alCrear()
                    } else {
                        _mensaje.value = "No se pudo crear la cita. Intenta con otro horario."
                        _hora.value = null
                    }
                }
            }
        }
    }
}
