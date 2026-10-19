package com.junco.huellitasvet

import android.app.Application
import com.junco.huellitasvet.data.local.DatosIniciales
import com.junco.huellitasvet.data.local.VetDatabase
import com.junco.huellitasvet.data.prefs.PreferenciasStore
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.data.repository.VetRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Application: crea una sola vez la base, el repository y (despues) las preferencias.
 * Las pantallas piden estas piezas desde aqui, nunca las construyen ellas mismas.
 */
class VetApp : Application() {

    private val ambitoIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: VetDatabase by lazy { VetDatabase.obtener(this) }

    // El resto de la app solo conoce la interface; la implementacion con Room se decide aqui.
    val repository: VetRepository by lazy { VetRepositoryRoom(database) }

    // Preferencias pequeñas del dispositivo (perfil activo, modo oscuro...)
    val prefs: PreferenciasStore by lazy { PreferenciasStore(this) }

    override fun onCreate() {
        super.onCreate()
        // La siembra corre en un hilo de IO y solo actua cuando la base esta vacia.
        ambitoIO.launch { DatosIniciales.sembrar(database) }
    }
}
