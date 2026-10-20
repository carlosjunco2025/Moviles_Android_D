package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.PuntoPeso
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
