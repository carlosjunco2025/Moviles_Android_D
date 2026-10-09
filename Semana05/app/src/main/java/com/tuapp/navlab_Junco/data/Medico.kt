package com.tuapp.navlab_Junco.data

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val titulo: String,
    val experiencia: Int,
    val rating: Double,
    val resenas: Int,
    val descripcion: String
)

object DatosMedicos {
    val listaMedicos = listOf(
        Medico(1, "Dra. Ana Torres", "Cardiología", "Cardióloga", 12, 4.9, 128, "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."),
        Medico(2, "Dr. Luis Vega", "Pediatría", "Pediatra", 9, 4.7, 96, "Especialista en control del niño sano y vacunación infantil."),
        Medico(3, "Dr. Rosa Díaz", "Dermatología", "Dermatóloga", 10, 4.8, 110, "Especialista en dermatología clínica y estética, formación en el Hospital Clínic.")
    )

    val especialidades = listOf("Cardiología", "Pediatría")

    fun buscar(id: Int): Medico {
        return listaMedicos.find { it.id == id } ?: listaMedicos.first()
    }
}
