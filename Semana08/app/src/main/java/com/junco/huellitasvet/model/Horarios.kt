package com.junco.huellitasvet.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.util.Locale

/** Los 18 horarios de atencion: de 09:00 a 17:30, cada 30 minutos. */
object Horarios {
    private val apertura: LocalTime = LocalTime.of(9, 0)

    val todos: List<String> = List(18) { indice ->
        apertura.plusMinutes(30L * indice).toString() // LocalTime.toString() ya da "09:00"
    }
}

/** Funciones de formato compartidas por las pantallas. */
object Formato {
    private val diasCortos = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
    private val mesesCortos = listOf(
        "ene", "feb", "mar", "abr", "may", "jun",
        "jul", "ago", "sep", "oct", "nov", "dic"
    )
    private val mesesLargos = listOf(
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    )

    fun diaSemana(fecha: LocalDate): String = diasCortos[fecha.dayOfWeek.value - 1]

    /** "2026-10-21" -> "Mié 21 oct" */
    fun fechaCorta(fecha: String): String {
        val d = LocalDate.parse(fecha)
        return "${diaSemana(d)} ${d.dayOfMonth} ${mesesCortos[d.monthValue - 1]}"
    }

    /** "2026-10-21" -> "21 oct" */
    fun fechaDiaMes(fecha: String): String {
        val d = LocalDate.parse(fecha)
        return "${d.dayOfMonth} ${mesesCortos[d.monthValue - 1]}"
    }

    fun fechaHora(fecha: String, hora: String): String = "${fechaCorta(fecha)} · $hora"

    /** YearMonth.of(2026, 10) -> "octubre 2026" */
    fun mesAnio(mes: YearMonth): String = "${mesesLargos[mes.monthValue - 1]} ${mes.year}"

    fun soles(monto: Double): String = "S/ " + String.format(Locale.US, "%.2f", monto)

    fun peso(kg: Double): String = String.format(Locale.US, "%.1f kg", kg)

    fun edad(anioNacimiento: Int): Int = LocalDate.now().year - anioNacimiento

    fun fechaHoraDe(fecha: String, hora: String): LocalDateTime =
        LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(hora))
}
