package com.junco.huellitasvet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.junco.huellitasvet.data.local.entity.Vacuna
import kotlinx.coroutines.flow.Flow

@Dao
interface VacunaDao {

    @Query("SELECT * FROM vacunas WHERE mascotaId = :mascotaId ORDER BY fecha DESC")
    fun deMascota(mascotaId: Int): Flow<List<Vacuna>>

    @Insert
    suspend fun insertar(vacuna: Vacuna)

    @Insert
    suspend fun insertarVarias(vacunas: List<Vacuna>)

    @Delete
    suspend fun borrar(vacuna: Vacuna)
}
