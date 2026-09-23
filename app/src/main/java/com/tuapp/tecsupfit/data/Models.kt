package com.example.tecsupfit.data

data class GymClass(
    val id: String,
    val title: String,
    val category: String, // Cardio, Fuerza, Flexibilidad
    val instructor: String,
    val durationMinutes: Int,
    val description: String,
    val availableSchedules: List<String>
)

data class Reservation(
    val classId: String,
    val classTitle: String,
    val schedule: String,
    val userName: String = "Estudiante TECSUP"
)

object MockData {
    val sampleClasses = listOf(
        GymClass(
            id = "1",
            title = "Spinning Intensivo",
            category = "Cardio",
            instructor = "Carlos Mendoza",
            durationMinutes = 45,
            description = "Entrenamiento de alta intensidad sobre bicicleta estática para mejorar la resistencia cardiovascular y quemar calorías.",
            availableSchedules = listOf("07:00 AM", "05:00 PM", "07:00 PM")
        ),
        GymClass(
            id = "2",
            title = "Power Lifting & Pesas",
            category = "Fuerza",
            instructor = "Ana Paredes",
            durationMinutes = 60,
            description = "Sesión guiada centrada en técnica de levantamiento, hipertrofia y desarrollo de fuerza muscular.",
            availableSchedules = listOf("08:00 AM", "04:00 PM", "06:00 PM")
        ),
        GymClass(
            id = "3",
            title = "Yoga & Stretches",
            category = "Flexibilidad",
            instructor = "Lucía Gómez",
            durationMinutes = 50,
            description = "Clase de estiramientos profundos, respiración consciente y postura para mejorar la flexibilidad y reducir estrés.",
            availableSchedules = listOf("06:00 AM", "09:00 AM", "08:00 PM")
        ),
        GymClass(
            id = "4",
            title = "CrossFit Functional",
            category = "Fuerza",
            instructor = "Roberto Silva",
            durationMinutes = 60,
            description = "Circuito funcional de alta demanda física utilizando kettlebells, cuerdas y peso corporal.",
            availableSchedules = listOf("07:00 AM", "06:00 PM")
        )
    )
}