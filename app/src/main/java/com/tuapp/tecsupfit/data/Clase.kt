package com.tuapp.tecsupfit.data

data class Clase(
    val id: Int,
    val nombre: String,
    val dia: String,
    val hora: String,
    val sala: String,
    val duracion: Int,
    val descripcion: String,
    val cuposDisponibles: Int,
    val cuposTotales: Int
)

object DatosClases {
    val listaClases = listOf(
        Clase(1, "Yoga funcional", "Hoy", "7:00 am", "Sala 2", 60, "Movilidad y fuerza con peso corporal. Ideal para empezar el día.", 5, 15),
        Clase(2, "Cross Training", "Hoy", "6:00 pm", "Sala 1", 45, "Entrenamiento funcional de alta intensidad. Cupos limitados.", 8, 12),
        Clase(3, "Spinning", "Hoy", "7:30 pm", "Sala 3", 50, "Ciclismo indoor con música y cambios de ritmo.", 10, 20),
        Clase(4, "Pilates", "Mié", "8:00 am", "Sala 2", 50, "Control postural, respiración y fortalecimiento del core.", 6, 12),
        Clase(5, "Box funcional", "Jue", "7:00 pm", "Sala 1", 60, "Técnica de golpes y circuitos de resistencia.", 9, 14)
    )

    val filtros = listOf("Hoy", "Esta semana")

    fun buscar(id: Int): Clase {
        return listaClases.first { it.id == id }
    }
}
