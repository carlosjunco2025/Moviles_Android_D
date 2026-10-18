package com.junco.huellitasvet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.ui.graphics.vector.ImageVector

/** Servicio.icono guarda un texto (heart, syringe, scissors); aqui se convierte en icono. */
fun iconoServicio(clave: String): ImageVector = when (clave) {
    "syringe" -> Icons.Filled.Vaccines
    "scissors" -> Icons.Filled.ContentCut
    else -> Icons.Filled.Favorite
}
