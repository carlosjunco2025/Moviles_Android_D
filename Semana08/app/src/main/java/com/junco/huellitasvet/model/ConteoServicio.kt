package com.junco.huellitasvet.model

/** Fila del GROUP BY del panel: cuantas citas tiene cada servicio. */
data class ConteoServicio(
    val nombre: String,
    val total: Int
)
