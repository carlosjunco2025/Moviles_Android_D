package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junco.huellitasvet.data.local.entity.Usuario
import com.junco.huellitasvet.data.prefs.PreferenciasStore
import com.junco.huellitasvet.data.repository.VetRepository
import com.junco.huellitasvet.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Quien esta dentro de la app, errores de login/registro y preferencias del dispositivo. */
class SesionViewModel(
    private val repo: VetRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Estas tres se leen del DataStore; Eagerly para que el tema oscuro aplique desde el primer frame.
    val modoOscuro: StateFlow<Boolean> =
        prefs.modoOscuro.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val notificaciones: StateFlow<Boolean> =
        prefs.notificaciones.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    val aceptarCitas: StateFlow<Boolean> =
        prefs.aceptarCitas.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    init {
        // Si quedo una sesion guardada, se recupera el usuario para que el Splash salte el login.
        viewModelScope.launch {
            val id = prefs.usuarioId.first()
            if (id > 0) _usuarioActual.value = repo.obtenerUsuario(id)
        }
    }

    /** Valida campos, credenciales y que la cuenta corresponda a la pestaña elegida. */
    fun login(correo: String, clave: String, rolElegido: Rol, alEntrar: (Rol) -> Unit) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Completa todos los campos"
            return
        }
        viewModelScope.launch {
            val usuario = repo.login(correo, clave)
            if (usuario == null) {
                _error.value = "Correo o contraseña incorrectos"
                return@launch
            }
            if (usuario.rol != rolElegido.name) {
                _error.value = if (rolElegido == Rol.CLIENTE) "Esta cuenta no es de dueño"
                else "Esta cuenta no es de veterinario"
                return@launch
            }
            iniciarSesion(usuario, alEntrar)
        }
    }

    /** Crea una cuenta nueva (dueño o veterinario) y entra directo con ella. */
    fun registrar(
        nombre: String, correo: String, clave: String, repetida: String,
        rol: Rol, alEntrar: (Rol) -> Unit
    ) {
        val mensaje = when {
            nombre.trim().length < 3 -> "Escribe tu nombre completo"
            !correo.trim().matches(Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) -> "Correo no válido"
            clave.length < 4 -> "La contraseña debe tener al menos 4 caracteres"
            clave != repetida -> "Las contraseñas no coinciden"
            else -> null
        }
        if (mensaje != null) {
            _error.value = mensaje
            return
        }
        viewModelScope.launch {
            val creado = repo.registrarUsuario(nombre, correo, clave, rol.name)
            if (creado == null) _error.value = "Ese correo ya está registrado"
            else iniciarSesion(creado, alEntrar)
        }
    }

    private suspend fun iniciarSesion(usuario: Usuario, alEntrar: (Rol) -> Unit) {
        prefs.guardarSesion(usuario.rol, usuario.id)
        _usuarioActual.value = usuario
        _error.value = null
        alEntrar(Rol.valueOf(usuario.rol))
    }

    fun limpiarError() { _error.value = null }

    /** Borra la sesion guardada (el modo oscuro se conserva) y avisa para volver al login. */
    fun cerrarSesion(alTerminar: () -> Unit) {
        viewModelScope.launch {
            prefs.cerrarSesion()
            _usuarioActual.value = null
            _error.value = null
            alTerminar()
        }
    }

    /** Cambia entre el perfil dueño y veterinario usando las cuentas de prueba sembradas. */
    fun cambiarPerfil(alTerminar: (Rol) -> Unit) {
        val actual = _usuarioActual.value ?: return
        val (correo, clave) = if (actual.rol == Rol.CLIENTE.name) {
            "vet@huellitas.test" to "admin"
        } else {
            "ana@correo.test" to "1234"
        }
        viewModelScope.launch {
            val otro = repo.login(correo, clave)
            if (otro == null) {
                _error.value = "No se encontró el otro perfil"
            } else {
                iniciarSesion(otro, alTerminar)
            }
        }
    }

    fun cambiarModoOscuro(valor: Boolean) { viewModelScope.launch { prefs.guardarModoOscuro(valor) } }

    fun cambiarNotificaciones(valor: Boolean) { viewModelScope.launch { prefs.guardarNotificaciones(valor) } }

    fun cambiarAceptarCitas(valor: Boolean) { viewModelScope.launch { prefs.guardarAceptarCitas(valor) } }
}
