package com.junco.huellitasvet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.relation.MascotaConDueno
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {

    @Query("SELECT * FROM mascotas WHERE duenoId = :duenoId ORDER BY nombre ASC")
    fun deDueno(duenoId: Int): Flow<List<Mascota>>

    @Query("SELECT * FROM mascotas WHERE id = :id")
    fun porId(id: Int): Flow<Mascota?>

    @Transaction
    @Query("SELECT * FROM mascotas ORDER BY nombre ASC")
    fun todasConDueno(): Flow<List<MascotaConDueno>>

    @Query("SELECT COUNT(*) FROM citas WHERE mascotaId = :mascotaId")
    suspend fun cuantasCitas(mascotaId: Int): Int

    @Upsert
    suspend fun guardar(mascota: Mascota)

    @Insert
    suspend fun insertarVarias(mascotas: List<Mascota>)

    @Delete
    suspend fun borrar(mascota: Mascota)

    @Query("UPDATE mascotas SET peso = :peso WHERE id = :id")
    suspend fun cambiarPeso(id: Int, peso: Double)
}
