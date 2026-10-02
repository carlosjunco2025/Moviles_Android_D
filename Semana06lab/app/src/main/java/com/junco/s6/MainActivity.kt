package com.junco.s6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val snackbarHostState = remember { SnackbarHostState() }
    
    var seccionActual by remember { mutableStateOf("Inicio") }

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
                    selected = seccionActual == "Inicio",
                    onClick = {
                        seccionActual = "Inicio"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Categorías") },
                    selected = seccionActual == "Categorías",
                    onClick = {
                        seccionActual = "Categorías"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Mis Pedidos") },
                    selected = seccionActual == "Mis Pedidos",
                    onClick = {
                        seccionActual = "Mis Pedidos"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Ajustes") },
                    selected = seccionActual == "Ajustes",
                    onClick = {
                        seccionActual = "Ajustes"
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(seccionActual) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Text("☰", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (seccionActual) {
                    "Inicio" -> SeccionInicio(
                        onAccionRealizada = { mensaje ->
                            scope.launch { snackbarHostState.showSnackbar(mensaje) }
                        }
                    )
                    "Categorías" -> SeccionCategorias()
                    "Mis Pedidos" -> SeccionPedidos()
                    "Ajustes" -> SeccionAjustes()
                }
            }
        }
    }
}

@Composable
fun SeccionInicio(onAccionRealizada: (String) -> Unit) {
    Column {
        TarjetaProducto(
            nombreProducto = "Laptop Gamer TECSUP",
            precio = "S/ 4,500.00",
            onAccionRealizada = onAccionRealizada
        )
        TarjetaProducto(
            nombreProducto = "Mouse Inalámbrico RGB",
            precio = "S/ 85.00",
            onAccionRealizada = onAccionRealizada
        )
    }
}

@Composable
fun SeccionCategorias() {
    val categorias = listOf("Laptops y Laptops Gamer", "Periféricos y Mouse", "Monitores", "Accesorios")
    LazyColumn {
        items(categorias) { cat ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = cat,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun SeccionPedidos() {
    Column {
        Text("Historial de Compras", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pedido #1024 - Completado", style = MaterialTheme.typography.titleMedium)
                Text("Total: S/ 4,585.00", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun SeccionAjustes() {
    Column {
        Text("Ajustes de la App", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Versión de la App: 1.0.0", style = MaterialTheme.typography.bodyLarge)
        Text("Usuario: Carlos Junco", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TarjetaProducto(
    nombreProducto: String,
    precio: String,
    onAccionRealizada: (String) -> Unit
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
                        onClick = {
                            expanded = false
                            onAccionRealizada("Viendo detalle de $nombreProducto")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Compartir") },
                        onClick = {
                            expanded = false
                            onAccionRealizada("Compartiendo $nombreProducto")
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            expanded = false
                            onAccionRealizada("Editando $nombreProducto")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            expanded = false
                            onAccionRealizada("Eliminando $nombreProducto")
                        }
                    )
                }
            }
        }
    }
}
