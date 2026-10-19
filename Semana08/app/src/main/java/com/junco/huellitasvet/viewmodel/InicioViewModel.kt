package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Formato
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

/** Pantalla de inicio del dueño: sus mascotas y su proxima cita. */
class InicioViewModel(repo: VetRepository, duenoId: Int) : ViewModel() {

    val mascotas: StateFlow<List<Mascota>> =
        repo.observarMascotasDeDueno(duenoId).comoEstado(this, emptyList())

    /**
     * Proxima cita: la primera PENDIENTE o CONFIRMADA desde este momento.
     * La regla vive aqui, no en la pantalla ni en el DAO.
     */
    val proximaCita: StateFlow<CitaConDetalle?> = repo.observarCitasDeDueno(duenoId)
        .map { lista ->
            val ahora = LocalDateTime.now()
            lista
                .filter { it.cita.estado.let { e -> e == EstadoCita.PENDIENTE.name || e == EstadoCita.CONFIRMADA.name } }
                .filter { !Formato.fechaHoraDe(it.cita.fecha, it.cita.hora).isBefore(ahora) }
                .minByOrNull { Formato.fechaHoraDe(it.cita.fecha, it.cita.hora) }
        }
        .comoEstado(this, null)
}
