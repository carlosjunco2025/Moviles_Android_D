package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.PuntoPeso
import com.junco.huellitasvet.data.local.entity.Vacuna
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

/** Alta, edicion y baja de mascotas, mas su historial clinico. */
class MascotaViewModel(private val repo: VetRepository) : ViewModel() {

    // Errores por campo: nombre, especie, sexo, anio, peso
    private val _errores = MutableStateFlow<Map<String, String>>(emptyMap())
    val errores: StateFlow<Map<String, String>> = _errores.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    // Un StateFlow por mascota, para no recrearlo en cada recomposicion.
    private val historiales = HashMap<Int, StateFlow<List<CitaConDetalle>>>()

    fun mascota(id: Int): Flow<Mascota?> = repo.observarMascota(id)

    /** Citas ATENDIDAS de la mascota, la mas reciente primero. */
    fun historial(mascotaId: Int): StateFlow<List<CitaConDetalle>> =
        historiales.getOrPut(mascotaId) {
            repo.observarHistorial(mascotaId).comoEstado(this, emptyList())
        }

    private val pesos = HashMap<Int, StateFlow<List<PuntoPeso>>>()

    /** Serie de pesos registrados en las atenciones, de la mas antigua a la mas nueva. */
    fun evolucionPeso(mascotaId: Int): StateFlow<List<PuntoPeso>> =
        pesos.getOrPut(mascotaId) {
            repo.observarEvolucionPeso(mascotaId).comoEstado(this, emptyList())
        }

    private val carnets = HashMap<Int, StateFlow<List<Vacuna>>>()

    fun vacunas(mascotaId: Int): StateFlow<List<Vacuna>> =
        carnets.getOrPut(mascotaId) {
            repo.observarVacunas(mascotaId).comoEstado(this, emptyList())
        }

    /** Nombre obligatorio; fecha y refuerzo en formato aaaa-MM-dd (el refuerzo es opcional). */
    fun agregarVacuna(mascotaId: Int, nombre: String, fecha: String, proxima: String, alTerminar: () -> Unit) {
        val hoy = LocalDate.now()
        val aplicada = runCatching { LocalDate.parse(fecha.trim()) }.getOrNull()
        val refuerzo = proxima.trim().takeIf { it.isNotEmpty() }?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
        val fallos = buildMap {
            if (nombre.trim().length < 3) put("vacuna", "Mínimo 3 caracteres")
            if (aplicada == null) put("vacunaFecha", "Usa el formato aaaa-mm-dd")
            else if (aplicada.isAfter(hoy)) put("vacunaFecha", "No puede ser futura")
            if (proxima.isNotBlank() && refuerzo == null) put("vacunaProxima", "Usa el formato aaaa-mm-dd")
            else if (refuerzo != null && aplicada != null && !refuerzo.isAfter(aplicada)) {
                put("vacunaProxima", "Debe ser posterior a la aplicación")
            }
        }
        _errores.value = fallos
        if (fallos.isNotEmpty()) return
        viewModelScope.launch {
            repo.guardarVacuna(
                Vacuna(
                    mascotaId = mascotaId,
                    nombre = nombre.trim(),
                    fecha = aplicada.toString(),
                    proximaDosis = refuerzo?.toString()
                )
            )
            alTerminar()
        }
    }

    fun quitarVacuna(vacuna: Vacuna) {
        viewModelScope.launch { repo.eliminarVacuna(vacuna) }
    }

    /** Nombre de 2+ letras, especie y sexo elegidos, año entre 1990 y hoy, peso positivo. */
    private fun validar(m: Mascota): Map<String, String> {
        val anioActual = LocalDate.now().year
        return buildMap {
            if (m.nombre.trim().length < 2) put("nombre", "Mínimo 2 caracteres")
            if (m.especie.isBlank()) put("especie", "Elige una especie")
            if (m.sexo.isBlank()) put("sexo", "Elige el sexo")
            if (m.anioNacimiento !in 1990..anioActual) put("anio", "Entre 1990 y $anioActual")
            if (m.peso <= 0.0) put("peso", "Debe ser mayor que 0")
        }
    }

    fun guardar(mascota: Mascota, alTerminar: () -> Unit) {
        val fallos = validar(mascota)
        _errores.value = fallos
        if (fallos.isNotEmpty()) return
        viewModelScope.launch {
            repo.guardarMascota(mascota.copy(nombre = mascota.nombre.trim(), raza = mascota.raza.trim()))
            alTerminar()
        }
    }

    /** Si la mascota ya tiene citas no se elimina y se avisa con un mensaje. */
    fun eliminar(mascota: Mascota, alTerminar: () -> Unit) {
        viewModelScope.launch {
            if (repo.mascotaTieneCitas(mascota.id)) {
                _mensaje.value = "No se puede eliminar a ${mascota.nombre}: tiene citas registradas"
            } else {
                repo.eliminarMascota(mascota)
                alTerminar()
            }
        }
    }

    fun limpiarErrores() { _errores.value = emptyMap() }

    fun limpiarMensaje() { _mensaje.value = null }
}
