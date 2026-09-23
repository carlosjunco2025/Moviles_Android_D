package com.tuapp.navlab_Junco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tuapp.navlab_Junco.navigation.AppNavigation
import com.tuapp.navlab_Junco.ui.theme.Semana05Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Semana05Theme {
                AppNavigation()
            }
        }
    }
}
