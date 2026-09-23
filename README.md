# Clínica Salud+ - Sistema de Reserva de Citas Médicas 


##  Requisitos Funcionales Implementados

1. **Catálogo de Médicos e Integración de Filtros (`HomeScreen`)**:
   - Catálogo dinámico filtrable por especialidades (Cardiología, Pediatría, Dermatología) mediante `LazyRow` y `FilterChip`.
   - Listado reactivo de profesionales con tarjetas informativas (`Card`), puntuaciones (`Rating`) y número de reseñas utilizando `LazyColumn`.

2. **Perfil Detallado del Profesional (`DoctorProfileScreen`)**:
   - Visualización completa de información médica: biografía, años de experiencia clínica y calificación promedio.
   - Navegación hacia el flujo secuencial de agendamiento mediante botones de acción primaria (`Button`).

3. **Módulo de Agendamiento de Citas (`BookAppointmentScreen`)**:
   - Selección interactiva e intuitiva de fechas y horarios disponibles mediante un estado local con `remember` y `mutableStateOf`.
   - Transferencia segura de parámetros de cita a través de rutas parametrizadas de navegación.

4. **Navegación e Interfaz de Usuario (`AppNavigation` & Drawer)**:
   - Menú lateral deslizable (`ModalNavigationDrawer`) accesible desde las pantallas principales con opciones a Inicio, Mis Citas, Historial y Perfil.
   - Pantallas de confirmación (`ConfirmationScreen`) y visualización de reservas programadas (`AppointmentsScreen`).

---

##  Requisitos Técnicos y Arquitectura

- **Lenguaje**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Navegación**: Navigation Compose (`NavHost`, `composable`, `navArgument`)
- **Navegación Secundaria**: `ModalNavigationDrawer`, `ModalDrawerSheet`
- **Estructura del Proyecto**:
  - `data/`: Modelos de datos (`Doctor`, `Appointment`) y fuente estática (`MockData`).
  - `ui/`: Componentes reutilizables, menú lateral y vistas completas.
  - `navigation/`: Configuración global de rutas y paso de argumentos.

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/0d249ac7-ce25-4d09-9d9a-d0cbb9309704" />

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/0af16b08-2b0e-40c1-89df-777787834a0c" />

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/65801001-f2a3-4abe-a758-ffb7299e697a" />

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/0ae1d666-fe2d-4817-be78-5a9fbfc8ec7b" />

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/58a543a5-1c09-44f7-a180-7b045fde1d45" />

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/2d4c7743-2648-4427-b456-b01feac39e3a" />

<img width="1387" height="867" alt="image" src="https://github.com/user-attachments/assets/dbe6cf3a-b646-41a6-b156-94a17f6657b1" />
