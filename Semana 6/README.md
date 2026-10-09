# Clínica SaludPlus — App Paciente

Aplicación móvil Android para el registro de pacientes y la gestión de citas médicas. Desarrollada con Kotlin, Jetpack Compose y Material 3.

## Autor

**Carlos Fernando Junco Santiago**

Correo: carlos.junco@tecsup.edu.pe  
Repositorio: [Moviles_Android_D](https://github.com/carlosjunco2025/Moviles_Android_D)

## Descripción del proyecto

Clínica SaludPlus es una aplicación móvil que permite a los pacientes registrarse, iniciar sesión, consultar especialidades médicas, visualizar médicos disponibles, seleccionar la sede de atención y gestionar sus citas.

También incluye secciones para consultar las citas del paciente organizadas por sedes, gestionar médicos favoritos en "Mis doctores", revisar el perfil del paciente, visualizar resultados de exámenes y acceder a las notificaciones.

Los datos se almacenan temporalmente en memoria mediante el objeto `Repositorio`. Actualmente, no se utiliza una base de datos, por lo que la información se pierde al cerrar la aplicación.

## Objetivos

- Desarrollar una aplicación móvil para la gestión de citas médicas.
- Implementar el registro de pacientes y el inicio de sesión.
- Diseñar interfaces intuitivas y fáciles de utilizar con Jetpack Compose y Material 3.
- Implementar la navegación por sedes para el agendamiento y visualización de citas.
- Organizar la gestión de médicos favoritos en "Mis doctores" por especialidad.
- Organizar el código mediante componentes reutilizables y arquitectura limpia en Kotlin.

## Tecnologías utilizadas

- **Lenguaje:** Kotlin
- **Entorno de desarrollo:** Android Studio
- **Interfaces:** Jetpack Compose
- **Diseño visual:** Material 3
- **Navegación:** Navigation Compose
- **Carga de imágenes:** Coil
- **Control de versiones:** Git y GitHub

## Funcionalidades principales

1. Pantalla de bienvenida (Splash).
2. Registro de pacientes y validación de campos.
3. Inicio de sesión.
4. Visualización de términos y condiciones.
5. Pantalla principal con accesos rápidos (Agendar cita, Sedes, Mis datos, Resultados y Mis doctores).
6. Selección de sede de atención ("Elegir sede").
7. Consulta de especialidades por sede y búsqueda general de especialidades.
8. Listado de médicos por sede y especialidad.
9. Selección de fecha y horario con calendario dinámico.
10. Confirmación y resumen de la cita mostrando sede y dirección.
11. Gestión de citas del paciente agrupadas por sede (`SedesScreen`).
12. Visualización del detalle de una cita con dirección de sede y opción de reprogramación/cancelación.
13. Módulo "Mis doctores" con médicos favoritos agrupados por especialidad.
14. Perfil del paciente y cierre de sesión.
15. Consulta de resultados de exámenes.
16. Centro de notificaciones y sección de ayuda/preguntas frecuentes.

## Instalación y ejecución

### Requisitos

- Android Studio.
- JDK compatible con la configuración del proyecto.
- Emulador o dispositivo Android.
- Conexión a internet para descargar las dependencias necesarias.

### Pasos

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/carlosjunco2025/Moviles_Android_D.git
   ```

2. Ingresar a la carpeta del repositorio:

   ```bash
   cd Moviles_Android_D
   ```

3. Abrir la carpeta `Semana 6` desde Android Studio.
4. Esperar a que finalice la sincronización de Gradle.
5. Seleccionar un emulador o dispositivo Android.
6. Ejecutar la aplicación.

## Estructura del proyecto

```text
Semana 6/
├── app/
│   └── src/main/java/com/saludplus/citas/
│       ├── data/
│       │   ├── model/
│       │   │   ├── Cita.kt
│       │   │   ├── Especialidad.kt
│       │   │   ├── Medico.kt
│       │   │   ├── Resultado.kt
│       │   │   ├── Sede.kt
│       │   │   └── Usuario.kt
│       │   └── repository/
│       │       └── Repositorio.kt
│       ├── navigation/
│       │   ├── AppNavigation.kt
│       │   └── Rutas.kt
│       ├── ui/
│       │   ├── components/
│       │   │   ├── AvatarMedico.kt
│       │   │   ├── BarraNavegacion.kt
│       │   │   ├── BarraSuperior.kt
│       │   │   ├── BotonAzul.kt
│       │   │   ├── CampoTextoIcono.kt
│       │   │   ├── EnlaceTexto.kt
│       │   │   ├── EstiloEspecialidad.kt
│       │   │   ├── FilaDetalle.kt
│       │   │   ├── SelectorFechaHora.kt
│       │   │   ├── TarjetaMedico.kt
│       │   │   └── TarjetaSuave.kt
│       │   ├── screens/
│       │   │   ├── agendamiento/
│       │   │   │   ├── CitaExitosaScreen.kt
│       │   │   │   ├── ConfirmarCitaScreen.kt
│       │   │   │   ├── ElegirSedeScreen.kt
│       │   │   │   ├── EspecialidadesSedeScreen.kt
│       │   │   │   ├── EspecialidadesScreen.kt
│       │   │   │   ├── FechaHoraScreen.kt
│       │   │   │   └── MedicosScreen.kt
│       │   │   ├── auth/
│       │   │   ├── citas/
│       │   │   │   ├── DetalleCitaScreen.kt
│       │   │   │   ├── MisCitasScreen.kt
│       │   │   │   └── ReprogramarCitaScreen.kt
│       │   │   ├── doctores/
│       │   │   │   └── MisDoctoresScreen.kt
│       │   │   ├── home/
│       │   │   ├── notificaciones/
│       │   │   ├── perfil/
│       │   │   ├── resultados/
│       │   │   └── sedes/
│       │   │       └── SedesScreen.kt
│       │   └── theme/
│       └── util/
│           └── Fechas.kt
├── README.md
└── PROMPTS.md
```

## Flujo de navegación

```text
Splash
 ├── Registro
 │    └── Términos y condiciones
 └── Inicio de sesión
      └── Inicio
           ├── Agendar Cita
           │    └── Elegir Sede
           │         └── Especialidades de Sede
           │              └── Médicos de Sede
           │                   └── Fecha y Hora
           │                        └── Confirmación
           │                             └── Cita Agendada
           ├── Sedes (Citas por Sede)
           │    └── Detalle de Cita
           ├── Mis Doctores (Favoritos por Especialidad)
           ├── Resultados de exámenes
           ├── Perfil
           │    ├── Ayuda y Preguntas Frecuentes
           │    └── Cerrar sesión
           └── Notificaciones
```

## Consideraciones técnicas

- La aplicación está desarrollada para dispositivos Android (minSdk 26).
- Las interfaces utilizan Jetpack Compose y Material 3.
- La navegación entre pantallas se gestiona mediante Navigation Compose.
- Los datos se almacenan temporalmente en memoria dentro del objeto `Repositorio`.
- La información de sedes y direcciones son datos ficticios de ejemplo demostrativos.
- La información no persiste en disco ni base de datos al cerrar la aplicación.

## Fase 2 (con-ia)

> **Nota de procedencia:** La Fase 2 se desarrolló con asistencia de IA en Android Studio; los commits de la Fase 1 conservan sus autores originales.

En la Fase 2 se implementaron mejoras clave utilizando IA para elevar la funcionalidad y experiencia de usuario de la aplicación.

### Mejoras implementadas

1. **Sistema de Sedes y Flujo de Agendamiento por Sede:**
   - Creación del modelo `Sede` y asignación de `sedeId` a cada médico en el `Repositorio` (Sede Los Olivos, Sede San Isidro y Sede Santiago de Surco, datos ficticios de ejemplo).
   - Flujo "Agendar cita": Inicio > Elegir sede (`ElegirSedeScreen`) > Especialidades de la sede (`EspecialidadesSedeScreen`) > Médicos de la sede (`MedicosScreen`) > Fecha y hora > Confirmar cita.
   - Presentación de la sede y dirección correspondiente en Confirmación y Detalle de cita.
2. **Reemplazo de "Mis citas" por "Sedes":**
   - Nueva pantalla `SedesScreen` que organiza las citas del usuario en sesión agrupadas por sede, indicando el número de citas por sede y estados vacíos apropiados.
   - Actualización de la barra inferior (destinos: Inicio, Sedes, Resultados, Perfil).
3. **Módulo "Mis doctores" (Médicos Favoritos):**
   - Componente `TarjetaMedico` con botón de corazón interactivo, animación de escala, aviso snackbar y soporte para mostrar sede.
   - Nueva pantalla `MisDoctoresScreen` accesible desde la tarjeta de ancho completo en Inicio, presentando los doctores favoritos agrupados por especialidad en orden alfabético y por calificación descendente, con acción Deshacer al quitar un favorito.
4. **Soporte de API y Utilidades de Fechas:**
   - Se actualizó el `minSdk` a 26 en `build.gradle.kts`.
   - Se crearon utilidades de fecha (`util/Fechas.kt`) basadas en `java.time.LocalDate` para cálculo de días hábiles, nombres de días, meses y formateo en español.
5. **Calendario Dinámico:**
   - Integración de un selector interactivo de fechas en la pantalla de Fecha y hora con navegación por semanas hábiles e indicador "Hoy".
6. **Prevención de Citas Solapadas y Reprogramación:**
   - Validación de horarios del paciente para evitar reservas duplicadas y soporte para reprogramar citas liberando el turno anterior.

### Limitaciones conocidas

- Todos los datos (citas, sedes y médicos favoritos) viven temporalmente en el objeto `Repositorio` en memoria y no persisten al cerrar o reiniciar la aplicación.

## Información académica

**Autor:** Carlos Fernando Junco Santiago  
**Correo:** carlos.junco@tecsup.edu.pe  
**Curso:** Programación en Móviles  
**Proyecto:** Clínica SaludPlus — App Paciente  
**Repositorio:** [GitHub - Moviles_Android_D](https://github.com/carlosjunco2025/Moviles_Android_D)
