package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.data.repository.VetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiciosViewModel(private val repo: VetRepository) : ViewModel() {

    val servicios: StateFlow<List<Servicio>> = repo.observarServicios().comoEstado(this, emptyList())

    fun alternar(servicio: Servicio) {
        viewModelScope.launch { repo.cambiarActivo(servicio.id, !servicio.activo) }
    }

    // Errores por campo: "nombre" y "precio"
    private val _errores = MutableStateFlow<Map<String, String>>(emptyMap())
    val errores: StateFlow<Map<String, String>> = _errores.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    /** Crea (id = 0) o edita un servicio. Nombre obligatorio y precio mayor que 0. */
    fun guardar(servicio: Servicio, alTerminar: () -> Unit) {
        val fallos = buildMap {
            if (servicio.nombre.isBlank()) put("nombre", "El nombre es obligatorio")
            if (servicio.precio <= 0.0) put("precio", "Debe ser mayor que 0")
        }
        _errores.value = fallos
        if (fallos.isNotEmpty()) return
        viewModelScope.launch {
            repo.guardarServicio(
                servicio.copy(nombre = servicio.nombre.trim(), descripcion = servicio.descripcion.trim())
            )
            alTerminar()
        }
    }

    /** Regla: un servicio que ya tiene citas no se borra; solo se puede desactivar. */
    fun eliminar(servicio: Servicio, alTerminar: () -> Unit) {
        viewModelScope.launch {
            if (repo.servicioTieneCitas(servicio.id)) {
                _mensaje.value = "No se puede eliminar \"${servicio.nombre}\": tiene citas. Puedes desactivarlo."
            } else {
                repo.eliminarServicio(servicio)
                alTerminar()
            }
        }
    }

    fun limpiarErrores() { _errores.value = emptyMap() }

    fun limpiarMensaje() { _mensaje.value = null }
}
