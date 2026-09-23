package com.tuapp.tecsupfit.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.tecsupfit.data.DatosClases
import com.tuapp.tecsupfit.data.Reserva
import com.tuapp.tecsupfit.screens.ConfirmacionScreen
import com.tuapp.tecsupfit.screens.DetalleClaseScreen
import com.tuapp.tecsupfit.screens.InicioScreen
import com.tuapp.tecsupfit.screens.PerfilScreen
import com.tuapp.tecsupfit.screens.ReservasScreen
import com.tuapp.tecsupfit.screens.RutinasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry.value?.destination?.route

    val reservas = remember {
        mutableStateListOf(
            Reserva("Cross Training", "Hoy, 6:00 pm", "Confirmada"),
            Reserva("Yoga funcional", "Ayer, 7:00 am", "Completada")
        )
    }

    val onTabClick: (String) -> Unit = { ruta ->
        if (ruta == Screen.Inicio.route) {
            navController.popBackStack(Screen.Inicio.route, inclusive = false)
        } else if (ruta != rutaActual) {
            navController.navigate(ruta) {
                popUpTo(Screen.Inicio.route) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route
    ) {
        composable(Screen.Inicio.route) {
            InicioScreen(
                rutaActual = rutaActual,
                onTabClick = onTabClick,
                onClaseClick = { claseId ->
                    navController.navigate(Screen.Detalle.createRoute(claseId))
                }
            )
        }

        composable(
            route = Screen.Detalle.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
            DetalleClaseScreen(
                claseId = claseId,
                onBack = { navController.popBackStack() },
                onReservar = {
                    val clase = DatosClases.buscar(claseId)
                    val yaExiste = reservas.any { it.clase == clase.nombre && it.estado == "Confirmada" }
                    if (!yaExiste) {
                        reservas.add(
                            0,
                            Reserva(
                                clase = clase.nombre,
                                fecha = "${clase.dia}, ${clase.hora}",
                                estado = "Confirmada"
                            )
                        )
                    }
                    navController.navigate(Screen.Confirmacion.createRoute(claseId))
                }
            )
        }

        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1

            BackHandler {
                navController.popBackStack(Screen.Inicio.route, inclusive = false)
            }

            ConfirmacionScreen(
                claseId = claseId,
                onVerReservasClick = {
                    navController.navigate(Screen.Reservas.route) {
                        popUpTo(Screen.Inicio.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Reservas.route) {
            ReservasScreen(
                reservas = reservas,
                rutaActual = rutaActual,
                onTabClick = onTabClick
            )
        }

        composable(Screen.Rutinas.route) {
            RutinasScreen(
                rutaActual = rutaActual,
                onTabClick = onTabClick
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                rutaActual = rutaActual,
                onTabClick = onTabClick
            )
        }
    }
}
