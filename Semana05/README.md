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

<img width="256" height="307" alt="image" src="https://github.com/user-attachments/assets/81d1e899-30d6-4368-a49e-bf98c3db8b4f" />

<img width="219" height="316" alt="image" src="https://github.com/user-attachments/assets/34a21050-4721-49a5-a5e7-9e33f85de03f" />

<img width="207" height="385" alt="image" src="https://github.com/user-attachments/assets/44671d6c-27d4-43d8-9fbc-45b614a6611b" />

<img width="166" height="258" alt="image" src="https://github.com/user-attachments/assets/956c71cb-2620-4135-b0d4-50bad4247116" />


