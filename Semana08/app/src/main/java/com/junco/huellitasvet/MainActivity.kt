package com.junco.huellitasvet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.junco.huellitasvet.ui.navigation.NavVet

/** Unica Activity: toda la app vive en Compose y la navegacion la resuelve NavVet. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NavVet() }
    }
}
