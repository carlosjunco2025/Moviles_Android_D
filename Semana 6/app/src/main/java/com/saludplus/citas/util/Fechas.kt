package com.saludplus.citas.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun esDiaHabil(fecha: LocalDate): Boolean {
    return fecha.dayOfWeek != DayOfWeek.SATURDAY && fecha.dayOfWeek != DayOfWeek.SUNDAY
}

fun primerDiaHabil(desde: LocalDate): LocalDate {
    var actual = desde
    while (!esDiaHabil(actual)) {
        actual = actual.plusDays(1)
    }
    return actual
}

fun diasHabiles(desde: LocalDate, cantidad: Int = 5): List<LocalDate> {
    val resultado = mutableListOf<LocalDate>()
    var actual = primerDiaHabil(desde)
    while (resultado.size < cantidad) {
        if (esDiaHabil(actual)) {
            resultado.add(actual)
        }
        actual = actual.plusDays(1)
    }
    return resultado
}

fun semanaDeCalendario(hoy: LocalDate, indiceSemana: Int): List<LocalDate> {
    val fechaBase = hoy.plusDays(indiceSemana * 7L)
    return diasHabiles(fechaBase, 5)
}

fun nombreDiaCorto(fecha: LocalDate): String {
    return when (fecha.dayOfWeek) {
        DayOfWeek.MONDAY -> "Lun"
        DayOfWeek.TUESDAY -> "Mar"
        DayOfWeek.WEDNESDAY -> "Mié"
        DayOfWeek.THURSDAY -> "Jue"
        DayOfWeek.FRIDAY -> "Vie"
        DayOfWeek.SATURDAY -> "Sáb"
        DayOfWeek.SUNDAY -> "Dom"
        else -> ""
    }
}

private fun obtenerNombreMes(mes: Int): String {
    return when (mes) {
        1 -> "enero"
        2 -> "febrero"
        3 -> "marzo"
        4 -> "abril"
        5 -> "mayo"
        6 -> "junio"
        7 -> "julio"
        8 -> "agosto"
        9 -> "setiembre"
        10 -> "octubre"
        11 -> "noviembre"
        12 -> "diciembre"
        else -> ""
    }
}

fun mesYAnio(fecha: LocalDate): String {
    val mesNombre = obtenerNombreMes(fecha.monthValue).replaceFirstChar { it.uppercase() }
    return "$mesNombre ${fecha.year}"
}

fun fechaEnTexto(fecha: LocalDate): String {
    val diaSemana = when (fecha.dayOfWeek) {
        DayOfWeek.MONDAY -> "Lunes"
        DayOfWeek.TUESDAY -> "Martes"
        DayOfWeek.WEDNESDAY -> "Miércoles"
        DayOfWeek.THURSDAY -> "Jueves"
        DayOfWeek.FRIDAY -> "Viernes"
        DayOfWeek.SATURDAY -> "Sábado"
        DayOfWeek.SUNDAY -> "Domingo"
        else -> ""
    }
    val mesNombre = obtenerNombreMes(fecha.monthValue)
    return "$diaSemana ${fecha.dayOfMonth} de $mesNombre ${fecha.year}"
}

fun fechaEnTexto(fechaIso: String): String {
    return try {
        val fecha = LocalDate.parse(fechaIso)
        fechaEnTexto(fecha)
    } catch (e: DateTimeParseException) {
        fechaIso
    }
}
