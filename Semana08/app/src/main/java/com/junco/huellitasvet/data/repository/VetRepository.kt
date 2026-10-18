package com.junco.huellitasvet.data.repository

import com.junco.huellitasvet.data.local.entity.Servicio
import kotlinx.coroutines.flow.Flow

/** Contrato de datos: los ViewModels dependen de esta interface y nunca de los DAO. */
interface VetRepository {

    // ---- Servicios ----
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(servicio: Servicio)
    suspend fun eliminarServicio(servicio: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)
}
