package com.jucno03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaRegistroNotas(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaRegistroNotas(modifier: Modifier = Modifier) {
    // ESTADOS PARA LAS NOTAS (0 a 20)
    var notaFundamentos by remember { mutableFloatStateOf(0f) }
    var notaPOO by remember { mutableFloatStateOf(0f) }
    var notaMoviles by remember { mutableFloatStateOf(0f) }
    var notaBD by remember { mutableFloatStateOf(0f) }

    // ESTADOS DE OPCIONES
    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }

    // ESTADOS DE RESULTADOS
    var promPonderado by remember { mutableDoubleStateOf(0.0) }
    var promFinalStr by remember { mutableStateOf("") }
    var observacion by remember { mutableStateOf("") }
    var colorChip by remember { mutableStateOf(Color.Gray) }

    val gradientBg = Brush.verticalGradient(
        colors = listOf(Color(0xFFEADBFF), Color(0xFFF6F2FF))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBg)
            .verticalScroll(rememberScrollState())
    ) {
        // BARRA SUPERIOR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF65558F)
        ) {
            Text(
                text = "Registro de Notas",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ENCABEZADO DE SECCIÓN
            Text(
                text = "Notas del ciclo",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Desliza para asignar cada nota (0 a 20)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            // SLIDERS POR CURSO (RETO: Semáforo aplicado en los badges)
            ItemCursoSlider("Fundamentos de Programación", 20, notaFundamentos) { notaFundamentos = it }
            ItemCursoSlider("Programación Orientada a Objetos", 25, notaPOO) { notaPOO = it }
            ItemCursoSlider("Programación en Móviles", 30, notaMoviles) { notaMoviles = it }
            ItemCursoSlider("Base de Datos", 25, notaBD) { notaBD = it }

            Spacer(modifier = Modifier.height(8.dp))

            // SWITCH REDONDEAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Redondear promedio final", style = MaterialTheme.typography.bodyMedium)
                Switch(
                    checked = redondear,
                    onCheckedChange = { redondear = it }
                )
            }

            // CHECKBOX CONFIRMACIÓN
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = confirmado,
                    onCheckedChange = { confirmado = it }
                )
                Text(text = "Confirmo que las notas son correctas", style = MaterialTheme.typography.bodyMedium)
            }

            // BOTÓN CALCULAR PROMEDIO
            Button(
                onClick = {
                    val pPonderado = (notaFundamentos * 0.20) + (notaPOO * 0.25) + (notaMoviles * 0.30) + (notaBD * 0.25)
                    promPonderado = pPonderado

                    val pFinal = if (redondear) pPonderado.roundToInt().toDouble() else pPonderado
                    promFinalStr = if (redondear) "${pPonderado.roundToInt()}" else String.format("%.2f", pPonderado)

                    when {
                        pFinal >= 17.0 -> {
                            observacion = "EXCELENTE"
                            colorChip = Color(0xFF1B5E20)
                        }
                        pFinal >= 13.0 -> {
                            observacion = "APROBADO"
                            colorChip = Color(0xFF2E7D32)
                        }
                        pFinal >= 10.0 -> {
                            observacion = "EN RECUPERACIÓN"
                            colorChip = Color(0xFFF57F17)
                        }
                        else -> {
                            observacion = "DESAPROBADO"
                            colorChip = Color(0xFFC62828)
                        }
                    }
                    calculado = true
                },
                enabled = confirmado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text("CALCULAR PROMEDIO", fontWeight = FontWeight.Bold)
            }

            // RETO OPCIONAL 3: BOTÓN LIMPIAR
            OutlinedButton(
                onClick = {
                    notaFundamentos = 0f
                    notaPOO = 0f
                    notaMoviles = 0f
                    notaBD = 0f
                    redondear = false
                    confirmado = false
                    calculado = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LIMPIAR", color = Color(0xFF65558F), fontWeight = FontWeight.Bold)
            }

            // MENSAJE O TARJETA DE RESULTADOS
            if (!calculado) {
                Text(
                    text = "Asigna las notas y confirma para calcular",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Promedio ponderado:  ${String.format("%.2f", promPonderado)}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Promedio final:  $promFinalStr",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF65558F)
                                )
                            )
                            if (redondear) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "(redondeado)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }

                        Surface(
                            color = colorChip.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = observacion,
                                color = colorChip,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                        // RETO OPCIONAL 1: APORTE POR CURSO
                        Text("Aporte por curso:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        Text("Fundamentos: ${notaFundamentos.toInt()} × 20% = ${String.format("%.2f", notaFundamentos * 0.20)}", style = MaterialTheme.typography.bodySmall)
                        Text("POO: ${notaPOO.toInt()} × 25% = ${String.format("%.2f", notaPOO * 0.25)}", style = MaterialTheme.typography.bodySmall)
                        Text("Móviles: ${notaMoviles.toInt()} × 30% = ${String.format("%.2f", notaMoviles * 0.30)}", style = MaterialTheme.typography.bodySmall)
                        Text("Base de Datos: ${notaBD.toInt()} × 25% = ${String.format("%.2f", notaBD * 0.25)}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Text(
                    text = "✓ Promedio calculado correctamente",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // PIE DE PÁGINA
            Text(
                text = "Desarrollado por: Carlos Junco",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ItemCursoSlider(
    nombre: String,
    peso: Int,
    valor: Float,
    onValueChange: (Float) -> Unit
) {
    // RETO OPCIONAL 2: SEMÁFORO (rojo si nota < 13, verde si >= 13)
    val colorBadge = if (valor < 13f) Color(0xFFC62828) else Color(0xFF2E7D32)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "($peso%)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF65558F)
                )
            }
            Surface(
                color = colorBadge.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${valor.toInt()}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontWeight = FontWeight.Bold,
                    color = colorBadge
                )
            }
        }
        Slider(
            value = valor,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            steps = 19,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF65558F),
                activeTrackColor = Color(0xFF65558F)
            )
        )
    }
}