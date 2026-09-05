package com.jucno03

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaRegistro() {
    // Estados para los campos de texto
    var nombre by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var cantidadText by remember { mutableStateOf("") }

    // Estados para controlar la vista del resumen y el calculo
    var mostrarResumen by remember { mutableStateOf(false) }
    var importeCalculado by remember { mutableStateOf(0.0) }
    var nombreGuardado by remember { mutableStateOf("") }
    var precioGuardado by remember { mutableStateOf(0.0) }
    var cantidadGuardada by remember { mutableStateOf(0) }

    // Estado para controlar el mensaje de error de validación
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Registro de Producto",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Precio
        OutlinedTextField(
            value = precioText,
            onValueChange = { precioText = it },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Cantidad
        OutlinedTextField(
            value = cantidadText,
            onValueChange = { cantidadText = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de Acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // Validación de campos vacíos
                    if (nombre.isBlank() || precioText.isBlank() || cantidadText.isBlank()) {
                        mensajeError = "Por favor, llene todos los campos."
                        mostrarResumen = false
                    } else {
                        val precio = precioText.toDoubleOrNull() ?: 0.0
                        val cantidad = cantidadText.toIntOrNull() ?: 0

                        // Cálculo del importe (precio * cantidad)
                        importeCalculado = precio * cantidad

                        // Guardar valores para mostrar en la Card
                        nombreGuardado = nombre
                        precioGuardado = precio
                        cantidadGuardada = cantidad

                        // Estado de éxito
                        mensajeError = ""
                        mostrarResumen = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Agregar")
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = {
                    // Limpiar todas las variables de estado
                    nombre = ""
                    precioText = ""
                    cantidadText = ""
                    mostrarResumen = false
                    mensajeError = ""
                    importeCalculado = 0.0
                    nombreGuardado = ""
                    precioGuardado = 0.0
                    cantidadGuardada = 0
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Limpiar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de Error
        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = Color.Red,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Resumen y Mensaje de Éxito
        if (mostrarResumen) {
            Text(
                text = "¡Producto registrado con éxito!",
                color = Color(0xFF2E7D32), // Tono verde Material Design
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Resume del Producto",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Producto: $nombreGuardado")
                    Text(text = "Precio unitario: S/ $precioGuardado")
                    Text(text = "Cantidad: $cantidadGuardada")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Importe total: S/ $importeCalculado",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}