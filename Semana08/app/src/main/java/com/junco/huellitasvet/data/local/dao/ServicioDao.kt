package com.junco.huellitasvet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.junco.huellitasvet.data.local.entity.Servicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {

    @Query("SELECT * FROM servicios ORDER BY nombre ASC")
    fun todos(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios WHERE activo = 1 ORDER BY nombre ASC")
    fun soloActivos(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios WHERE id = :id LIMIT 1")
    suspend fun buscar(id: Int): Servicio?

    @Query("SELECT COUNT(*) FROM servicios")
    suspend fun cantidad(): Int

    /** Inserta si el id es 0 y actualiza si ya existe: sirve para crear y editar. */
    @Upsert
    suspend fun guardar(servicio: Servicio)

    @Insert
    suspend fun insertarVarios(servicios: List<Servicio>)

    @Delete
    suspend fun borrar(servicio: Servicio)

    @Query("UPDATE servicios SET activo = :activo WHERE id = :id")
    suspend fun marcarActivo(id: Int, activo: Boolean)
}
