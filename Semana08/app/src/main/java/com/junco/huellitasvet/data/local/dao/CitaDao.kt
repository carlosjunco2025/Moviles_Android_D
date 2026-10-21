package com.junco.huellitasvet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.model.ConteoServicio
import com.junco.huellitasvet.model.PuntoPeso
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {

    // ---- Escritura ----
    @Insert
    suspend fun insertar(cita: Cita): Long

    @Insert
    suspend fun insertarVarias(citas: List<Cita>)

    @Query("UPDATE citas SET estado = :estado WHERE id = :id")
    suspend fun cambiarEstado(id: Int, estado: String)

    @Query("SELECT * FROM citas WHERE id = :id")
    suspend fun buscar(id: Int): Cita?

    /** Igual que antes, pero ademas guarda la fecha del proximo control (puede ser null). */
    @Query(
        "UPDATE citas SET estado = 'ATENDIDA', diagnostico = :diagnostico, " +
            "tratamiento = :tratamiento, pesoRegistrado = :peso, fechaControl = :fechaControl " +
            "WHERE id = :id"
    )
    suspend fun cerrarAtencion(
        id: Int, diagnostico: String, tratamiento: String, peso: Double, fechaControl: String?
    )

    /** Citas ATENDIDAS de un dueño que tienen proximo control, para el recordatorio de Inicio. */
    @Transaction
    @Query(
        "SELECT c.* FROM citas c INNER JOIN mascotas m ON c.mascotaId = m.id " +
            "WHERE m.duenoId = :duenoId AND c.estado = 'ATENDIDA' AND c.fechaControl IS NOT NULL " +
            "ORDER BY c.fechaControl ASC"
    )
    fun controlesDeDueno(duenoId: Int): Flow<List<CitaConDetalle>>

    // ---- Horarios ----
    @Query("SELECT COUNT(*) FROM citas WHERE fecha = :fecha AND hora = :hora AND estado != 'CANCELADA'")
    suspend fun contarEnHorario(fecha: String, hora: String): Int

    @Query("SELECT hora FROM citas WHERE fecha = :fecha AND estado != 'CANCELADA'")
    fun horasTomadas(fecha: String): Flow<List<String>>

    @Query("UPDATE citas SET fecha = :fecha, hora = :hora WHERE id = :id")
    suspend fun mover(id: Int, fecha: String, hora: String)

    // ---- Lecturas con detalle ----
    @Transaction
    @Query(
        "SELECT c.* FROM citas c INNER JOIN mascotas m ON c.mascotaId = m.id " +
            "WHERE m.duenoId = :duenoId ORDER BY c.fecha ASC, c.hora ASC"
    )
    fun deDueno(duenoId: Int): Flow<List<CitaConDetalle>>

    @Transaction
    @Query("SELECT * FROM citas WHERE fecha = :fecha ORDER BY hora ASC")
    fun deFecha(fecha: String): Flow<List<CitaConDetalle>>

    @Transaction
    @Query("SELECT * FROM citas WHERE id = :id")
    fun porId(id: Int): Flow<CitaConDetalle?>

    @Transaction
    @Query(
        "SELECT * FROM citas WHERE mascotaId = :mascotaId AND estado = 'ATENDIDA' " +
            "ORDER BY fecha DESC, hora DESC"
    )
    fun historialDe(mascotaId: Int): Flow<List<CitaConDetalle>>

    /** Puntos del grafico de peso: solo atenciones con peso registrado, de la mas vieja a la nueva. */
    @Query(
        "SELECT fecha AS fecha, pesoRegistrado AS peso FROM citas " +
            "WHERE mascotaId = :mascotaId AND estado = 'ATENDIDA' AND pesoRegistrado IS NOT NULL " +
            "ORDER BY fecha ASC, hora ASC"
    )
    fun evolucionPeso(mascotaId: Int): Flow<List<PuntoPeso>>

    /** Citas no canceladas dentro de un rango de fechas (para pintar el mes del calendario). */
    @Query(
        "SELECT fecha FROM citas WHERE fecha BETWEEN :desde AND :hasta AND estado != 'CANCELADA'"
    )
    fun fechasConCitas(desde: String, hasta: String): Flow<List<String>>

    // ---- Reglas de borrado ----
    @Query("SELECT COUNT(*) FROM citas WHERE servicioId = :servicioId")
    suspend fun cuantasDeServicio(servicioId: Int): Int

    // ---- Panel ----
    @Query("SELECT COUNT(*) FROM citas WHERE fecha = :fecha AND estado != 'CANCELADA'")
    fun totalDelDia(fecha: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM citas WHERE estado = :estado")
    fun totalConEstado(estado: String): Flow<Int>

    @Query(
        "SELECT s.nombre AS nombre, COUNT(c.id) AS total FROM citas c " +
            "INNER JOIN servicios s ON c.servicioId = s.id " +
            "GROUP BY s.id ORDER BY s.nombre ASC"
    )
    fun totalPorServicio(): Flow<List<ConteoServicio>>

    @Query(
        "SELECT COALESCE(SUM(precio), 0.0) FROM citas " +
            "WHERE estado = 'ATENDIDA' AND fecha >= :desde"
    )
    fun ingresosDesde(desde: String): Flow<Double>
}
