
# Clínica SaludPlus — App Paciente

Aplicación móvil Android para el registro de pacientes y la gestión de citas médicas. Desarrollada con Kotlin, Jetpack Compose y Material 3.

## Autor

**Carlos Junco**

Repositorio: [Moviles_Android_D](https://github.com/carlosjunco2025/Moviles_Android_D)

## Descripción del proyecto

Clínica SaludPlus es una aplicación móvil que permite a los pacientes registrarse, iniciar sesión, consultar especialidades médicas, visualizar médicos disponibles y gestionar sus citas.

También incluye secciones para consultar el historial de citas, revisar el perfil del paciente, visualizar resultados de exámenes y acceder a las notificaciones.

Los datos se almacenan temporalmente en memoria mediante el objeto `Repositorio`. Actualmente, no se utiliza una base de datos, por lo que la información se pierde al cerrar la aplicación.

## Objetivos

- Desarrollar una aplicación móvil para la gestión de citas médicas.
- Implementar el registro de pacientes y el inicio de sesión.
- Diseñar interfaces intuitivas y fáciles de utilizar.
- Implementar la navegación entre pantallas.
- Organizar el código mediante componentes reutilizables.
- Utilizar Git y GitHub para el control de versiones.

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
5. Pantalla principal con accesos rápidos.
6. Consulta de especialidades médicas.
7. Listado de médicos por especialidad.
8. Selección de fecha y horario.
9. Confirmación y resumen de la cita.
10. Historial de citas médicas.
11. Visualización del detalle de una cita.
12. Perfil del paciente y cierre de sesión.
13. Consulta de resultados de exámenes.
14. Centro de notificaciones.

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
│       │   └── repository/
│       ├── navigation/
│       └── ui/
│           ├── components/
│           ├── screens/
│           │   ├── auth/
│           │   ├── home/
│           │   ├── agendamiento/
│           │   ├── citas/
│           │   ├── perfil/
│           │   ├── resultados/
│           │   └── notificaciones/
│           └── theme/
├── docs/
│   └── capturas/
└── README.md
```

## Capturas de pantalla

Las siguientes capturas muestran las principales pantallas de Clínica SaludPlus. Se presentan en filas de tres imágenes para facilitar su visualización.

<table>
  <tr>
    <td align="center" width="33%">
      <strong>1. Pantalla de bienvenida</strong><br>
      <img src="docs/capturas/01_pantalla_splash.jpeg" width="220" alt="Pantalla de bienvenida">
    </td>
    <td align="center" width="33%">
      <strong>2. Registro de paciente</strong><br>
      <img src="docs/capturas/02_registro_paciente.jpeg" width="220" alt="Registro de paciente">
    </td>
    <td align="center" width="33%">
      <strong>3. Términos y condiciones</strong><br>
      <img src="docs/capturas/03_terminos_condiciones.jpeg" width="220" alt="Términos y condiciones">
    </td>
  </tr>
  <tr>
    <td align="center">
      <strong>4. Inicio de sesión</strong><br>
      <img src="docs/capturas/04_inicio_sesion.jpeg" width="220" alt="Inicio de sesión">
    </td>
    <td align="center">
      <strong>5. Pantalla principal</strong><br>
      <img src="docs/capturas/05_pantalla_principal.jpeg" width="220" alt="Pantalla principal">
    </td>
    <td align="center">
      <strong>6. Especialidades médicas</strong><br>
      <img src="docs/capturas/06_especialidades_medicas.jpeg" width="220" alt="Especialidades médicas">
    </td>
  </tr>
  <tr>
    <td align="center">
      <strong>7. Listado de médicos</strong><br>
      <img src="docs/capturas/07_listado_medicos.jpeg" width="220" alt="Listado de médicos">
    </td>
    <td align="center">
      <strong>8. Selección de fecha y hora</strong><br>
      <img src="docs/capturas/08_seleccion_fecha_hora.jpeg" width="220" alt="Selección de fecha y hora">
    </td>
    <td align="center">
      <strong>9. Confirmación de cita</strong><br>
      <img src="docs/capturas/09_confirmacion_cita.jpeg" width="220" alt="Confirmación de cita">
    </td>
  </tr>
  <tr>
    <td align="center">
      <strong>10. Cita agendada</strong><br>
      <img src="docs/capturas/10_cita_agendada.jpeg" width="220" alt="Cita agendada">
    </td>
    <td align="center">
      <strong>11. Historial de citas</strong><br>
      <img src="docs/capturas/11_historial_citas.jpeg" width="220" alt="Historial de citas">
    </td>
    <td align="center">
      <strong>12. Historial vacío</strong><br>
      <img src="docs/capturas/12_historial_citas_vacio.jpeg" width="220" alt="Historial de citas vacío">
    </td>
  </tr>
  <tr>
    <td align="center">
      <strong>13. Detalle de cita</strong><br>
      <img src="docs/capturas/13_detalle_cita.jpeg" width="220" alt="Detalle de cita">
    </td>
    <td align="center">
      <strong>14. Perfil del paciente</strong><br>
      <img src="docs/capturas/14_perfil_paciente.jpeg" width="220" alt="Perfil del paciente">
    </td>
    <td align="center">
      <strong>15. Resultados de exámenes</strong><br>
      <img src="docs/capturas/15_resultados_examenes.jpeg" width="220" alt="Resultados de exámenes">
    </td>
  </tr>
  <tr>
    <td align="center" width="33%">
      <strong>16. Notificaciones</strong><br>
      <img src="docs/capturas/16_notificaciones.jpeg" width="220" alt="Notificaciones">
    </td>
    <td></td>
    <td></td>
  </tr>
</table>

## Flujo de navegación

```text
Splash
 ├── Registro
 │    └── Términos y condiciones
 └── Inicio de sesión
      └── Inicio
           ├── Especialidades
           │    └── Médicos
           │         └── Fecha y hora
           │              └── Confirmación
           │                   └── Cita agendada
           ├── Mis citas
           │    └── Detalle de cita
           ├── Resultados de exámenes
           ├── Perfil
           │    └── Cerrar sesión
           └── Notificaciones
```

## Consideraciones técnicas

- La aplicación está desarrollada para dispositivos Android.
- Las interfaces utilizan Jetpack Compose y Material 3.
- La navegación entre pantallas se gestiona mediante Navigation Compose.
- Los datos se almacenan temporalmente en memoria.
- La información no persiste al cerrar la aplicación.
- La carga de imágenes externas puede requerir conexión a internet.
- Los datos de ejemplo son demostrativos y no representan información real de una clínica.

## Conclusión

El desarrollo de Clínica SaludPlus permite aplicar conocimientos de programación móvil, diseño de interfaces y navegación entre pantallas. La aplicación reúne funcionalidades básicas para el registro de pacientes y la gestión de citas médicas, y constituye una base para futuras mejoras, como la integración de una base de datos y la autenticación persistente.

## Información académica

**Autor:** Carlos Junco  
**Curso:** Programación en Móviles  
**Proyecto:** Clínica SaludPlus — App Paciente  
**Repositorio:** [GitHub - Moviles_Android_D](https://github.com/carlosjunco2025/Moviles_Android_D)
