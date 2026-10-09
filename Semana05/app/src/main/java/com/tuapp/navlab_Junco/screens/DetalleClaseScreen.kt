package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.navlab_Junco.components.*
import com.tuapp.navlab_Junco.data.Clase
import com.tuapp.navlab_Junco.data.DatosClases
import com.tuapp.navlab_Junco.ui.theme.*

@Composable
fun DetalleClaseScreen(
    claseId: Int,
    onBack: () -> Unit,
    onReservarClick: (Clase, String, String) -> Unit
) {
    val clase = DatosClases.buscar(claseId)

    Scaffold(
        containerColor = Fondo,
        topBar = {
            BarraSuperiorBlanca(titulo = "Detalle de clase", onBack = onBack)
        },
        bottomBar = {
            BotonPrincipal(texto = "Reservar cupo") {
                onReservarClick(clase, clase.dia, clase.hora)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(126.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(VerdeClaro),
                contentAlignment = Alignment.Center
            ) {
                IconoMancuerna(ancho = 104.dp, alto = 38.dp)
            }
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = clase.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${clase.hora} · ${clase.sala} · ${clase.duracion} min",
                fontSize = 13.sp,
                color = TextoSecundario
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = clase.descripcion,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = TextoCuerpo,
                modifier = Modifier.fillMaxWidth(0.78f)
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
                fontSize = 13.sp,
                color = TextoCuerpo
            )
        }
    }
}
