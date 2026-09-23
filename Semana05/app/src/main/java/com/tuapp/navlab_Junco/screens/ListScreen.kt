package com.tuapp.navlab_Junco.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.tuapp.navlab_Junco.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    val items = (1..10).map { "Elemento $it" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Elementos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(items.size) { index ->
                ListItem(
                    headlineContent = { Text(items[index]) },
                    supportingContent = { Text("Haz clic para ver mas detalles") },
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Detail.createRoute(index + 1))
                    }
                )
                HorizontalDivider()
            }
        }
    }
}