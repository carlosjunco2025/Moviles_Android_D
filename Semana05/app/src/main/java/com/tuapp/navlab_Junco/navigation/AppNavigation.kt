package com.tuapp.navlab_Junco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.navlab_Junco.screens.DetailScreen
import com.tuapp.navlab_Junco.screens.HomeScreen
import com.tuapp.navlab_Junco.screens.ListScreen
import com.tuapp.navlab_Junco.screens.LoginScreen
import com.tuapp.navlab_Junco.screens.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.List.route) {
            ListScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(
            route = "detail/{itemId}",
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ) { backStackEntry ->
            val itemIdString = backStackEntry.arguments?.getString("itemId")
            val itemId = backStackEntry.arguments?.getInt("itemId")
                ?: itemIdString?.toIntOrNull()
                ?: 1
            DetailScreen(navController, itemId = itemId)
        }
    }
}