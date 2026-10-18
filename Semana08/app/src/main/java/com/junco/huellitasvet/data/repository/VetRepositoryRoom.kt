package com.junco.huellitasvet.data.repository

import com.junco.huellitasvet.data.local.VetDatabase
import com.junco.huellitasvet.data.local.entity.Servicio
import kotlinx.coroutines.flow.Flow

/** Implementacion del contrato usando Room; cada funcion delega en el DAO correspondiente. */
class VetRepositoryRoom(private val db: VetDatabase) : VetRepository {

    private val servicios get() = db.servicioDao()

    // ---- Servicios ----
    override fun observarServicios(): Flow<List<Servicio>> = servicios.todos()

    override fun observarServiciosActivos(): Flow<List<Servicio>> = servicios.soloActivos()

    override suspend fun guardarServicio(servicio: Servicio) = servicios.guardar(servicio)

    override suspend fun eliminarServicio(servicio: Servicio) = servicios.borrar(servicio)

    override suspend fun cambiarActivo(id: Int, activo: Boolean) = servicios.marcarActivo(id, activo)
}
