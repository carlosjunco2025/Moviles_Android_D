cat << 'EOF' > app/src/main/java/com/junco/s6/MainActivity.kt
package com.junco.s6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

val PurplePrimary = Color(0xFF5E227F)
val PurpleLightBg = Color(0xFFF3E8F9)
val PurpleSelected = Color(0xFFE9D5F5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = PurplePrimary,
                    secondary = PurplePrimary
                )
            ) {
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
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = Color.White
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(PurpleSelected, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "MR",
                            fontWeight = FontWeight.Bold,
                            color = PurplePrimary,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Maria Rojas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "maria@tecsup.edu.pe",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )

                val items = listOf(
                    "Inicio" to Icons.Outlined.Home,
                    "Mis pedidos" to Icons.Outlined.ShoppingBag,
                    "Favoritos" to Icons.Outlined.FavoriteBorder,
                    "Perfil" to Icons.Outlined.Person,
                    "Cerrar sesion" to Icons.Outlined.ExitToApp
                )

                items.forEach { (titulo, icono) ->
                    val isSelected = seccionActual == titulo
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                icono,
                                contentDescription = titulo,
                                tint = if (isSelected) PurplePrimary else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = titulo,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) PurplePrimary else Color.DarkGray
                            )
                        },
                        selected = isSelected,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = PurpleSelected,
                            unselectedContainerColor = Color.Transparent
                        ),
                        onClick = {
                            seccionActual = titulo
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "TECSUP Store",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                            Text(
                                "Mas vendidos",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PurplePrimary
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFFAFAFA))
                    .padding(16.dp)
            ) {
                when (seccionActual) {
                    "Inicio" -> SeccionProductos(
                        onAccionRealizada = { mensaje ->
                            scope.launch { snackbarHostState.showSnackbar(mensaje) }
                        }
                    )
                    else -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sección: $seccionActual", fontSize = 18.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

data class Producto(val nombre: String, val precio: String)

@Composable
fun SeccionProductos(onAccionRealizada: (String) -> Unit) {
    val listaProductos = listOf(
        Producto("Audifonos", "S/ 89.00"),
        Producto("Smartwatch", "S/ 199.00"),
        Producto("Funda celular", "S/ 25.00")
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(listaProductos) { prod ->
            TarjetaProductoItem(
                producto = prod,
                onAccionRealizada = onAccionRealizada
            )
        }
    }
}

@Composable
fun TarjetaProductoItem(
    producto: Producto,
    onAccionRealizada: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PurpleLightBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(PurpleSelected, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = null,
                        tint = PurplePrimary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = producto.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = producto.precio,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Opciones",
                        tint = Color.Gray
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("Favoritos", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.DarkGray)
                        },
                        onClick = {
                            expanded = false
                            onAccionRealizada("Añadido a Favoritos: ${producto.nombre}")
                        }
                    )
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                    DropdownMenuItem(
                        text = { Text("Compartir", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Default.Share, contentDescription = null, tint = Color.DarkGray)
                        },
                        onClick = {
                            expanded = false
                            onAccionRealizada("Compartiendo: ${producto.nombre}")
                        }
                    )
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                    DropdownMenuItem(
                        text = { Text("Reportar", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.DarkGray)
                        },
                        onClick = {
                            expanded = false
                            onAccionRealizada("Reportado: ${producto.nombre}")
                        }
                    )
                }
            }
        }
    }
}
EOF
git add . && git commit -m "feat(ui): implementacion exacta de UI segun diseño (Commit 6)" && git push origin main
