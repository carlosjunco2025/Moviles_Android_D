package com.junco.huellitasvet.model

/** Perfil con el que se entra a la app. */
enum class Rol { CLIENTE, ADMIN }

/** Ciclo de vida de una cita. */
enum class EstadoCita {
    PENDIENTE, CONFIRMADA, ATENDIDA, CANCELADA;

    /** Una cita activa todavia puede confirmarse, atenderse o cancelarse. */
    val esActiva: Boolean
        get() = this == PENDIENTE || this == CONFIRMADA
}

enum class Especie { PERRO, GATO, OTRO }

enum class Sexo { MACHO, HEMBRA }
