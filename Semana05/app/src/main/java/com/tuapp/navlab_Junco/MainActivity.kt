<<<<<<< Updated upstream:Semana05/app/src/main/java/com/tuapp/navlab_Junco/MainActivity.kt
package com.tuapp.navlab_Junco
=======
package com.tuapp.tecsupfit
>>>>>>> Stashed changes:app/src/main/java/com/tuapp/tecsupfit/MainActivity.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
<<<<<<< Updated upstream:Semana05/app/src/main/java/com/tuapp/navlab_Junco/MainActivity.kt
import com.tuapp.navlab_Junco.navigation.AppNavigation
import com.tuapp.navlab_Junco.ui.theme.Semana05Theme
=======
import com.tuapp.tecsupfit.navigation.AppNavigation
import com.tuapp.tecsupfit.ui.theme.TECSUPFitTheme
>>>>>>> Stashed changes:app/src/main/java/com/tuapp/tecsupfit/MainActivity.kt

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
