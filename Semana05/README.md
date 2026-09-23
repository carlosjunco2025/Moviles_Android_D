
## 1. Prompt Inicial
Actúa como desarrollador Senior de Android (Kotlin + Jetpack Compose, Material 3). Genera la app "Clínica Salud+" con código COMPLETO, archivo por archivo, con todos los imports y sin "// resto del código". El resultado debe ser VISUALMENTE IDÉNTICO al diseño que describo. NO inventes estilos, NO agregues elementos que no pida, NO uses valores por defecto de Material donde yo doy medidas.

==================================================
REGLAS OBLIGATORIAS
==================================================
- Sin ViewModel ni MVVM. Estado solo con remember / rememberSaveable / mutableStateOf / mutableStateListOf.
- Solo Compose, nada de XML.
- Todas las pantallas usan Scaffold y aplican innerPadding.
- PROHIBIDO usar TopAppBar, CenterAlignedTopAppBar, FilterChip, AssistChip, Card y NavigationDrawerItem. Todo se construye con Row, Column, Box, Text, Canvas y Modifier (background, clip, clickable, padding, size, border).
- Fondo de todas las pantallas: blanco puro.
- Tema siempre claro, dynamicColor = false. No renombres la función del tema de mi proyecto.
- MainActivity: enableEdgeToEdge(); setContent { MiTema { AppNavigation() } }.
- Textos exactamente como están escritos, con tildes.
- Paquete: el de mi proyecto.

Dependencias (build.gradle.kts :app):
implementation("androidx.navigation:navigation-compose:2.7.7")
implementation("androidx.compose.material:material-icons-core")

==================================================
COLORES (ui/theme/Color.kt)
==================================================
MoradoPrincipal   = Color(0xFF5B2A86)
MoradoClaro       = Color(0xFFEEE6F7)
TextoSubtituloBar = Color(0xFFDCCDEB)
Superficie        = Color(0xFFF3F1F7)
Fondo             = Color(0xFFFFFFFF)
TextoPrincipal    = Color(0xFF1E1E1E)
TextoSecundario   = Color(0xFF6E6E6E)
TextoCuerpo       = Color(0xFF464646)
TextoItemDrawer   = Color(0xFF3C3C3C)
Divisor           = Color(0xFFE1E1E1)
Estrella          = Color(0xFFBA8A00)
VerdeExito        = Color(0xFF1D9E75)
VerdeExitoFondo   = Color(0xFFE1F5EE)
GrisBadge         = Color(0xFFE6E6E6)
Theme.kt: lightColorScheme(primary = MoradoPrincipal, onPrimary = blanco, background = Fondo, surface = Fondo, onBackground = TextoPrincipal, onSurface = TextoPrincipal).

==================================================
ESTRUCTURA DE ARCHIVOS
==================================================
data/Medico.kt, data/Cita.kt
navigation/Screen.kt, navigation/AppNavigation.kt
components/Componentes.kt, components/DrawerContent.kt
screens/InicioScreen.kt, PerfilMedicoScreen.kt, AgendarCitaScreen.kt, ConfirmacionScreen.kt, MisCitasScreen.kt, HistorialScreen.kt, PerfilScreen.kt, BotonPrincipal.kt

==================================================
DATOS
==================================================
data class Medico(id: Int, nombre: String, especialidad: String, titulo: String, experiencia: Int, rating: Double, resenas: Int, descripcion: String)
object DatosMedicos con:
- listaMedicos:
  Medico(1, "Dra. Ana Torres", "Cardiología", "Cardióloga", 12, 4.9, 128, "Especialista en arritmias e hipertensión, formación en la Clínica Mayo.")
  Medico(2, "Dr. Luis Vega", "Pediatría", "Pediatra", 9, 4.7, 96, "Especialista en control del niño sano y vacunación infantil.")
  Medico(3, "Dra. Rosa Díaz", "Dermatología", "Dermatóloga", 10, 4.8, 110, "Especialista en dermatología clínica y estética, formación en el Hospital Clínic.")
- especialidades = listOf("Cardiología", "Pediatría")   // SOLO estos 2 chips
- fun buscar(id: Int): Medico

data class Cita(medico: String, fecha: String, hora: String, estado: String)
En AppNavigation: val citas = remember { mutableStateListOf(
  Cita("Dra. Ana Torres", "Viernes 27", "10:30 am", "Confirmada"),
  Cita("Dr. Luis Vega", "Miércoles 15", "3:00 pm", "Completada")) }
Las citas nuevas se insertan en el índice 0 con estado "Confirmada".

==================================================
COMPONENTES REUTILIZABLES (components/Componentes.kt)
==================================================
1) IconosBarraEstado(oscuros: Boolean): con LocalView + SideEffect + WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = oscuros. Inicio usa false (íconos blancos); todas las demás true.

2) IconoMas(tamano: Dp, color = MoradoPrincipal): "+" dibujado con Canvas, DOS drawRect (barra vertical y horizontal) de grosor = 20% del tamaño, puntas rectas. NO usar Icons.Filled.Add.

3) IconoCheck(tamano: Dp, color): Canvas con Path: moveTo(6%,52%) → lineTo(38%,84%) → lineTo(96%,14%), Stroke grosor = 14% del ancho, cap Butt, join Miter. NO usar Icons.Filled.Check.

4) BarraSuperiorBlanca(titulo, tamanoTitulo = 17.sp, onBack: (() -> Unit)? = null, accion: (@Composable () -> Unit)? = null):
   Row fillMaxWidth, fondo blanco, statusBarsPadding(), padding(start 20dp, end 8dp, top 20dp, bottom 12dp), heightIn(min 32dp), centrado vertical.
   Si hay onBack: Text("←") mismo tamaño y Bold que el título, clickable sin ripple, padding end 6dp.
   Text(titulo) Bold TextoPrincipal con weight(1f). Al final, accion si existe.

5) BotonPrincipal(texto, onClick): Button navigationBarsPadding(), padding(start 20dp, end 20dp, bottom 22dp), fillMaxWidth, alto 60dp, RoundedCornerShape(12.dp), containerColor MoradoPrincipal, elevación 0, texto 15sp Bold blanco. Se coloca en el bottomBar del Scaffold.

6) BotonMenu(onClick): IconButton con Icons.Filled.Menu tint TextoPrincipal.

==================================================
NAVEGACIÓN
==================================================
sealed class Screen(route):
Inicio "inicio"; PerfilMedico "perfil_medico/{medicoId}" (createRoute(id)); Agendar "agendar/{medicoId}" (createRoute(id)); Confirmacion "confirmacion/{medicoId}/{fecha}/{hora}" (createRoute(id, fecha, hora) usando Uri.encode en fecha y hora); MisCitas "mis_citas"; Historial "historial"; Perfil "perfil".
medicoId = NavType.IntType; fecha y hora = NavType.StringType.

AppNavigation:
- rememberNavController, rememberDrawerState(Closed), rememberCoroutineScope.
- rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route.
- ModalNavigationDrawer ENVUELVE al NavHost. gesturesEnabled = rutaActual en [inicio, mis_citas, historial, perfil] || drawerState.isOpen.
- Al tocar opción del menú: cerrar drawer; si es "inicio" → popBackStack("inicio", false); si no → navigate(ruta) { popUpTo("inicio") { saveState = true }; launchSingleTop = true; restoreState = true }.
- Flujo: Inicio → PerfilMedico(id) → Agendar(id) → Confirmacion(id, fecha, hora).
- En Agendar, al confirmar: agregar la Cita a la lista y navegar a Confirmacion.
- En Confirmacion: "Ver mis citas" → navigate("mis_citas") { popUpTo("inicio"); launchSingleTop = true }. BackHandler → popBackStack("inicio", false).

==================================================
PANTALLA 1: INICIO
==================================================
Scaffold(containerColor blanco). topBar = Row fillMaxWidth, background MoradoPrincipal, statusBarsPadding(), padding(start 20dp, end 8dp, top 16dp, bottom 16dp), centrado vertical:
  - Column(weight 1f): Text("Clínica Salud+", 22.sp, Bold, blanco); Spacer 4dp; Text("Hola, Juan", 13.sp, TextoSubtituloBar).
  - IconButton con Icons.Filled.Menu blanco a la DERECHA → abre el drawer.
Contenido (Column con innerPadding):
  - Spacer 24dp.
  - LazyRow(contentPadding horizontal 20dp, spacedBy 10dp) con los 2 chips.
    Chip = Box alto 40dp, clip(RoundedCornerShape(50)), padding horizontal 22dp, texto 13sp Medium centrado.
    Seleccionado: fondo MoradoPrincipal, texto blanco. No seleccionado: fondo Superficie, texto TextoItemDrawer.
    Estado: rememberSaveable { mutableStateOf("Cardiología") } (Cardiología marcado al inicio).
    La lista se ORDENA poniendo primero los médicos de la especialidad seleccionada (sortedByDescending { it.especialidad == seleccionada }). No se ocultan médicos.
  - Spacer 20dp. Text("Médicos disponibles", 16.sp, Bold, TextoPrincipal, padding horizontal 20dp). Spacer 12dp.
  - LazyColumn(contentPadding start/end 20dp bottom 20dp, spacedBy 11dp). Tarjeta de médico:
    Row fillMaxWidth, alto 82dp, clip RoundedCornerShape(12.dp), fondo Superficie, clickable, padding horizontal 12dp, centrado vertical:
    · Box 56dp CircleShape fondo MoradoClaro con IconoMas(30.dp) centrado.
    · Spacer 12dp.
    · Column(weight 1f): nombre 15.sp Bold TextoPrincipal; Spacer 2dp; titulo ("Cardióloga") 13.sp TextoSecundario.
    · Row con offset(y = -8.dp): Icons.Filled.Star 18dp tint Estrella; Spacer 3dp; rating 13.sp TextoSecundario.

==================================================
PANTALLA 2: PERFIL DEL MÉDICO (recibe medicoId)
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Perfil del médico", onBack = popBackStack). bottomBar = BotonPrincipal("Agendar cita").
Column centrada horizontalmente:
  Spacer 20dp; Box 100dp CircleShape MoradoClaro con IconoMas(52.dp);
  Spacer 10dp; nombre 20.sp Bold TextoPrincipal;
  Spacer 6dp; "${titulo} · ${experiencia} años exp." 13.sp TextoSecundario;
  Spacer 6dp; Row: Star 17dp Estrella + Spacer 4dp + "${rating} (${resenas} reseñas)" 12.sp TextoSecundario;
  Spacer 28dp; Box fillMaxWidth padding horizontal 20dp con Text(descripcion, 13.sp, lineHeight 21.sp, TextoCuerpo, fillMaxWidth(0.72f)) alineado a la izquierda.

==================================================
PANTALLA 3: AGENDAR CITA (recibe medicoId)
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Agendar cita", onBack). bottomBar = BotonPrincipal("Confirmar cita").
Column padding horizontal 20dp:
  Spacer 12dp; "Selecciona fecha" 13.sp TextoSecundario; Spacer 12dp;
  Row spacedBy 11dp con 3 fechas: ("Jue","26","Jueves 26"), ("Vie","27","Viernes 27"), ("Sáb","28","Sábado 28").
    Box 90dp x 62dp, clip RoundedCornerShape(12.dp), clickable. Column centrada: día 12.sp, número 18.sp Bold.
    Seleccionada: fondo MoradoPrincipal, ambos textos blancos. No seleccionada: fondo Superficie, día TextoSecundario, número TextoPrincipal.
  Spacer 20dp; "Selecciona hora" 13.sp TextoSecundario; Spacer 12dp;
  Row spacedBy 11dp con 3 horas: ("9:00","9:00 am"), ("10:30","10:30 am"), ("3:00","3:00 pm").
    Box 94dp x 48dp, clip RoundedCornerShape(12.dp), texto 13.sp. Seleccionada: fondo MoradoPrincipal texto blanco. No seleccionada: fondo Superficie texto TextoItemDrawer.
  Selección única con un índice por grupo (rememberSaveable), por defecto índice 1 en ambos (Vie 27 y 10:30).

==================================================
PANTALLA 4: CONFIRMACIÓN
==================================================
Scaffold: topBar = Spacer(Modifier.fillMaxWidth().statusBarsPadding()) (sin barra visible).
Column fillMaxSize, centrada vertical y horizontalmente:
  Box 88dp CircleShape fondo VerdeExitoFondo con IconoCheck(44.dp, VerdeExito);
  Spacer 22dp; "¡Cita agendada!" 20.sp Bold TextoPrincipal;
  Spacer 8dp; nombre del médico 13.sp TextoSecundario;
  Spacer 4dp; "$fecha, $hora" 13.sp TextoSecundario;
  Spacer 36dp; Box 200dp x 56dp, clip RoundedCornerShape(14.dp), fondo Superficie, clickable, Text("Ver mis citas", 14.sp, TextoPrincipal) centrado;
  Spacer 40dp.

==================================================
MENÚ LATERAL (components/DrawerContent.kt)
==================================================
ModalDrawerSheet: width 300dp, drawerShape RoundedCornerShape(topEnd 28dp, bottomEnd 28dp), drawerContainerColor blanco, y Modifier.border(2.dp, MoradoPrincipal, esa misma forma).
Cabecera Row padding(start 20, end 20, top 24, bottom 18): Box 54dp CircleShape MoradoClaro con "JP" 17.sp Bold MoradoPrincipal; Spacer 14dp; Column: "Juan Pérez" 16.sp Bold TextoPrincipal, "Paciente" 12.sp TextoSecundario.
HorizontalDivider(padding horizontal 20dp, 1dp, Divisor). Spacer 12dp.
Opciones "Inicio", "Mis citas", "Historial médico", "Perfil". Cada una:
  Row padding horizontal 14dp, fillMaxWidth, alto 56dp, clip RoundedCornerShape(14.dp), fondo MoradoClaro si está seleccionada o transparente si no, clickable, padding start 12dp, centrado vertical:
  · Box 24dp con border(1.5.dp, MoradoPrincipal si seleccionada / TextoPrincipal si no, CircleShape) — un CÍRCULO VACÍO, NO un ícono.
  · Spacer 30dp.
  · Texto 15.sp: seleccionado Bold MoradoPrincipal; no seleccionado Normal TextoItemDrawer.
  Spacer 14dp después de cada opción.

==================================================
PANTALLA 5: MIS CITAS
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Mis citas", 22.sp, accion = BotonMenu).
LazyColumn(contentPadding start/end 20dp, top 16dp, bottom 20dp, spacedBy 16dp). Tarjeta por cita:
  confirmada = estado == "Confirmada".
  Forma: si confirmada RoundedCornerShape(topStart 0, bottomStart 0, topEnd 14, bottomEnd 14); si no RoundedCornerShape(14.dp).
  Row fillMaxWidth, alto 112dp, clip(forma), fondo Superficie:
  · Si confirmada: Box width 6dp fillMaxHeight fondo MoradoPrincipal.
  · Column fillMaxHeight, padding(start = 16dp si confirmada o 22dp si no, end 16dp), verticalArrangement Center:
    médico 16.sp Bold TextoPrincipal; Spacer 4dp; "$fecha, $hora" 13.sp TextoSecundario; Spacer 8dp;
    Badge: Box 118dp x 30dp, clip RoundedCornerShape(50), texto 12.sp Medium centrado.
    Confirmada: fondo VerdeExitoFondo, texto VerdeExito. Completada: fondo GrisBadge, texto TextoSecundario.

==================================================
PANTALLA 6: HISTORIAL MÉDICO
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Historial médico", 22.sp, accion = BotonMenu).
LazyColumn igual que Mis citas. Tarjeta: Column fillMaxWidth, clip RoundedCornerShape(14.dp), fondo Superficie, padding(horizontal 22dp, vertical 18dp): título 16.sp Bold; Spacer 4dp; fecha 13.sp TextoSecundario; Spacer 8dp; detalle 13.sp TextoCuerpo.
Datos: ("Control cardiológico","10 de agosto","Presión arterial estable"), ("Consulta pediátrica","02 de julio","Control general sin observaciones"), ("Evaluación dermatológica","15 de mayo","Dermatitis leve, tratamiento tópico").

==================================================
PANTALLA 7: PERFIL (paciente)
==================================================
Scaffold: topBar = BarraSuperiorBlanca("Perfil", 22.sp, accion = BotonMenu).
Column padding horizontal 20dp, centrada: Spacer 16dp; Box 96dp CircleShape MoradoClaro con "JP" 32.sp Bold MoradoPrincipal; Spacer 12dp; "Juan Pérez" 20.sp Bold; "Paciente" 13.sp TextoSecundario; Spacer 24dp.
Column fillMaxWidth, clip RoundedCornerShape(14.dp), fondo Superficie, padding(horizontal 20dp, vertical 8dp) con 4 filas (Row padding vertical 12dp: Icon 20dp MoradoPrincipal, Spacer 16dp, Column: etiqueta 11.sp TextoSecundario, valor 14.sp SemiBold TextoPrincipal):
Icons.Filled.Email "Correo" "juan.perez@gmail.com"; Icons.Filled.Phone "Teléfono" "+51 987 654 321"; Icons.Filled.Person "DNI" "71234567"; Icons.Filled.DateRange "Citas realizadas" = cantidad de citas con estado "Completada".

==================================================
ENTREGA
==================================================
Entrega cada archivo completo con su ruta como título. Comentarios breves en español: cómo viaja el medicoId por las rutas hasta Confirmación, por qué el drawer envuelve al Scaffold, y por qué la fecha/hora funciona como RadioButton.



1. Requisitos Funcionales
RF-01: Gestión de Menú Lateral (Navigation Drawer)

Menú lateral desplegable con acceso a: Inicio, Mis Citas, Historial Médico y Perfil.

Cabecera personalizada con el avatar con iniciales ("JP"), nombre completo ("Juan Pérez") y rol ("Paciente").

Indicador visual de selección tipo círculo (radio button) personalizado con estado activo/inactivo.

RF-02: Pantalla de Inicio (Catálogo de Médicos)

Cabecera superior con el nombre de la clínica ("Clínica Salud+"), bienvenida personalizada ("Hola, Juan") y botón de acceso al menú lateral.

Filtro por especialidades médicas mediante chips (Cardiología, Pediatría).

Reordenamiento dinámico de la lista de médicos priorizando la especialidad elegida sin ocultar a los demás profesionales.

Tarjeta descriptiva para cada médico que muestra avatar dinámico, nombre, subespecialidad y calificación/rating.

RF-03: Consulta de Perfil del Médico

Pantalla detallada del profesional seleccionado que muestra avatar ampliado, especialidad, años de experiencia, calificación, total de reseñas y descripción profesional.

Botón inferior de acción principal para iniciar el proceso de reserva ("Agendar cita").

RF-04: Agendamiento de Citas

Selección de fecha entre opciones predefinidas mediante tarjetas interactivas (Jueves 26, Viernes 27, Sábado 28).

Selección de horario disponible mediante botones (9:00 am, 10:30 am, 3:00 pm).

Selección única obligatoria (comportamiento tipo RadioButton) tanto para la fecha como para la hora.

Inserción automática de la nueva cita confirmada en la parte superior del historial (índice 0).

RF-05: Pantalla de Confirmación

Confirmación visual con ícono animado de éxito (Check dibujado en vector customizado).

Resumen de la reserva indicando el nombre del médico, fecha y hora elegidas.

Botón directo para redirigir a la pantalla de "Mis citas".

RF-06: Gestión de Citas ("Mis Citas")

Visualización diferida de citas con distintivos visuales de estado:

Confirmada: Borde lateral izquierdo en color morado principal y badge verde de éxito.

Completada: Borde recto estándar y badge gris.

RF-07: Historial Médico

Consulta de registros médicos previos con título del procedimiento, fecha de atención y resumen/detalle del diagnóstico.

RF-08: Perfil del Paciente

Resumen de datos personales del paciente (Correo, Teléfono, DNI).

Contador dinámico que calcula en tiempo real el total de citas en estado "Completada".

2. Requisitos No Funcionales y Técnicos
RNF-01: Arquitectura Limpia y Simplificada

Desarrollo 100% en Jetpack Compose (declarativo purista, sin uso de vistas XML).

Sin patrones ViewModel/MVVM: Gestión directa del estado UI mediante el uso exclusivo de remember, rememberSaveable, mutableStateOf y mutableStateListOf.

RNF-02: Manejo de Navegación

Sistema de rutas estructurado con NavHost y rememberNavController.

Paso de parámetros entre pantallas mediante variables de ruta (medicoId, fecha, hora) codificadas de forma segura con Uri.encode.

Control del historial mediante popBackStack para evitar ciclos de navegación innecesarios.

RNF-03: Restricciones Estrictas de Componentes (Diseño Atomizado)

Prohibición del uso de componentes de alto nivel preconstruidos (TopAppBar, Card, FilterChip, AssistChip, NavigationDrawerItem).

Construcción de la interfaz desde cero empleando componentes fundamentales: Row, Column, Box, Text, Canvas y modificadores avanzados (clip, border, background).

RNF-04: Renderizado de Gráficos Vectoriales a Mano (Canvas)

Dibujado customizado mediante la API Canvas de Compose para:

Icono de suma (+) mediante dos rectángulos perpendiculares.

Icono de confirmación (Check) mediante trazados de líneas dinámicas con Path y Stroke.

RNF-05: Sistema de Diseño y Tema

Implementación basada en Material Design 3 con paleta de colores personalizada fija en modo claro (dynamicColor = false).

Consistencia total en el diseño con fondo blanco puro (#FFFFFF) y esquinas redondeadas uniformes para los componentes (RoundedCornerShape).

Integración con la barra de estado del dispositivo (isAppearanceLightStatusBars) para adaptar dinámicamente los íconos de la barra del sistema según el fondo del encabezado.


<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/ce0f24f3-81e0-4da0-83fa-4a67ff99b5ab" />

<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/23ebf053-6ef0-4efb-87a9-0a590db873d2" />

<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/a7f63788-bfe7-4b79-be35-1832721237bf" />

<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/9869cc7d-1796-4b71-9a1d-2d91abb253d0" />

<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/3d1a10ed-9bfa-4407-94d8-6a9461e8f35d" />

<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/34605e51-863c-4881-935b-d8993e76bfbe" />

<img width="880" height="550" alt="image" src="https://github.com/user-attachments/assets/9e1633da-a7f9-4c07-a485-1a0137ab7af7" />
