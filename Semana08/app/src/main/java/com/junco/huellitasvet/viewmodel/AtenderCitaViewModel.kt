package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.EstadoCita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

/** Registro clinico de una cita: diagnostico, tratamiento y peso. */
class AtenderCitaViewModel(
    private val repo: VetRepository,
    private val citaId: Int
) : ViewModel() {

    val cita: StateFlow<CitaConDetalle?> = repo.observarCita(citaId).comoEstado(this, null)

    // Errores por campo: diagnostico, peso, general
    private val _errores = MutableStateFlow<Map<String, String>>(emptyMap())
    val errores: StateFlow<Map<String, String>> = _errores.asStateFlow()

    private fun sePuedeAtender(estado: String) =
        estado == EstadoCita.PENDIENTE.name || estado == EstadoCita.CONFIRMADA.name

    /**
     * Igual que antes y ademas guarda el proximo control (aaaa-MM-dd, opcional).
     * Si se escribe, debe ser una fecha valida posterior a hoy.
     */
    fun registrar(
        diagnostico: String, tratamiento: String, peso: Double, fechaControl: String,
        alTerminar: () -> Unit
    ) {
        viewModelScope.launch {
            val actual = repo.observarCita(citaId).first()
            val control = fechaControl.trim().takeIf { it.isNotEmpty() }
            val fechaValida = control?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
            val fallos = buildMap {
                if (actual == null || !sePuedeAtender(actual.cita.estado)) {
                    put("general", "Solo se puede atender una cita pendiente o confirmada")
                }
                if (diagnostico.trim().length < 5) put("diagnostico", "Mínimo 5 caracteres")
                if (peso <= 0.0) put("peso", "Debe ser mayor que 0")
                if (control != null && fechaValida == null) put("control", "Usa el formato aaaa-mm-dd")
                else if (fechaValida != null && !fechaValida.isAfter(LocalDate.now())) {
                    put("control", "Debe ser una fecha futura")
                }
            }
            _errores.value = fallos
            if (fallos.isNotEmpty()) return@launch
            repo.registrarAtencion(citaId, diagnostico.trim(), tratamiento.trim(), peso, fechaValida?.toString())
            alTerminar()
        }
    }

    fun cancelar(alTerminar: () -> Unit) {
        viewModelScope.launch {
            val actual = repo.observarCita(citaId).first()
            if (actual != null && sePuedeAtender(actual.cita.estado)) {
                repo.cambiarEstadoCita(citaId, EstadoCita.CANCELADA)
                alTerminar()
            } else {
                _errores.value = mapOf("general" to "Esta cita ya no se puede cancelar")
            }
        }
    }

    fun limpiarErrores() { _errores.value = emptyMap() }
}
