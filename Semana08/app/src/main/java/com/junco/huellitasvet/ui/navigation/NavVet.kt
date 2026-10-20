package com.junco.huellitasvet.ui.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.platform.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.junco.huellitasvet.VetApp
import com.junco.huellitasvet.model.Rol
import com.junco.huellitasvet.ui.dueno.SplashScreen
import com.junco.huellitasvet.ui.theme.VetTheme
import com.junco.huellitasvet.ui.veterinario.ServiciosScreen
import com.junco.huellitasvet.viewmodel.ServiciosViewModel
import com.junco.huellitasvet.viewmodel.VetViewModelFactory
import com.junco.huellitasvet.ui.dueno.LoginScreen
import com.junco.huellitasvet.ui.dueno.RegistroScreen
import com.junco.huellitasvet.viewmodel.SesionViewModel
import com.junco.huellitasvet.ui.dueno.InicioScreen
import com.junco.huellitasvet.ui.dueno.MascotasScreen
import com.junco.huellitasvet.viewmodel.InicioViewModel
import com.junco.huellitasvet.ui.dueno.FichaMascotaScreen
import com.junco.huellitasvet.ui.dueno.FormularioMascotaScreen
import com.junco.huellitasvet.viewmodel.MascotaViewModel
import com.junco.huellitasvet.ui.dueno.PedirCitaScreen
import com.junco.huellitasvet.viewmodel.PedirCitaViewModel
import com.junco.huellitasvet.ui.dueno.MisCitasScreen
import com.junco.huellitasvet.viewmodel.MisCitasViewModel
import com.junco.huellitasvet.ui.veterinario.AgendaScreen
import com.junco.huellitasvet.ui.veterinario.PanelScreen
import com.junco.huellitasvet.viewmodel.AgendaViewModel
import com.junco.huellitasvet.viewmodel.PanelViewModel
import com.junco.huellitasvet.ui.veterinario.AtenderCitaScreen
import com.junco.huellitasvet.ui.veterinario.PacientesScreen
import com.junco.huellitasvet.viewmodel.AtenderCitaViewModel
import com.junco.huellitasvet.viewmodel.PacientesViewModel
import com.junco.huellitasvet.model.EstadoCita
import com.junco.huellitasvet.ui.dueno.PerfilScreen
import com.junco.huellitasvet.ui.veterinario.AjustesScreen
import com.junco.huellitasvet.ui.veterinario.FormularioServicioScreen

/** Una pestaña de la barra inferior. */
private data class Pestana(val ruta: String, val titulo: String, val icono: ImageVector)

@Composable
fun NavVet() {
    val app = LocalContext.current.applicationContext as VetApp
    val repo = app.repository
    val nav = rememberNavController()

    // La sesion se comparte con toda la app (usuario, modo oscuro y preferencias)
    val sesionVm: SesionViewModel = viewModel(
        factory = VetViewModelFactory { SesionViewModel(repo, app.prefs) }
    )
    val usuario by sesionVm.usuarioActual.collectAsState()
    val oscuro by sesionVm.modoOscuro.collectAsState()
    val rolTema = if (usuario?.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE
    val rutaActual = nav.currentBackStackEntryAsState().value?.destination?.route

    val pestanasDueno = listOf<Pestana>(
        Pestana(Rutas.INICIO, "Inicio", Icons.Filled.Home),
        Pestana(Rutas.MASCOTAS, "Mascotas", Icons.Filled.Pets),
        Pestana(Rutas.MIS_CITAS, "Citas", Icons.Filled.CalendarMonth),
        Pestana(Rutas.PERFIL, "Perfil", Icons.Filled.Person),
    )
    val pestanasVet = listOf<Pestana>(
        Pestana(Rutas.PANEL, "Panel", Icons.Filled.BarChart),
        Pestana(Rutas.AGENDA, "Agenda", Icons.Filled.CalendarMonth),
        Pestana(Rutas.PACIENTES, "Pacientes", Icons.Filled.Pets),
        Pestana(Rutas.SERVICIOS, "Servicios", Icons.Filled.LocalOffer),
        Pestana(Rutas.AJUSTES, "Ajustes", Icons.Filled.Settings),
    )
    val rutasDueno = pestanasDueno.map { it.ruta }
    val rutasVet = pestanasVet.map { it.ruta }
    val pestanas: List<Pestana> = when (rutaActual) {
        in rutasDueno -> pestanasDueno
        in rutasVet -> pestanasVet
        else -> emptyList()
    }

    /** Lleva a la pantalla principal del perfil y vacia la pila de navegacion. */
    fun irAInicio(rol: Rol) {
        val destino = if (rol == Rol.CLIENTE) Rutas.INICIO else Rutas.PANEL
        nav.navigate(destino) {
            popUpTo(nav.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    /** Borra la sesion guardada y vuelve al login. */
    fun salir() {
        sesionVm.cerrarSesion {
            nav.navigate(Rutas.LOGIN) { popUpTo(nav.graph.id) { inclusive = true } }
        }
    }

    VetTheme(rol = rolTema, oscuro = oscuro) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                if (pestanas.size > 1) {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 6.dp) {
                        pestanas.forEach { pestana ->
                            NavigationBarItem(
                                selected = rutaActual == pestana.ruta,
                                onClick = {
                                    nav.navigate(pestana.ruta) {
                                        popUpTo(pestanas.first().ruta) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(pestana.icono, contentDescription = pestana.titulo) },
                                label = { Text(pestana.titulo) },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            NavHost(
                navController = nav,
                startDestination = Rutas.SPLASH,
                modifier = Modifier.padding(innerPadding)
            ) {
                // ---------- Comunes ----------
                composable(Rutas.SPLASH) {
                    SplashScreen(onComenzar = {
                        // Si quedo una sesion guardada se salta el login
                        val u = usuario
                        if (u != null) {
                            irAInicio(Rol.valueOf(u.rol))
                        } else {
                            nav.navigate(Rutas.LOGIN) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                        }
                    })
                }
                composable(Rutas.LOGIN) {
                    LoginScreen(
                        sesionVm,
                        onLogin = { rol -> irAInicio(rol) },
                        onRegistro = { nav.navigate(Rutas.REGISTRO) }
                    )
                }
                composable(Rutas.REGISTRO) {
                    RegistroScreen(
                        sesionVm,
                        onRegistrado = { rol -> irAInicio(rol) },
                        onVolver = { nav.popBackStack() }
                    )
                }

                // ---------- Flujo del dueño ----------
                composable(Rutas.INICIO) {
                    val u = usuario ?: return@composable
                    val vm: InicioViewModel = viewModel(
                        factory = VetViewModelFactory { InicioViewModel(repo, u.id) }
                    )
                    InicioScreen(
                        nombre = u.nombre,
                        vm = vm,
                        onVerMascota = { nav.navigate(Rutas.fichaMascota(it)) },
                        onAgregar = { nav.navigate(Rutas.formMascota()) },
                        onPedirCita = { nav.navigate(Rutas.pedirCita()) },
                        onVerTodas = { nav.navigate(Rutas.MASCOTAS) { launchSingleTop = true } }
                    )
                }
                composable(Rutas.MASCOTAS) {
                    val u = usuario ?: return@composable
                    val vm: InicioViewModel = viewModel(
                        factory = VetViewModelFactory { InicioViewModel(repo, u.id) }
                    )
                    MascotasScreen(
                        vm = vm,
                        onVerMascota = { nav.navigate(Rutas.fichaMascota(it)) },
                        onAgregar = { nav.navigate(Rutas.formMascota()) }
                    )
                }
                composable(
                    Rutas.FICHA_MASCOTA,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entry ->
                    val id = entry.arguments?.getInt("id") ?: return@composable
                    val vm: MascotaViewModel = viewModel(
                        factory = VetViewModelFactory { MascotaViewModel(repo) }
                    )
                    FichaMascotaScreen(
                        id = id,
                        vm = vm,
                        onBack = { nav.popBackStack() },
                        onPedirCita = { nav.navigate(Rutas.pedirCita(it)) },
                        onEditar = { nav.navigate(Rutas.formMascota(it)) },
                        soloLectura = usuario?.rol == Rol.ADMIN.name
                    )
                }
                composable(
                    Rutas.FORM_MASCOTA,
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
                ) { entry ->
                    val idArg = entry.arguments?.getInt("id") ?: -1
                    val u = usuario ?: return@composable
                    val vm: MascotaViewModel = viewModel(
                        factory = VetViewModelFactory { MascotaViewModel(repo) }
                    )
                    FormularioMascotaScreen(
                        id = idArg.takeIf { it > 0 },
                        duenoId = u.id,
                        vm = vm,
                        onBack = { nav.popBackStack() }
                    )
                }
                composable(
                    Rutas.PEDIR_CITA,
                    arguments = listOf(navArgument("mascotaId") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
                ) { entry ->
                    val mascotaId = entry.arguments?.getInt("mascotaId") ?: -1
                    val u = usuario ?: return@composable
                    val vm: PedirCitaViewModel = viewModel(
                        factory = VetViewModelFactory {
                            PedirCitaViewModel(repo, app.prefs, u.id, mascotaId.takeIf { it > 0 })
                        }
                    )
                    PedirCitaScreen(
                        vm = vm,
                        onBack = { nav.popBackStack() },
                        onCreada = { nav.navigate(Rutas.MIS_CITAS) { popUpTo(Rutas.INICIO) } }
                    )
                }
                composable(Rutas.MIS_CITAS) {
                    val u = usuario ?: return@composable
                    val vm: MisCitasViewModel = viewModel(
                        factory = VetViewModelFactory { MisCitasViewModel(repo, u.id) }
                    )
                    MisCitasScreen(
                        vm,
                        onPedirCita = { nav.navigate(Rutas.pedirCita()) },
                    )
                }
                composable(Rutas.PERFIL) {
                    val u = usuario ?: return@composable
                    val inicioVm: InicioViewModel = viewModel(
                        factory = VetViewModelFactory { InicioViewModel(repo, u.id) }
                    )
                    val citasVm: MisCitasViewModel = viewModel(
                        factory = VetViewModelFactory { MisCitasViewModel(repo, u.id) }
                    )
                    val mascotas by inicioVm.mascotas.collectAsState()
                    val proximas by citasVm.proximas.collectAsState()
                    val historial by citasVm.historial.collectAsState()
                    val notificaciones by sesionVm.notificaciones.collectAsState()
                    PerfilScreen(
                        usuario = u,
                        mascotas = mascotas.size,
                        proximas = proximas.size,
                        atenciones = historial.count { it.cita.estado == EstadoCita.ATENDIDA.name },
                        oscuro = oscuro,
                        notificaciones = notificaciones,
                        onOscuro = sesionVm::cambiarModoOscuro,
                        onNotificaciones = sesionVm::cambiarNotificaciones,
                        onCambiarPerfil = { sesionVm.cambiarPerfil { rol -> irAInicio(rol) } },
                        onCerrarSesion = { salir() }
                    )
                }

                // ---------- Flujo del veterinario ----------
                composable(Rutas.SERVICIOS) {
                    val vm: ServiciosViewModel = viewModel(
                        factory = VetViewModelFactory { ServiciosViewModel(repo) }
                    )
                    ServiciosScreen(
                        vm = vm,
                        onEditar = { nav.navigate(Rutas.formServicio(it)) },
                        onNuevo = { nav.navigate(Rutas.formServicio()) }
                    )
                }
                composable(Rutas.PANEL) {
                    val vm: PanelViewModel = viewModel(
                        factory = VetViewModelFactory { PanelViewModel(repo) }
                    )
                    PanelScreen(
                        vm = vm,
                        onVerPendientes = { nav.navigate(Rutas.AGENDA) { launchSingleTop = true } }
                    )
                }
                composable(Rutas.AGENDA) {
                    val vm: AgendaViewModel = viewModel(
                        factory = VetViewModelFactory { AgendaViewModel(repo) }
                    )
                    AgendaScreen(
                        vm = vm,
                        onAtender = { nav.navigate(Rutas.atenderCita(it)) }
                    )
                }
                composable(
                    Rutas.ATENDER_CITA,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entry ->
                    val citaId = entry.arguments?.getInt("id") ?: return@composable
                    val vm: AtenderCitaViewModel = viewModel(
                        factory = VetViewModelFactory { AtenderCitaViewModel(repo, citaId) }
                    )
                    AtenderCitaScreen(vm, onBack = { nav.popBackStack() })
                }
                composable(Rutas.PACIENTES) {
                    val vm: PacientesViewModel = viewModel(
                        factory = VetViewModelFactory { PacientesViewModel(repo) }
                    )
                    PacientesScreen(vm, onVerMascota = { nav.navigate(Rutas.fichaMascota(it)) })
                }
                composable(
                    Rutas.FORM_SERVICIO,
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
                ) { entry ->
                    val idArg = entry.arguments?.getInt("id") ?: -1
                    val vm: ServiciosViewModel = viewModel(
                        factory = VetViewModelFactory { ServiciosViewModel(repo) }
                    )
                    FormularioServicioScreen(
                        id = idArg.takeIf { it > 0 },
                        vm = vm,
                        onBack = { nav.popBackStack() }
                    )
                }
                composable(Rutas.AJUSTES) {
                    val u = usuario ?: return@composable
                    val notificaciones by sesionVm.notificaciones.collectAsState()
                    val aceptarCitas by sesionVm.aceptarCitas.collectAsState()
                    AjustesScreen(
                        usuario = u,
                        oscuro = oscuro,
                        notificaciones = notificaciones,
                        aceptarCitas = aceptarCitas,
                        onOscuro = sesionVm::cambiarModoOscuro,
                        onNotificaciones = sesionVm::cambiarNotificaciones,
                        onAceptarCitas = sesionVm::cambiarAceptarCitas,
                        onCambiarPerfil = { sesionVm.cambiarPerfil { rol -> irAInicio(rol) } },
                        onCerrarSesion = { salir() }
                    )
                }
            }
        }
    }
}
