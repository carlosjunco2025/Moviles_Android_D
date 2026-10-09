package com.saludplus.citas.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.BotonAzul
import com.saludplus.citas.ui.theme.AzulOscuro
import com.saludplus.citas.ui.theme.GrisTexto

private data class Seccion(val titulo: String, val cuerpo: String)

private val secciones = listOf(
    Seccion("1. Aceptación de los términos",
        "Al registrarte en SaludPlus, aceptas estos términos y nuestra Política de Privacidad."),

    Seccion("2. Marco legal",
        "Se aplican las leyes peruanas de protección de datos personales y derechos de los pacientes."),

    Seccion("3. Responsable del tratamiento",
        "La responsable es la Clínica SaludPlus. Contacto: privacidad@saludplus.example."),

    Seccion("4. Datos que recopilamos",
        "Recopilamos tu nombre, teléfono, correo, contraseña y datos de tus citas médicas."),

    Seccion("5. Para qué usamos tus datos",
        "Usamos tus datos para administrar tu cuenta, gestionar citas y enviarte notificaciones."),

    Seccion("6. Consentimiento",
        "Al aceptar estos términos, autorizas el uso de tus datos para las finalidades indicadas. Puedes retirar tu consentimiento."),

    Seccion("7. Confidencialidad de la historia clínica",
        "Tu información médica es confidencial y solo podrá consultarse por personas autorizadas."),

    Seccion("8. Con quién compartimos tus datos",
        "No vendemos tus datos. Solo se comparten cuando sea necesario para la atención médica o lo exija la ley."),

    Seccion("9. Conservación",
        "Guardamos tus datos durante el tiempo necesario y según los plazos legales aplicables."),

    Seccion("10. Seguridad",
        "Aplicamos medidas de seguridad para proteger tu información. Mantén tu contraseña en privado."),

    Seccion("11. Tus derechos",
        "Puedes solicitar acceso, corrección, eliminación u oponerte al uso de tus datos mediante nuestro correo de contacto."),

    Seccion("12. Uso de la aplicación",
        "La app permite gestionar citas, pero no reemplaza la atención médica ni los servicios de emergencia."),

    Seccion("13. Cambios en estos términos",
        "Podemos actualizar estos términos y te avisaremos si los cambios son importantes."),

    Seccion("14. Ley aplicable",
        "Estos términos se rigen por las leyes del Perú y respetan los derechos de los usuarios.")
)

@Composable
fun TerminosScreen(onAtras: () -> Unit) {
    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior(titulo = "Términos y Condiciones", onAtras = onAtras) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            // Zona con scroll para leer todo el texto
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Términos y Condiciones de Uso y Política de Privacidad",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AzulOscuro
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Clínica SaludPlus · App Paciente",
                    fontSize = 14.sp,
                    color = GrisTexto
                )
                Text(
                    text = "Última actualización: 4 de octubre de 2026",
                    fontSize = 13.sp,
                    color = GrisTexto
                )

                Spacer(Modifier.height(16.dp))

                // Aviso de versión de demostración
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFFFF1D6))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "Versión de demostración: esta app es un proyecto académico. Los datos " +
                                "se guardan solo en la memoria del teléfono y se pierden al cerrar la app.",
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = Color(0xFF8A5A00)
                    )
                }

                secciones.forEach { seccion ->
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = seccion.titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = seccion.cuerpo,
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = GrisTexto
                    )
                }

                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Texto de ejemplo con fines educativos; no constituye asesoría legal.",
                    fontSize = 12.sp,
                    color = GrisTexto
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(8.dp))
            BotonAzul(texto = "Entendido", onClick = onAtras)
            Spacer(Modifier.height(16.dp))
        }
    }
}