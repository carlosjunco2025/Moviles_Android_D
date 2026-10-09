package com.tuapp.navlab_Junco.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")

    object PerfilMedico : Screen("perfil_medico/{medicoId}") {
        fun createRoute(medicoId: Int): String = "perfil_medico/$medicoId"
    }

    object Agendar : Screen("agendar/{medicoId}") {
        fun createRoute(medicoId: Int): String = "agendar/$medicoId"
    }

    object Confirmacion : Screen("confirmacion/{medicoId}/{fecha}/{hora}") {
        fun createRoute(medicoId: Int, fecha: String, hora: String): String {
            val encodedFecha = Uri.encode(fecha)
            val encodedHora = Uri.encode(hora)
            return "confirmacion/$medicoId/$encodedFecha/$encodedHora"
        }
    }

    object MisCitas : Screen("mis_citas")
    object Historial : Screen("historial")
    object Perfil : Screen("perfil")
}
