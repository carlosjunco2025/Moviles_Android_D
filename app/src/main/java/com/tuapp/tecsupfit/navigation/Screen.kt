package com.tuapp.tecsupfit.navigation

sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")
    object Detalle : Screen("detalle/{claseId}") {
        fun createRoute(id: Int) = "detalle/$id"
    }
    object Confirmacion : Screen("confirmacion/{claseId}") {
        fun createRoute(id: Int) = "confirmacion/$id"
    }
    object Reservas : Screen("reservas")
    object Rutinas : Screen("rutinas")
    object Perfil : Screen("perfil")
}
