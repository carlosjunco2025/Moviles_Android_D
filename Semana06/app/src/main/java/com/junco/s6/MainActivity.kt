package com.junco.s6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TECSUPStoreApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TECSUPStoreApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "TECSUP Store",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Categorías") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Mis Pedidos") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Text("☰", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                TarjetaProducto(nombreProducto = "Laptop Gamer TECSUP", precio = "S/ 4,500.00")
                TarjetaProducto(nombreProducto = "Mouse Inalámbrico RGB", precio = "S/ 85.00")
            }
        }
    }
}

@Composable
fun TarjetaProducto(
    nombreProducto: String,
    precio: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombreProducto,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = precio,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Text("⋮", style = MaterialTheme.typography.headlineMedium)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ver Detalle") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Compartir") },
                        onClick = { expanded = false }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = { expanded = false }
                    )
                }
            }
        }
    }
}