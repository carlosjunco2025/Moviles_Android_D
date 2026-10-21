package com.junco.huellitasvet.data.repository

import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.entity.Usuario
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.local.relation.MascotaConDueno
import com.junco.huellitasvet.model.ConteoServicio
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.PuntoPeso
import com.junco.huellitasvet.data.local.entity.Vacuna
import kotlinx.coroutines.flow.Flow

/** Contrato de datos: los ViewModels dependen de esta interface y nunca de los DAO. */
interface VetRepository {

    // ---- Servicios ----
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(servicio: Servicio)
    suspend fun eliminarServicio(servicio: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)
    suspend fun servicioTieneCitas(id: Int): Boolean

    // ---- Usuarios ----
    suspend fun login(correo: String, clave: String): Usuario?
    suspend fun obtenerUsuario(id: Int): Usuario?

    /** Devuelve null si el correo ya existe; si no, el usuario creado. */
    suspend fun registrarUsuario(nombre: String, correo: String, clave: String, rol: String): Usuario?

    // ---- Mascotas ----
    fun observarMascotasDeDueno(duenoId: Int): Flow<List<Mascota>>
    fun observarMascota(id: Int): Flow<Mascota?>
    fun observarTodasLasMascotas(): Flow<List<MascotaConDueno>>
    suspend fun guardarMascota(mascota: Mascota)
    suspend fun eliminarMascota(mascota: Mascota)
    suspend fun mascotaTieneCitas(id: Int): Boolean

    // ---- Citas ----
    fun observarHorasOcupadas(fecha: String): Flow<List<String>>

    /** Devuelve el id de la cita o -1 si el horario ya estaba tomado. */
    suspend fun crearCita(mascotaId: Int, servicioId: Int, fecha: String, hora: String, nota: String): Long
    fun observarCitasDeDueno(duenoId: Int): Flow<List<CitaConDetalle>>
    fun observarCitasPorFecha(fecha: String): Flow<List<CitaConDetalle>>
    fun observarCita(id: Int): Flow<CitaConDetalle?>
    fun observarHistorial(mascotaId: Int): Flow<List<CitaConDetalle>>
    suspend fun cambiarEstadoCita(id: Int, estado: EstadoCita)
    suspend fun registrarAtencion(
        citaId: Int, diagnostico: String, tratamiento: String, peso: Double, fechaControl: String?
    )
    fun observarControles(duenoId: Int): Flow<List<CitaConDetalle>>

    // ---- Panel ----
    fun observarCitasDeFecha(fecha: String): Flow<Int>
    fun observarCantidadPorEstado(estado: EstadoCita): Flow<Int>
    fun observarCitasPorServicio(): Flow<List<ConteoServicio>>
    fun observarIngresosDesde(fechaInicio: String): Flow<Double>

    // ---- Fase 2 ----
    fun observarEvolucionPeso(mascotaId: Int): Flow<List<PuntoPeso>>

    /** Mueve la cita a otra fecha/hora. false si el nuevo horario esta ocupado. */
    suspend fun reprogramarCita(id: Int, fecha: String, hora: String): Boolean
    fun observarVacunas(mascotaId: Int): Flow<List<Vacuna>>
    suspend fun guardarVacuna(vacuna: Vacuna)
    suspend fun eliminarVacuna(vacuna: Vacuna)
}
