package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import com.junco.huellitasvet.data.local.relation.MascotaConDueno
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.Especie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.text.Normalizer

/** Lista de pacientes filtrada por texto y por especie (combine de tres flujos). */
class PacientesViewModel(repo: VetRepository) : ViewModel() {

    private val _texto = MutableStateFlow("")
    val texto: StateFlow<String> = _texto.asStateFlow()

    /** null significa "Todos". */
    private val _especie = MutableStateFlow<Especie?>(null)
    val especie: StateFlow<Especie?> = _especie.asStateFlow()

    private val todos = repo.observarTodasLasMascotas()

    /** Quita tildes y pasa a minusculas: "Siamés" y "siames" se consideran iguales. */
    private fun normalizar(texto: String): String =
        Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
            .lowercase()

    private fun coincide(p: MascotaConDueno, buscado: String): Boolean =
        buscado.isEmpty() || listOf(p.mascota.nombre, p.mascota.raza, p.dueno.nombre)
            .any { normalizar(it).contains(buscado) }

    /** Resultado final: texto (nombre, raza o dueño) + especie. */
    val pacientes: StateFlow<List<MascotaConDueno>> =
        combine(todos, _texto, _especie) { lista, texto, especie ->
            val buscado = normalizar(texto.trim())
            lista.filter { coincide(it, buscado) && (especie == null || it.mascota.especie == especie.name) }
        }.comoEstado(this, emptyList())

    /**
     * Cantidad de pacientes por especie segun el texto escrito (el chip "Todos" usa la clave null).
     * Asi cada chip muestra cuantos resultados habria si se elige.
     */
    val contadores: StateFlow<Map<Especie?, Int>> =
        combine(todos, _texto) { lista, texto ->
            val buscado = normalizar(texto.trim())
            val visibles = lista.filter { coincide(it, buscado) }
            buildMap<Especie?, Int> {
                put(null, visibles.size)
                Especie.entries.forEach { e -> put(e, visibles.count { it.mascota.especie == e.name }) }
            }
        }.comoEstado(this, emptyMap())

    fun cambiarTexto(texto: String) { _texto.value = texto }

    fun cambiarEspecie(especie: Especie?) { _especie.value = especie }
}
