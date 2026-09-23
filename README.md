# TECSUP Fit - Gestión de Clases y Reservas de Gimnasio 


##  Requisitos Funcionales del Sistema

1. **Catálogo Dinámico de Clases (`HomeScreen`)**:
   - Visualización del catálogo completo de clases disponibles (Cardio, Fuerza, Flexibilidad).
   - Filtrado dinámico en tiempo real mediante componentes `FilterChip`.
   - Renderizado eficiente de tarjetas personalizadas (`ClassItemCard`) utilizando `LazyColumn` y `LazyRow`.

2. **Detalle de Clase y Selección (`DetailScreen`)**:
   - Muestra completa de información técnica: título, instructor, duración y descripción extendida.
   - Selección dinámica de horarios de entrenamiento mediante botones de radio (`RadioButton`).
   - Validación y botón de acción para procesar la reserva.

3. **Confirmación y Retroalimentación (`ConfirmationScreen`)**:
   - Resumen interactivo de la reserva realizada con el título de la clase y el horario elegido.
   - Interfaz visual con retroalimentación clara mediante iconos de estado de Material3.
   - Navegación de retorno seguro al catálogo principal restableciendo la pila de pantallas.

4. **Navegación e Interfaz General**:
   - Implementación de `Navigation Compose` para el flujo entre pantallas con paso seguro de argumentos (`classId`, `classTitle`, `schedule`).
   - Menú de navegación inferior (`NavigationBar`) persistente en el flujo principal.

---

## 🛠️ Requisitos Técnicos y Stack de Tecnologías

- **Lenguaje de Programación**: Kotlin
- **Kit de UI**: Jetpack Compose (Declarativo)
- **Sistema de Diseño**: Material Design 3 (Material3)
- **Navegación**: Navigation Compose (`NavHost`, `composable`, `navArgument`)
- **Arquitectura de Software**: Estructura limpia por capas (`data`, `ui`, `navigation`)
- **Control de Versiones**: Git & GitHub

<img width="237" height="506" alt="image" src="https://github.com/user-attachments/assets/de7ca785-adf2-4d31-a3cf-4131c021963e" />

<img width="235" height="557" alt="image" src="https://github.com/user-attachments/assets/a3e1a0d8-45d8-47ec-a4ee-73cbfa861a0d" />

<img width="285" height="551" alt="image" src="https://github.com/user-attachments/assets/228797b3-0bf7-4b31-b8b9-8bb67c4840a3" />

<img width="305" height="550" alt="image" src="https://github.com/user-attachments/assets/d34047a8-b3eb-4583-9d02-0fa61a20c764" />

<img width="243" height="481" alt="image" src="https://github.com/user-attachments/assets/75345a68-3a62-42eb-842c-e01a305b733e" />


