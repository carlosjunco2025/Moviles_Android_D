package com.junco.huellitasvet.data.repository

import com.junco.huellitasvet.data.local.VetDatabase
import com.junco.huellitasvet.data.local.entity.Servicio
import androidx.room.withTransaction
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.entity.Usuario
import com.junco.huellitasvet.data.local.relation.CitaConDetalle
import com.junco.huellitasvet.data.local.relation.MascotaConDueno
import com.junco.huellitasvet.model.ConteoServicio
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.PuntoPeso
import com.junco.huellitasvet.data.local.entity.Vacuna
import kotlinx.coroutines.flow.Flow

/** Implementacion del contrato usando Room; cada funcion delega en el DAO correspondiente. */
class VetRepositoryRoom(private val db: VetDatabase) : VetRepository {

    private val servicios get() = db.servicioDao()
    private val usuarios get() = db.usuarioDao()
    private val mascotas get() = db.mascotaDao()
    private val citas get() = db.citaDao()
    private val vacunas get() = db.vacunaDao()

    // ---- Servicios ----
    override fun observarServicios(): Flow<List<Servicio>> = servicios.todos()

    override fun observarServiciosActivos(): Flow<List<Servicio>> = servicios.soloActivos()

    override suspend fun guardarServicio(servicio: Servicio) = servicios.guardar(servicio)

    override suspend fun eliminarServicio(servicio: Servicio) = servicios.borrar(servicio)

    override suspend fun cambiarActivo(id: Int, activo: Boolean) = servicios.marcarActivo(id, activo)

    override suspend fun servicioTieneCitas(id: Int): Boolean = citas.cuantasDeServicio(id) > 0

    // ---- Usuarios ----
    override suspend fun login(correo: String, clave: String): Usuario? =
        usuarios.buscarPorCredenciales(correo.trim(), clave)

    override suspend fun obtenerUsuario(id: Int): Usuario? = usuarios.buscarPorId(id)

    override suspend fun registrarUsuario(
        nombre: String, correo: String, clave: String, rol: String
    ): Usuario? {
        val correoLimpio = correo.trim().lowercase()
        if (usuarios.cuantosConCorreo(correoLimpio) > 0) return null
        val nuevo = Usuario(nombre = nombre.trim(), correo = correoLimpio, clave = clave, rol = rol)
        val id = usuarios.insertar(nuevo).toInt()
        return nuevo.copy(id = id)
    }

    // ---- Mascotas ----
    override fun observarMascotasDeDueno(duenoId: Int): Flow<List<Mascota>> = mascotas.deDueno(duenoId)

    override fun observarMascota(id: Int): Flow<Mascota?> = mascotas.porId(id)

    override fun observarTodasLasMascotas(): Flow<List<MascotaConDueno>> = mascotas.todasConDueno()

    override suspend fun guardarMascota(mascota: Mascota) = mascotas.guardar(mascota)

    override suspend fun eliminarMascota(mascota: Mascota) = mascotas.borrar(mascota)

    override suspend fun mascotaTieneCitas(id: Int): Boolean = mascotas.cuantasCitas(id) > 0

    // ---- Citas ----
    override fun observarHorasOcupadas(fecha: String): Flow<List<String>> = citas.horasTomadas(fecha)

    override suspend fun crearCita(
        mascotaId: Int, servicioId: Int, fecha: String, hora: String, nota: String
    ): Long {
        // Segunda barrera: aunque la pantalla ya deshabilita horas ocupadas, se revisa otra vez aqui.
        if (citas.contarEnHorario(fecha, hora) > 0) return -1L
        val servicio = servicios.buscar(servicioId) ?: return -1L
        return citas.insertar(
            Cita(
                mascotaId = mascotaId,
                servicioId = servicioId,
                fecha = fecha,
                hora = hora,
                nota = nota,
                estado = EstadoCita.PENDIENTE.name,
                precio = servicio.precio // "foto" del precio en este momento
            )
        )
    }

    override fun observarCitasDeDueno(duenoId: Int): Flow<List<CitaConDetalle>> = citas.deDueno(duenoId)

    override fun observarCitasPorFecha(fecha: String): Flow<List<CitaConDetalle>> = citas.deFecha(fecha)

    override fun observarCita(id: Int): Flow<CitaConDetalle?> = citas.porId(id)

    override fun observarHistorial(mascotaId: Int): Flow<List<CitaConDetalle>> = citas.historialDe(mascotaId)

    override suspend fun cambiarEstadoCita(id: Int, estado: EstadoCita) =
        citas.cambiarEstado(id, estado.name)

    override suspend fun registrarAtencion(
        citaId: Int, diagnostico: String, tratamiento: String, peso: Double
    ) {
        // Todo o nada: la cita y el peso de la mascota se actualizan en una sola transaccion.
        db.withTransaction {
            val cita = citas.buscar(citaId) ?: return@withTransaction
            citas.cerrarAtencion(citaId, diagnostico, tratamiento, peso)
            mascotas.cambiarPeso(cita.mascotaId, peso)
        }
    }

    // ---- Panel ----
    override fun observarCitasDeFecha(fecha: String): Flow<Int> = citas.totalDelDia(fecha)

    override fun observarCantidadPorEstado(estado: EstadoCita): Flow<Int> = citas.totalConEstado(estado.name)

    override fun observarCitasPorServicio(): Flow<List<ConteoServicio>> = citas.totalPorServicio()

    override fun observarIngresosDesde(fechaInicio: String): Flow<Double> = citas.ingresosDesde(fechaInicio)

    // ---- Fase 2 ----
    override fun observarEvolucionPeso(mascotaId: Int): Flow<List<PuntoPeso>> = citas.evolucionPeso(mascotaId)

    override suspend fun reprogramarCita(id: Int, fecha: String, hora: String): Boolean {
        val actual = citas.buscar(id) ?: return false
        // Si la cita ya esta en ese horario no hay nada que mover; si otra lo ocupa, se rechaza.
        val mismoHorario = actual.fecha == fecha && actual.hora == hora
        if (!mismoHorario && citas.contarEnHorario(fecha, hora) > 0) return false
        citas.mover(id, fecha, hora)
        return true
    }

    override fun observarVacunas(mascotaId: Int): Flow<List<Vacuna>> = vacunas.deMascota(mascotaId)

    override suspend fun guardarVacuna(vacuna: Vacuna) = vacunas.insertar(vacuna)

    override suspend fun eliminarVacuna(vacuna: Vacuna) = vacunas.borrar(vacuna)
}
