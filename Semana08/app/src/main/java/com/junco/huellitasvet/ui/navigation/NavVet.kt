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

/** Una pestaña de la barra inferior. */
private data class Pestana(val ruta: String, val titulo: String, val icono: ImageVector)

@Composable
fun NavVet() {
    val app = LocalContext.current.applicationContext as VetApp
    val repo = app.repository
    val nav = rememberNavController()
    val oscuro = false
    val rolTema = Rol.ADMIN
    val rutaActual = nav.currentBackStackEntryAsState().value?.destination?.route

    val pestanasDueno = listOf<Pestana>(
    )
    val pestanasVet = listOf<Pestana>(
        Pestana(Rutas.SERVICIOS, "Servicios", Icons.Filled.LocalOffer),
    )
    val rutasDueno = pestanasDueno.map { it.ruta }
    val rutasVet = pestanasVet.map { it.ruta }
    val pestanas: List<Pestana> = when (rutaActual) {
        in rutasDueno -> pestanasDueno
        in rutasVet -> pestanasVet
        else -> emptyList()
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
                        nav.navigate(Rutas.SERVICIOS) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                    })
                }

                // ---------- Flujo del veterinario ----------
                composable(Rutas.SERVICIOS) {
                    val vm: ServiciosViewModel = viewModel(
                        factory = VetViewModelFactory { ServiciosViewModel(repo) }
                    )
                    ServiciosScreen(
                        vm = vm,
                    )
                }
            }
        }
    }
}
