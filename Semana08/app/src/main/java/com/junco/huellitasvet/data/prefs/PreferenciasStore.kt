package com.junco.huellitasvet.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Una sola instancia del DataStore por archivo, declarada fuera de la clase.
private val Context.almacen: DataStore<Preferences> by preferencesDataStore(name = "ajustes")

/**
 * Preferencias pequeñas del dispositivo (clave-valor).
 * Mascotas, citas y servicios NO van aqui: viven en Room.
 */
class PreferenciasStore(private val context: Context) {

    private companion object {
        val PERFIL = stringPreferencesKey("perfilActivo")
        val USUARIO = intPreferencesKey("usuarioId")
        val OSCURO = booleanPreferencesKey("modoOscuro")
        val AVISOS = booleanPreferencesKey("notificaciones")
        val ACEPTAR = booleanPreferencesKey("aceptarCitas")
    }

    private val datos: Flow<Preferences> get() = context.almacen.data

    /** "CLIENTE", "ADMIN" o cadena vacia cuando no hay sesion. */
    val perfilActivo: Flow<String> = datos.map { it[PERFIL].orEmpty() }

    /** Id del usuario con sesion; 0 si no hay. */
    val usuarioId: Flow<Int> = datos.map { it[USUARIO] ?: 0 }

    val modoOscuro: Flow<Boolean> = datos.map { it[OSCURO] ?: false }

    val notificaciones: Flow<Boolean> = datos.map { it[AVISOS] ?: true }

    val aceptarCitas: Flow<Boolean> = datos.map { it[ACEPTAR] ?: true }

    suspend fun guardarSesion(rol: String, usuarioId: Int) {
        context.almacen.edit { p ->
            p[PERFIL] = rol
            p[USUARIO] = usuarioId
        }
    }

    suspend fun guardarModoOscuro(valor: Boolean) {
        context.almacen.edit { it[OSCURO] = valor }
    }

    suspend fun guardarNotificaciones(valor: Boolean) {
        context.almacen.edit { it[AVISOS] = valor }
    }

    suspend fun guardarAceptarCitas(valor: Boolean) {
        context.almacen.edit { it[ACEPTAR] = valor }
    }

    /** Borra solo la sesion; los demas ajustes se conservan. */
    suspend fun cerrarSesion() {
        context.almacen.edit { p ->
            p.remove(PERFIL)
            p.remove(USUARIO)
        }
    }
}
