package com.junco.huellitasvet.ui.components.graficos

import androidx.compose.ui.graphics.Color

/** Dato generico para los graficos hechos con Canvas. Sin color, el grafico elige uno. */
data class DatoGrafico(
    val etiqueta: String,
    val valor: Float,
    val color: Color = Color.Unspecified
)
