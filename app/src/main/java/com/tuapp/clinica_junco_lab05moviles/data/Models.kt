package com.tuapp.clinicasalud.data

data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double,
    val reviewCount: Int,
    val experienceYears: Int,
    val biography: String,
    val availableDates: List<String>,
    val availableTimes: List<String>
)

data class Appointment(
    val id: Int,
    val doctorName: String,
    val specialty: String,
    val dateTime: String,
    val status: String
)

object MockData {
    val sampleDoctors = listOf(
        Doctor(
            id = 1,
            name = "Dra. Ana Torres",
            specialty = "Cardiología",
            rating = 4.9,
            reviewCount = 128,
            experienceYears = 12,
            biography = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo.",
            availableDates = listOf("Jue 26", "Vie 27", "Sáb 28"),
            availableTimes = listOf("9:00", "10:30", "3:00")
        ),
        Doctor(
            id = 2,
            name = "Dr. Luis Vega",
            specialty = "Pediatría",
            rating = 4.7,
            reviewCount = 95,
            experienceYears = 8,
            biography = "Atención integral infantil y desarrollo pediátrico continuo.",
            availableDates = listOf("Jue 26", "Vie 27", "Dom 29"),
            availableTimes = listOf("8:00", "11:00", "4:30")
        ),
        Doctor(
            id = 3,
            name = "Dra. Rosa Díaz",
            specialty = "Dermatología",
            rating = 4.8,
            reviewCount = 110,
            experienceYears = 10,
            biography = "Especialista en dermatología clínica, estética y tratamientos láser.",
            availableDates = listOf("Vie 27", "Sáb 28", "Lun 30"),
            availableTimes = listOf("10:00", "2:00", "5:00")
        )
    )

    val sampleAppointments = listOf(
        Appointment(
            id = 1,
            doctorName = "Dra. Ana Torres",
            specialty = "Cardiología",
            dateTime = "Viernes 27, 10:30 am",
            status = "Confirmada"
        ),
        Appointment(
            id = 2,
            doctorName = "Dr. Luis Vega",
            specialty = "Pediatría",
            dateTime = "Miércoles 15, 3:00 pm",
            status = "Completada"
        )
    )
}