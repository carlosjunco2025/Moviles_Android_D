package com.junco.huellitasvet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.junco.huellitasvet.data.local.dao.CitaDao
import com.junco.huellitasvet.data.local.dao.MascotaDao
import com.junco.huellitasvet.data.local.dao.ServicioDao
import com.junco.huellitasvet.data.local.dao.UsuarioDao
import com.junco.huellitasvet.data.local.entity.Cita
import com.junco.huellitasvet.data.local.entity.Mascota
import com.junco.huellitasvet.data.local.entity.Servicio
import com.junco.huellitasvet.data.local.entity.Usuario

@Database(
    entities = [Servicio::class, Usuario::class, Mascota::class, Cita::class],
    version = 2,            // paso de 1 a 2 al sumar tres tablas
    exportSchema = false
)
abstract class VetDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun mascotaDao(): MascotaDao
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile
        private var instancia: VetDatabase? = null

        /** Una sola instancia de la base para toda la app (doble chequeo con synchronized). */
        fun obtener(context: Context): VetDatabase =
            instancia ?: synchronized(this) {
                instancia ?: construir(context.applicationContext).also { instancia = it }
            }

        private fun construir(appContext: Context): VetDatabase =
            Room.databaseBuilder(appContext, VetDatabase::class.java, "huellitasvet.db")
                // Solo para el laboratorio: si cambia el esquema se recrea la base y se vuelve a sembrar.
                .fallbackToDestructiveMigration()
                .build()
    }
}
