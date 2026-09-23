package com.example.tecsupfit.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tecsupfit.ui.BottomNavigationBar
import com.example.tecsupfit.ui.ConfirmationScreen
import com.example.tecsupfit.ui.DetailScreen
import com.example.tecsupfit.ui.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{classId}") {
        fun createRoute(classId: String) = "detail/$classId"
    }
    object Confirmation : Screen("confirmation/{classTitle}/{schedule}") {
        fun createRoute(classTitle: String, schedule: String) = "confirmation/$classTitle/$schedule"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Home.route) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onClassClick = { classId ->
                        navController.navigate(Screen.Detail.createRoute(classId))
                    }
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("classId") { type = NavType.StringType })
            ) { backStackEntry ->
                val classId = backStackEntry.arguments?.getString("classId") ?: ""
                DetailScreen(
                    classId = classId,
                    onReserveClick = { title, schedule ->
                        navController.navigate(Screen.Confirmation.createRoute(title, schedule)) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Confirmation.route,
                arguments = listOf(
                    navArgument("classTitle") { type = NavType.StringType },
                    navArgument("schedule") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("classTitle") ?: ""
                val schedule = backStackEntry.arguments?.getString("schedule") ?: ""
                ConfirmationScreen(
                    classTitle = title,
                    schedule = schedule,
                    onReturnHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}