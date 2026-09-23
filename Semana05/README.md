# Portal Académico - Laboratorio 05 (Desarrollo Convencional / Sin IA)

## 📌 Datos del Estudiante
- **Nombre Completo:** Carlos Fernando Junco Santiago
- **Institución:** Tecsup
- **Curso:** Desarrollo de Aplicaciones Móviles Android
- **Semana:** 05 - Navegación en Jetpack Compose

---

## 📱 Descripción del Proyecto
Este proyecto corresponde al desarrollo base del **Portal Académico** realizado de manera tradicional (sin asistencia de Inteligencia Artificial). Implementa el flujo de navegación completo entre pantallas utilizando **Navigation Compose** y componentes nativos de **Material Design 3**.

---

## 📋 Requerimientos y Funcionalidades Implementadas
1. **Flujo de Autenticación (`LoginScreen`):**
   - Formulario de inicio de sesión con validación básica.
   - Transición hacia el menú principal tras autenticarse.

2. **Menú Principal (`HomeScreen`):**
   - Opciones para acceder al directorio de alumnos y perfil del usuario.
   - Opción para cerrar sesión de forma segura.

3. **Directorio de Alumnos (`ListScreen`):**
   - Uso de `LazyColumn` para desplegar la lista dinámica de estudiantes.
   - Paso de parámetros dinámicos (`itemId`) mediante argumentos de ruta.

4. **Expediente Académico (`DetailScreen`):**
   - Recepción y renderizado del detalle del alumno según el `itemId` seleccionado.

5. **Configuración de Perfil (`ProfileScreen`):**
   - Interfaz con información del usuario y opción de cierre de sesión.

6. **Navegación Robusta (`AppNavigation` & `Screen`):**
   - Estructura centralizada mediante `NavHost` y rutas fuertemente tipadas mediante un `sealed class`.

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose (Material3)
- **Navegación:** Navigation Compose (`androidx.navigation:navigation-compose`)
- **Control de Versiones:** Git & GitHub

<img width="258" height="350" alt="image" src="https://github.com/user-attachments/assets/0e6ce3c9-e011-484d-8352-c1db898d63df" />

<img width="228" height="349" alt="image" src="https://github.com/user-attachments/assets/7bf65ddb-f3b7-46a8-8980-b4de5fe24741" />

<img width="212" height="389" alt="image" src="https://github.com/user-attachments/assets/06845f76-4f3b-4a7c-8d6a-da6b24ef1aff" />

<img width="190" height="249" alt="image" src="https://github.com/user-attachments/assets/a9bfd174-19b8-4a40-96ac-3b5e477f5bff" />


