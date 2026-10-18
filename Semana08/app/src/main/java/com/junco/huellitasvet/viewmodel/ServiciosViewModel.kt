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
}
