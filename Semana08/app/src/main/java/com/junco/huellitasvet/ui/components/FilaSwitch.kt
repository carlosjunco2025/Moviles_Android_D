package com.junco.huellitasvet.ui.components

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

/** Preferencia con titulo, descripcion y un Switch a la derecha. */
@Composable
fun FilaSwitch(
    titulo: String,
    descripcion: String,
    activo: Boolean,
    onCambio: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(titulo, fontWeight = FontWeight.SemiBold)
            Text(descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = activo, onCheckedChange = onCambio)
    }
}
