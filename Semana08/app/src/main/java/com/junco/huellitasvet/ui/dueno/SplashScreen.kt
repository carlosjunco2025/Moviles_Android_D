package com.junco.huellitasvet.ui.dueno

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.platform.*
import androidx.compose.foundation.text.*
import com.junco.huellitasvet.model.Especie
import com.junco.huellitasvet.ui.components.MascotaAvatar
import com.junco.huellitasvet.ui.theme.degradadoMarca

/** Pantalla de bienvenida: fondo con degradado, logo y boton para entrar. */
@Composable
fun SplashScreen(onComenzar: () -> Unit) {
    Box(Modifier.fillMaxSize().background(degradadoMarca())) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(32.dp),
                color = Color.White,
                shadowElevation = 10.dp,
                modifier = Modifier.size(104.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Filled.Pets, contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(58.dp)
                    )
                }
            }
            Spacer(Modifier.height(22.dp))
            Text(
                "HuellitasVet", color = Color.White, fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                "Cuidamos a quienes más quieres",
                color = Color.White.copy(alpha = 0.88f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(30.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                MascotaAvatar(Especie.PERRO.name, tamano = 64.dp)
                MascotaAvatar(Especie.GATO.name, tamano = 64.dp)
                MascotaAvatar(Especie.OTRO.name, tamano = 64.dp)
            }
            Spacer(Modifier.height(44.dp))
            Button(
                onClick = onComenzar,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                )
            ) {
                Text("Comenzar", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                "Datos ficticios · Laboratorio 08",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.75f)
            )
        }
    }
}
