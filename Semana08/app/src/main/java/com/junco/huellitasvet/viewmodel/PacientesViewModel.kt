package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import com.junco.huellitasvet.data.local.relation.MascotaConDueno
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.Especie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

/** Lista de pacientes filtrada por texto y por especie (combine de tres flujos). */
class PacientesViewModel(repo: VetRepository) : ViewModel() {

    private val _texto = MutableStateFlow("")
    val texto: StateFlow<String> = _texto.asStateFlow()

    /** null significa "Todos". */
    private val _especie = MutableStateFlow<Especie?>(null)
    val especie: StateFlow<Especie?> = _especie.asStateFlow()

    val pacientes: StateFlow<List<MascotaConDueno>> =
        combine(repo.observarTodasLasMascotas(), _texto, _especie) { lista, texto, especie ->
            val buscado = texto.trim()
            lista.filter { p ->
                val coincideTexto = buscado.isEmpty() ||
                    p.mascota.nombre.contains(buscado, ignoreCase = true) ||
                    p.dueno.nombre.contains(buscado, ignoreCase = true)
                coincideTexto && (especie == null || p.mascota.especie == especie.name)
            }
        }.comoEstado(this, emptyList())

    fun cambiarTexto(texto: String) { _texto.value = texto }

    fun cambiarEspecie(especie: Especie?) { _especie.value = especie }
}
