package com.tuapp.navlab_Junco.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.navlab_Junco.components.DrawerContent
import com.tuapp.navlab_Junco.data.Cita
import com.tuapp.navlab_Junco.screens.*
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val citas = remember {
        mutableStateListOf(
            Cita("Dra. Ana Torres", "Viernes 27", "10:30 am", "Confirmada"),
            Cita("Dr. Luis Vega", "Miércoles 15", "3:00 pm", "Completada")
        )
    }

    val gesturesEnabled = rutaActual in listOf("inicio", "mis_citas", "historial", "perfil") || drawerState.isOpen

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            DrawerContent(
                rutaActual = rutaActual,
                onOpcionClick = { ruta ->
                    scope.launch {
                        drawerState.close()
                    }
                    if (ruta == "inicio") {
                        navController.popBackStack("inicio", false)
                    } else {
                        navController.navigate(ruta) {
                            popUpTo("inicio") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Inicio.route
        ) {
            composable(Screen.Inicio.route) {
                InicioScreen(
                    onMedicoClick = { id ->
                        navController.navigate(Screen.PerfilMedico.createRoute(id))
                    },
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

            composable(
                route = Screen.PerfilMedico.route,
                arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 1
                PerfilMedicoScreen(
                    medicoId = medicoId,
                    onBack = { navController.popBackStack() },
                    onAgendarClick = {
                        navController.navigate(Screen.Agendar.createRoute(medicoId))
                    }
                )
            }

            composable(
                route = Screen.Agendar.route,
                arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 1
                AgendarCitaScreen(
                    medicoId = medicoId,
                    onBack = { navController.popBackStack() },
                    onConfirmar = { fecha, hora, nombreMedico ->
                        citas.add(0, Cita(nombreMedico, fecha, hora, "Confirmada"))
                        navController.navigate(Screen.Confirmacion.createRoute(medicoId, fecha, hora))
                    }
                )
            }

            composable(
                route = Screen.Confirmacion.route,
                arguments = listOf(
                    navArgument("medicoId") { type = NavType.IntType },
                    navArgument("fecha") { type = NavType.StringType },
                    navArgument("hora") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 1
                val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
                val hora = backStackEntry.arguments?.getString("hora") ?: ""

                BackHandler {
                    navController.popBackStack("inicio", false)
                }

                ConfirmacionScreen(
                    medicoId = medicoId,
                    fecha = fecha,
                    hora = hora,
                    onVerMisCitas = {
                        navController.navigate(Screen.MisCitas.route) {
                            popUpTo("inicio")
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.MisCitas.route) {
                MisCitasScreen(
                    citas = citas,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

            composable(Screen.Historial.route) {
                HistorialScreen(
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

            composable(Screen.Perfil.route) {
                PerfilScreen(
                    citas = citas,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        }
    }
}
