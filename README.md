Lab 05 (TecsupFit)

## 1. Prompt Inicial

Actúa como desarrollador Senior de Android (Kotlin + Jetpack Compose, Material 3). Genera la app "TECSUP Fit" con código COMPLETO, archivo por archivo, con todos los imports y sin "// resto del código". El resultado debe ser VISUALMENTE IDÉNTICO al diseño que describo. NO inventes estilos, NO agregues elementos que no pida, NO uses valores por defecto de Material donde yo doy medidas.

==================================================
REGLAS OBLIGATORIAS
==================================================
- Sin ViewModel ni MVVM. Estado solo con remember / rememberSaveable / mutableStateOf / mutableStateListOf.
- Solo Compose, nada de XML.
- Todas las pantallas usan Scaffold y aplican innerPadding.
- PROHIBIDO usar TopAppBar, CenterAlignedTopAppBar, NavigationBar, NavigationBarItem, FilterChip, AssistChip y Card. Todo se construye con Row, Column, Box, Text, Canvas y Modifier (background, clip, clickable, padding, size, border).
- Fondo de todas las pantallas: blanco puro.
- Tema siempre claro, dynamicColor = false. No renombres la función del tema de mi proyecto.
- MainActivity: enableEdgeToEdge(); setContent { MiTema { AppNavigation() } }.
- Textos exactamente como están escritos, con tildes.
- Paquete: el de mi proyecto.

Dependencia (build.gradle.kts :app):
implementation("androidx.navigation:navigation-compose:2.7.7")

==================================================
COLORES (ui/theme/Color.kt)
==================================================
VerdePrincipal    = Color(0xFF0F6E56)
VerdeClaro        = Color(0xFFE1F5EE)
VerdeExito        = Color(0xFF1D9E75)
TextoSubtituloBar = Color(0xFFD2EBE4)
Superficie        = Color(0xFFF0F0F0)
Fondo             = Color(0xFFFFFFFF)
TextoPrincipal    = Color(0xFF1E1E1E)
TextoSecundario   = Color(0xFF6E6E6E)
TextoCuerpo       = Color(0xFF464646)
TextoInactivo     = Color(0xFF3C3C3C)
Divisor           = Color(0xFFE1E1E1)
GrisBadge         = Color(0xFFE6E6E6)
Theme.kt: lightColorScheme(primary = VerdePrincipal, onPrimary = blanco, background = Fondo, surface = Fondo, onBackground = TextoPrincipal, onSurface = TextoPrincipal).

==================================================
ESTRUCTURA DE ARCHIVOS
==================================================
data/Clase.kt, data/Reserva.kt
navigation/Screen.kt, navigation/AppNavigation.kt
components/Componentes.kt, components/BarraInferior.kt
screens/InicioScreen.kt, DetalleClaseScreen.kt, ConfirmacionScreen.kt, ReservasScreen.kt, RutinasScreen.kt, PerfilScreen.kt, BotonPrincipal.kt

==================================================
DATOS
==================================================
data class Clase(id: Int, nombre: String, dia: String, hora: String, sala: String, duracion: Int, descripcion: String, cuposDisponibles: Int, cuposTotales: Int)
object DatosClases con:
- listaClases:
  Clase(1, "Yoga funcional", "Hoy", "7:00 am", "Sala 2", 60, "Movilidad y fuerza con peso corporal. Ideal para empezar el día.", 5, 15)
  Clase(2, "Cross Training", "Hoy", "6:00 pm", "Sala 1", 45, "Entrenamiento funcional de alta intensidad. Cupos limitados.", 8, 12)
  Clase(3, "Spinning", "Hoy", "7:30 pm", "Sala 3", 50, "Ciclismo indoor con música y cambios de ritmo.", 10, 20)
  Clase(4, "Pilates", "Mié", "8:00 am", "Sala 2", 50, "Control postural, respiración y fortalecimiento del core.", 6, 12)
  Clase(5, "Box funcional", "Jue", "7:00 pm", "Sala 1", 60, "Técnica de golpes y circuitos de resistencia.", 9, 14)
- filtros = listOf("Hoy", "Esta semana")
- fun buscar(id: Int): Clase

data class Reserva(clase: String, fecha: String, estado: String)   // fecha ej: "Hoy, 6:00 pm"
En AppNavigation: val reservas = remember { mutableStateListOf(
  Reserva("Cross Training", "Hoy, 6:00 pm", "Confirmada"),
  Reserva("Yoga funcional", "Ayer, 7:00 am", "Completada")) }
Al reservar: si ya existe una Reserva "Confirmada" con el mismo nombre de clase NO se duplica; si no existe, se inserta en el índice 0 con estado "Confirmada" y fecha "${dia}, ${hora}".

==================================================
COMPONENTES REUTILIZABLES (components/Componentes.kt)
==================================================
1) IconosBarraEstado(oscuros: Boolean): LocalView + SideEffect + WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = oscuros. Inicio usa false (íconos blancos); todas las demás true.

2) IconoMancuerna(ancho: Dp, alto: Dp, color = VerdePrincipal): mancuerna dibujada con Canvas. NO usar íconos de Material.
   - Barra central: drawRect horizontal centrada, alto = 40% del alto, desde 15% hasta 85% del ancho.
   - Dos discos: drawRoundRect, cada uno de ancho = 17% del ancho y alto = 100% del alto, uno pegado al borde izquierdo y otro al derecho, cornerRadius = 25% del ancho del disco.

3) IconoCheck(tamano: Dp, color): Canvas con Path: moveTo(6%,52%) → lineTo(38%,84%) → lineTo(96%,14%), Stroke grosor = 14% del ancho, cap Butt, join Miter. NO usar Icons.Filled.Check.

4) BarraSuperiorBlanca(titulo, tamanoTitulo = 17.sp, onBack: (() -> Unit)? = null):
   Row fillMaxWidth, fondo blanco, statusBarsPadding(), padding(start 20dp, end 20dp, top 20dp, bottom 12dp), heightIn(min 32dp), centrado vertical.
   Si hay onBack: Text("←") mismo tamaño y Bold que el título, clickable sin ripple, padding end 6dp.
   Text(titulo) Bold TextoPrincipal.

5) BotonPrincipal(texto, onClick): Button navigationBarsPadding(), padding(start 20dp, end 20dp, bottom 22dp), fillMaxWidth, alto 60dp, RoundedCornerShape(12.dp), containerColor VerdePrincipal, elevación 0, texto 15.sp Bold blanco. Se coloca en el bottomBar del Scaffold.

==================================================
BARRA INFERIOR (components/BarraInferior.kt)
==================================================
BarraInferior(rutaActual: String?, onTabClick: (String) -> Unit). Se usa como bottomBar del Scaffold en Inicio, Reservas, Rutinas y Perfil (NO en Detalle ni Confirmación).
Column fillMaxWidth, fondo blanco, navigationBarsPadding():
  - HorizontalDivider(padding horizontal 12dp, 1dp, Divisor).
  - Row fillMaxWidth, padding(top 14dp, bottom 12dp). 4 pestañas con weight(1f): "Inicio" (ruta "inicio"), "Reservas" ("reservas"), "Rutinas" ("rutinas"), "Perfil" ("perfil").
  Cada pestaña: Column centrada horizontalmente, clickable sin ripple:
    · Box 26dp con border(CircleShape): activa → 2.dp VerdePrincipal; inactiva → 1.5.dp TextoInactivo. Es un CÍRCULO VACÍO, NO un ícono.
    · Spacer 8dp.
    · Text 12.sp: activa → Bold VerdePrincipal; inactiva → Normal TextoSecundario.
  La pestaña activa se decide comparando su ruta con rutaActual (que viene de navController.currentBackStackEntryAsState()).

==================================================
NAVEGACIÓN
==================================================
sealed class Screen(route):
Inicio "inicio"; Detalle "detalle/{claseId}" (createRoute(id)); Confirmacion "confirmacion/{claseId}" (createRoute(id)); Reservas "reservas"; Rutinas "rutinas"; Perfil "perfil".
claseId = NavType.IntType.

AppNavigation:
- rememberNavController; rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route.
- onTabClick(ruta): si es "inicio" → popBackStack("inicio", false); si no y es distinta a la actual → navigate(ruta) { popUpTo("inicio") { saveState = true }; launchSingleTop = true; restoreState = true }.
- Flujo: Inicio → Detalle(claseId) → Confirmacion(claseId).
- En Detalle, "Reservar cupo": agrega la Reserva (regla de no duplicar) y navega a Confirmacion(claseId).
- En Confirmacion: "Ver mis reservas" → navigate("reservas") { popUpTo("inicio"); launchSingleTop = true }. BackHandler → popBackStack("inicio", false).

==================================================
PANTALLA 1: INICIO
==================================================
Scaffold(containerColor blanco, bottomBar = BarraInferior).
topBar = Column fillMaxWidth, background VerdePrincipal, statusBarsPadding(), padding(start 20dp, end 20dp, top 18dp, bottom 18dp):
  Text("TECSUP Fit", 22.sp, Bold, blanco); Spacer 4dp; Text("Hola, Diego", 13.sp, TextoSubtituloBar).
Contenido (Column con innerPadding):
  - Spacer 20dp.
  - LazyRow(contentPadding horizontal 20dp, spacedBy 8dp) con los chips "Hoy" y "Esta semana".
    Chip = Box alto 34dp, clip(RoundedCornerShape(50)), padding horizontal 14dp, texto 12.sp centrado.
    Seleccionado: fondo VerdePrincipal, texto blanco Medium. No seleccionado: fondo Superficie, texto TextoInactivo.
    Estado: rememberSaveable { mutableStateOf("Hoy") }.
    Filtro: "Hoy" muestra solo las clases con dia == "Hoy"; "Esta semana" muestra todas.
  - Spacer 20dp. Text("Clases disponibles", 15.sp, Bold, TextoPrincipal, padding horizontal 20dp). Spacer 12dp.
  - LazyColumn(contentPadding start/end 20dp bottom 20dp, spacedBy 11dp). Tarjeta de clase:
    Row fillMaxWidth, alto 78dp, clip RoundedCornerShape(12.dp), fondo Superficie, clickable → Detalle, padding horizontal 12dp, centrado vertical:
    · Box 54dp, clip RoundedCornerShape(12.dp), fondo VerdeClaro, con IconoMancuerna(ancho 36dp, alto 16dp) centrado.
    · Spacer 12dp.
    · Column: nombre 15.sp Bold TextoPrincipal; Spacer 2dp; subtítulo 13.sp TextoSecundario = "${hora} · ${sala}" (si dia != "Hoy": "${dia} · ${hora} · ${sala}").

==================================================
PANTALLA 2: DETALLE DE CLASE (recibe claseId)
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Detalle de clase", onBack = popBackStack). bottomBar = BotonPrincipal("Reservar cupo").
Column padding horizontal 20dp:
  Spacer 12dp;
  Box fillMaxWidth, alto 126dp, clip RoundedCornerShape(14.dp), fondo VerdeClaro, con IconoMancuerna(ancho 104dp, alto 38dp) centrado;
  Spacer 22dp; nombre 20.sp Bold TextoPrincipal;
  Spacer 4dp; "${hora} · ${sala} · ${duracion} min" 13.sp TextoSecundario;
  Spacer 16dp; Text(descripcion, 13.sp, lineHeight 20.sp, TextoCuerpo, fillMaxWidth(0.78f));
  Spacer 22dp; "${cuposDisponibles} de ${cuposTotales} cupos disponibles" 13.sp TextoCuerpo.
Todo alineado a la izquierda.

==================================================
PANTALLA 3: CONFIRMACIÓN (recibe claseId)
==================================================
Scaffold: topBar = Spacer(Modifier.fillMaxWidth().statusBarsPadding()) (sin barra visible). Sin bottomBar.
Column fillMaxSize, centrada vertical y horizontalmente:
  Box 88dp CircleShape fondo VerdeClaro con IconoCheck(38.dp, VerdeExito);
  Spacer 22dp; "¡Cupo reservado!" 20.sp Bold TextoPrincipal;
  Spacer 8dp; nombre de la clase 13.sp TextoSecundario;
  Spacer 4dp; "${dia}, ${hora} · ${sala}" (ej: "Hoy, 6:00 pm · Sala 1") 13.sp TextoSecundario;
  Spacer 36dp; Box 240dp x 56dp, clip RoundedCornerShape(14.dp), fondo Superficie, clickable, Text("Ver mis reservas", 14.sp, TextoPrincipal) centrado;
  Spacer 40dp.

==================================================
PANTALLA 4: MIS RESERVAS
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Mis reservas", 22.sp). bottomBar = BarraInferior.
LazyColumn(contentPadding start/end 20dp, top 16dp, bottom 20dp, spacedBy 16dp). Tarjeta por reserva:
  confirmada = estado == "Confirmada".
  Forma: si confirmada RoundedCornerShape(topStart 0, bottomStart 0, topEnd 14, bottomEnd 14); si no RoundedCornerShape(14.dp).
  Row fillMaxWidth, alto 106dp, clip(forma), fondo Superficie:
  · Si confirmada: Box width 6dp fillMaxHeight fondo VerdePrincipal.
  · Column fillMaxHeight, padding(start = 16dp si confirmada o 22dp si no, end 16dp), verticalArrangement Center:
    clase 16.sp Bold TextoPrincipal; Spacer 4dp; fecha 13.sp TextoSecundario; Spacer 8dp;
    Badge: Box 118dp x 30dp, clip RoundedCornerShape(50), texto 12.sp Medium centrado.
    Confirmada: fondo VerdeClaro, texto VerdeExito. Completada: fondo GrisBadge, texto TextoSecundario.

==================================================
PANTALLA 5: RUTINAS
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Rutinas", 22.sp). bottomBar = BarraInferior.
LazyColumn igual que Mis reservas (spacedBy 11dp). Tarjeta con el MISMO estilo que la tarjeta de clase de Inicio (Box 54dp VerdeClaro con IconoMancuerna 36x16dp, nombre 15.sp Bold, subtítulo 13.sp TextoSecundario):
("Tren superior", "5 ejercicios · 30 min"), ("Piernas y glúteos", "6 ejercicios · 40 min"), ("Core y abdomen", "4 ejercicios · 20 min").

==================================================
PANTALLA 6: MI PERFIL
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Mi perfil", 22.sp). bottomBar = BarraInferior.
Column padding horizontal 20dp, centrada horizontalmente:
  Spacer 24dp; Box 100dp CircleShape fondo VerdeClaro con "DR" 26.sp Bold VerdePrincipal;
  Spacer 12dp; "Diego Ramos" 17.sp Bold TextoPrincipal;
  Spacer 2dp; "Plan Premium" 13.sp TextoSecundario;
  Spacer 28dp; Row fillMaxWidth spacedBy 8dp con 2 cajas de estadísticas (cada una weight 1f, alto 78dp, clip RoundedCornerShape(12.dp), fondo Superficie, Column centrada vertical y horizontalmente):
    · "14" 20.sp Bold TextoPrincipal + Spacer 4dp + "Clases" 12.sp TextoSecundario.
    · "3" 20.sp Bold TextoPrincipal + Spacer 4dp + "Rachas" 12.sp TextoSecundario.

==================================================
ENTREGA
==================================================
Entrega cada archivo completo con su ruta como título. Comentarios breves en español: cómo viaja el claseId desde Inicio hasta Confirmación, cómo sabe la barra inferior qué pestaña resaltar (currentBackStackEntryAsState), y por qué los chips funcionan como selección única.

1. Requisitos Funcionales (RF)
RF-01: Pantalla Principal e Inicio

RF-01.1: Encabezado con marca "TECSUP Fit", saludo personalizado ("Hola, Diego") e integración con barra de estado en contraste (íconos claros sobre fondo verde).

RF-01.2: Filtrado por categoría mediante chips interactivos con selección única ("Hoy" y "Esta semana").

RF-01.3: Filtro reactivo en tiempo real: el chip "Hoy" filtra las clases cuyo día es exactamente "Hoy", mientras que "Esta semana" muestra la totalidad del catálogo.

RF-01.4: Catálogo interactivo de clases disponibles mostrando ícono de mancuerna vectorial, nombre de la disciplina, hora asignada y sala.

RF-02: Detalle de la Clase

RF-02.1: Transmisión de parámetro claseId a través del sistema de rutas de navegación.

RF-02.2: Banner superior con ilustración vectorial customizada de la clase.

RF-02.3: Despliegue de metadatos completos: hora, sala, duración en minutos, descripción extensa y contador de cupos disponibles vs. totales (ej: "5 de 15 cupos disponibles").

RF-02.4: Botón de acción fija en la parte inferior ("Reservar cupo").

RF-03: Gestión de Reservas (Lógica Anti-Duplicados)

RF-03.1: Validación al reservar: si la clase ya cuenta con una reserva activa en estado "Confirmada", no se duplica la entrada.

RF-03.2: Inserción de la nueva reserva en el tope de la lista (índice 0) con formato de fecha dinámico "${dia}, ${hora}" y estado inicial "Confirmada".

RF-04: Confirmación de Reserva

RF-04.1: Pantalla limpia sin barras de navegación con ícono vectorial de Check sobre un contenedor circular de éxito.

RF-04.2: Resumen detallado con el nombre de la clase, día, hora y sala asignada.

RF-04.3: Redirección asistida hacia la pantalla de "Mis reservas" mediante botón dedicado y control del stack con BackHandler que retorna a la pantalla de Inicio.

RF-05: Pantalla de Mis Reservas

RF-05.1: Renderizado condicional de tarjetas según el estado de la reserva:

Confirmada: Forma asimétrica con borde/indicador vertical lateral izquierdo de 6dp en VerdePrincipal y badge de estado verde (VerdeClaro / VerdeExito).

Completada: Forma simétrica estándar con badge en escala de grises (GrisBadge / TextoSecundario).

RF-06: Catálogo de Rutinas

RF-06.1: Lista estructurada de rutinas de entrenamiento predeterminadas ("Tren superior", "Piernas y glúteos", "Core y abdomen") reutilizando la estética visual de las tarjetas de Inicio.

RF-07: Perfil de Usuario

RF-07.1: Resumen del suscriptor mostrando avatar circular con iniciales ("DR"), nombre ("Diego Ramos") y nivel de suscripción ("Plan Premium").

RF-07.2: Contadores estadísticos en tarjetas divididas equitativamente: total de "Clases" tomadas y "Rachas" acumuladas.

RF-08: Navegación Inferior Global (BarraInferior)

RF-08.1: Barra de navegación persistente en las pantallas principales (Inicio, Reservas, Rutinas, Perfil).

RF-08.2: Indicador dinámico de pestaña activa basado en la ruta actual detectada por el controlador de navegación.

RF-08.3: Reorganización del BackStack para evitar acumulaciones de pantallas al alternar entre pestañas (popUpTo("inicio") con saveState y restoreState).

2. Requisitos No Funcionales y Técnicos (RNF)
RNF-01: Estado y Arquitectura Simplificada (No-MVVM)

Prohibición de ViewModel: Gestión de estado estricta basada exclusivamente en Jetpack Compose con remember, rememberSaveable, mutableStateOf y mutableStateListOf.

Ausencia total de archivos XML de layout o componentes legados.

RNF-02: Restricciones de Componentes Material Design

Prohibición explícita de componentes de alto nivel: No uso de TopAppBar, CenterAlignedTopAppBar, NavigationBar, NavigationBarItem, FilterChip, AssistChip ni Card.

Atomización UI: Todos los componentes, tarjetas, barras superiores, botones y chips se construyen desde cero utilizando exclusivamente Row, Column, Box, Text, Canvas y modificadores de Modifier (background, clip, clickable, padding, size, border).

RNF-03: Gráficos Vectoriales Manuales (Canvas API)

Dibuja de forma programática las ilustraciones vectoriales sin depender de librerías de íconos externas o archivos de imagen:

Mancuerna: Construida en Canvas uniendo un drawRect central horizontal y dos drawRoundRect laterales para los discos.

Check: Dibujado con un Path preciso de tres puntos cardinales con stroke customizado.

RNF-04: Sistema de Diseño y Colorimetría Fija

Paleta estricta basada en el tema corporativo TECSUP Fit (VerdePrincipal #0F6E56, VerdeClaro #E1F5EE, VerdeExito #1D9E75, Fondo #FFFFFF puro).

Desactivación de colores dinámicos de Android (dynamicColor = false).

Integración con la barra del sistema mediante SideEffect y WindowCompat.getInsetsController para alternar la visibilidad de la status bar (isAppearanceLightStatusBars).

RNF-05: Control de Navegación y Rutas Tipadas

Navegación centralizada en AppNavigation mediante NavHost y rememberNavController.
    
Paso de argumentos numéricos tipados (NavType.IntType) para el parámetro claseId.         

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/18119256-a7a7-485f-9fc0-2c9590e3aced" />

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/1876b095-53ee-49e4-b108-64e001a96788" />

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/f6b8fb2e-1340-44db-9894-bfbedd62f21f" />

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/7b07d9cb-1500-4174-898a-affda73ee1be" />

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/63e3dba3-13e4-42e4-95b9-f1b2c3a58f3a" />

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/b5640ee3-6856-4333-ac02-58a16361f43f" />

<img width="1406" height="879" alt="image" src="https://github.com/user-attachments/assets/d7fab1a7-69b9-4dbf-bd2b-6ce26712879d" />
