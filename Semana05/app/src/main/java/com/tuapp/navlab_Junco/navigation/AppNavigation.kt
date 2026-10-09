package com.tuapp.navlab_Junco.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.tuapp.navlab_Junco.data.Reserva
import com.tuapp.navlab_Junco.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val reservas = remember {
        mutableStateListOf(
            Reserva("Cross Training", "Hoy, 6:00 pm", "Confirmada"),
            Reserva("Yoga funcional", "Ayer, 7:00 am", "Completada")
        )
    }

    val onTabClick: (String) -> Unit = { ruta ->
        if (ruta == "inicio") {
            navController.popBackStack("inicio", false)
        } else if (rutaActual != ruta) {
            navController.navigate(ruta) {
                popUpTo("inicio") { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.Inicio.route) {
        composable(Screen.Inicio.route) {
            InicioScreen(
                onClaseClick = { claseId ->
                    navController.navigate(Screen.Detalle.createRoute(claseId))
                },
                rutaActual = rutaActual,
                onTabClick = onTabClick
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
                onReservarClick = { clase, dia, hora ->
                    val yaExiste = reservas.any { it.clase == clase.nombre && it.estado == "Confirmada" }
                    if (!yaExiste) {
                        reservas.add(0, Reserva(clase.nombre, "$dia, $hora", "Confirmada"))
                    }
                    navController.navigate(Screen.Confirmacion.createRoute(claseId)) {
                        popUpTo(Screen.Inicio.route)
                    }
                }
            )
        }
        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
            BackHandler {
                navController.popBackStack("inicio", false)
            }
            ConfirmacionScreen(
                claseId = claseId,
                onVerReservasClick = {
                    navController.navigate(Screen.Reservas.route) {
                        popUpTo("inicio")
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
