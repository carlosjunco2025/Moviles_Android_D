package com.junco.huellitasvet.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.entity.Usuario

/** Mascota junto con su dueño (relacion 1 a N vista desde la mascota). */
data class MascotaConDueno(
    @Embedded val mascota: Mascota,
    @Relation(parentColumn = "duenoId", entityColumn = "id")
    val dueno: Usuario
)
