package com.tuapp.clinicasalud.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.clinicasalud.ui.*
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route -> navController.navigate(route) },
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onDoctorClick = { doctorId -> navController.navigate("doctor_profile/$doctorId") },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }

            composable(
                route = "doctor_profile/{doctorId}",
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
                DoctorProfileScreen(
                    doctorId = doctorId,
                    onBackClick = { navController.popBackStack() },
                    onScheduleClick = { id -> navController.navigate("book_appointment/$id") }
                )
            }

            composable(
                route = "book_appointment/{doctorId}",
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
                BookAppointmentScreen(
                    doctorId = doctorId,
                    onBackClick = { navController.popBackStack() },
                    onConfirmClick = { docName, date, time ->
                        navController.navigate("confirm/$docName/$date/$time")
                    }
                )
            }

            composable(
                route = "confirm/{doctorName}/{date}/{time}",
                arguments = listOf(
                    navArgument("doctorName") { type = NavType.StringType },
                    navArgument("date") { type = NavType.StringType },
                    navArgument("time") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("doctorName") ?: ""
                val date = backStackEntry.arguments?.getString("date") ?: ""
                val time = backStackEntry.arguments?.getString("time") ?: ""

                ConfirmationScreen(
                    doctorName = name,
                    date = date,
                    time = time,
                    onSeeAppointments = {
                        navController.navigate("appointments") {
                            popUpTo("home")
                        }
                    }
                )
            }

            composable("appointments") {
                AppointmentsScreen(onOpenDrawer = { scope.launch { drawerState.open() } })
            }

            composable("history") {
                AppointmentsScreen(onOpenDrawer = { scope.launch { drawerState.open() } })
            }

            composable("profile") {
                AppointmentsScreen(onOpenDrawer = { scope.launch { drawerState.open() } })
            }
        }
    }
}