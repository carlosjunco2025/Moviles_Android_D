package com.junco.huellitasvet.data.local

import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.entity.Usuario
import com.junco.huellitasvet.model.Especie
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.model.Rol
import com.junco.huellitasvet.model.Sexo
import com.junco.huellitasvet.data.local.entity.Vacuna
import kotlinx.coroutines.flow.first
import java.time.LocalDate

/** Datos ficticios (Ley 29733) que se cargan solo la primera vez que se abre la app. */
object DatosIniciales {

    private val catalogo = listOf(
        Servicio(nombre = "Consulta general", descripcion = "Revision general de salud",
            duracionMin = 30, precio = 60.0, activo = true, icono = "heart"),
        Servicio(nombre = "Vacunación", descripcion = "Aplicación de vacunas al día",
            duracionMin = 30, precio = 45.0, activo = true, icono = "syringe"),
        Servicio(nombre = "Baño y corte", descripcion = "Higiene general y corte de pelo",
            duracionMin = 60, precio = 50.0, activo = true, icono = "scissors"),
        Servicio(nombre = "Desparasitación", descripcion = "Tratamiento antiparasitario interno y externo",
            duracionMin = 15, precio = 25.0, activo = false, icono = "heart")
    )

    /** Carga el catalogo de servicios solo si la tabla esta vacia. */
    suspend fun sembrarServicios(db: VetDatabase) {
        if (db.servicioDao().cantidad() == 0) db.servicioDao().insertarVarios(catalogo)
    }

    /** Fecha relativa a hoy en formato aaaa-MM-dd: 0 = hoy, -10 = hace diez dias, 2 = pasado mañana. */
    private fun enDias(n: Long): String = LocalDate.now().plusDays(n).toString()

    /** Siembra todo el escenario de prueba; si ya hay usuarios no toca nada. */
    suspend fun sembrar(db: VetDatabase) {
        sembrarServicios(db)
        val usuarios = db.usuarioDao()
        if (usuarios.cantidad() > 0) return

        val servicios = db.servicioDao().todos().first()
        fun servicio(nombre: String) = servicios.first { it.nombre == nombre }
        val consulta = servicio("Consulta general")
        val vacunacion = servicio("Vacunación")
        val bano = servicio("Baño y corte")

        usuarios.insertarVarios(
            listOf(
                Usuario(nombre = "Ana García", correo = "ana@correo.test", clave = "1234", rol = Rol.CLIENTE.name),
                Usuario(nombre = "Luis Rojas", correo = "luis@correo.test", clave = "1234", rol = Rol.CLIENTE.name),
                Usuario(nombre = "Dra. Rojas", correo = "vet@huellitas.test", clave = "admin", rol = Rol.ADMIN.name)
            )
        )
        val ana = usuarios.buscarPorCredenciales("ana@correo.test", "1234")!!
        val luis = usuarios.buscarPorCredenciales("luis@correo.test", "1234")!!

        val anio = LocalDate.now().year
        db.mascotaDao().insertarVarias(
            listOf(
                Mascota(duenoId = ana.id, nombre = "Luna", especie = Especie.GATO.name, raza = "Siamesa",
                    anioNacimiento = anio - 3, peso = 4.3, sexo = Sexo.HEMBRA.name),
                Mascota(duenoId = ana.id, nombre = "Max", especie = Especie.PERRO.name, raza = "Labrador",
                    anioNacimiento = anio - 5, peso = 24.5, sexo = Sexo.MACHO.name),
                Mascota(duenoId = luis.id, nombre = "Rocky", especie = Especie.PERRO.name, raza = "Bulldog",
                    anioNacimiento = anio - 6, peso = 23.8, sexo = Sexo.MACHO.name),
                Mascota(duenoId = luis.id, nombre = "Michi", especie = Especie.GATO.name, raza = "Mestizo",
                    anioNacimiento = anio - 2, peso = 3.9, sexo = Sexo.MACHO.name)
            )
        )
        // Los ids los asigna Room; los recuperamos por nombre para armar las citas.
        val mascotas = db.mascotaDao().todasConDueno().first().map { it.mascota }
        fun idDe(nombre: String) = mascotas.first { it.nombre == nombre }.id
        val luna = idDe("Luna")
        val max = idDe("Max")
        val rocky = idDe("Rocky")
        val michi = idDe("Michi")

        fun cita(
            mascotaId: Int, s: Servicio, dias: Long, hora: String, estado: EstadoCita,
            diagnostico: String? = null, tratamiento: String? = null, peso: Double? = null,
            control: String? = null
        ) = Cita(
            mascotaId = mascotaId, servicioId = s.id, fecha = enDias(dias), hora = hora,
            estado = estado.name, precio = s.precio,
            diagnostico = diagnostico, tratamiento = tratamiento, pesoRegistrado = peso,
            fechaControl = control
        )

        // 10 citas en distintos estados; nunca dos no canceladas en el mismo horario.
        db.citaDao().insertarVarias(
            listOf(
                // Historial de Luna: 3 atenciones con peso (alimenta el grafico de la Fase 2)
                cita(luna, vacunacion, -10, "10:00", EstadoCita.ATENDIDA, "Vacuna triple felina", "Refuerzo en 12 meses", 4.3),
                cita(luna, consulta, -100, "09:30", EstadoCita.ATENDIDA, "Control de rutina, sin hallazgos", "Ninguno", 4.2),
                cita(luna, bano, -200, "11:00", EstadoCita.ATENDIDA, "Higiene general", "Baño medicado suave", 4.0),
                cita(rocky, consulta, -30, "14:00", EstadoCita.ATENDIDA, "Leve dermatitis", "Shampoo especial 2 semanas", 23.8,
                    control = enDias(6)
                ),
                // Hoy
                cita(max, bano, 0, "09:00", EstadoCita.ATENDIDA, "Higiene general", "Corte de pelo", 24.5),
                cita(rocky, consulta, 0, "11:00", EstadoCita.PENDIENTE),
                cita(michi, bano, 0, "12:30", EstadoCita.PENDIENTE),
                // Proximos dias
                cita(luna, vacunacion, 1, "10:30", EstadoCita.CONFIRMADA),
                cita(max, consulta, 2, "10:00", EstadoCita.PENDIENTE),
                cita(michi, vacunacion, 3, "09:30", EstadoCita.CANCELADA)
            )
        )

        // Carnet de vacunas de Luna y Max
        db.vacunaDao().insertarVarias(
            listOf(
                Vacuna(mascotaId = luna, nombre = "Triple felina", fecha = enDias(-10), proximaDosis = enDias(355)),
                Vacuna(mascotaId = luna, nombre = "Antirrábica", fecha = enDias(-200), proximaDosis = enDias(165)),
                Vacuna(mascotaId = max, nombre = "Quíntuple canina", fecha = enDias(-60), proximaDosis = null)
            )
        )
    }
}
