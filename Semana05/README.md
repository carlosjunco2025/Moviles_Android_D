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

<img width="226" height="322" alt="image" src="https://github.com/user-attachments/assets/5bc45701-109c-4534-8451-008d7091f6b6" />
<img width="249" height="314" alt="image" src="https://github.com/user-attachments/assets/84885d28-68c8-484b-adea-07407902c67f" />
<img width="209" height="400" alt="image" src="https://github.com/user-attachments/assets/478f9470-5848-49bd-a1d0-ebd5ed5c89b1" />
<img width="187" height="394" alt="image" src="https://github.com/user-attachments/assets/aeec9fab-9c49-43fe-83b1-b5f44b74ee9d" />




