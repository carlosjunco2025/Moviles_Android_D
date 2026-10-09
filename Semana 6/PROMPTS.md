# Bitácora de Prompts y Desarrollo — Clínica SaludPlus (Fase 2)

## Prompt 0 (Prompt Inicial)

```text
# 1. ROL / PERSONA
Eres el responsable técnico del desarrollo Android de este proyecto (Kotlin, Jetpack Compose,
Material 3). Tus responsabilidades:
- Entregar código que compile, sea legible y se integre con la estructura existente.
- Leer el código real del proyecto antes de modificarlo; nunca inventar nombres, firmas ni campos.
- Respetar las reglas de la sección 6 y advertir cuando una tarea las ponga en riesgo.
- Fundamentar en una o dos frases las decisiones no evidentes: el repositorio es material de
  referencia para estudiantes.
- No afirmar que algo funciona si no lo compilaste o probaste; registrar supuestos y riesgos.
- Trabajar de forma autónoma: sin preguntas durante la ejecución, salvo las condiciones de parada
  de la sección 4.
Comunicación: español neutro, formal y conciso.

# 2. CONTEXTO
App "Clínica SaludPlus — App Paciente", de agendamiento de citas médicas, ya terminada en su Fase 1
(15 pantallas con Jetpack Compose). Los datos viven en memoria en el objeto `Repositorio` y se
pierden al cerrar la app; es intencional, porque el curso aún no ve Room ni MVVM.

Esta es la Fase 2 ("mejora con IA") del Laboratorio 6, Semana 6, del curso Programación en Móviles.
El enunciado exige una única mejora obligatoria: calendario dinámico con java.time.LocalDate en la
pantalla Fecha y hora, y la fecha en texto en español en Confirmar cita. Exige además mínimo 3
commits en la rama y un archivo PROMPTS.md. Las demás mejoras de este prompt son valor agregado y
no pueden poner en riesgo la obligatoria.

- Repositorio de trabajo: https://github.com/carlosjunco2025/Moviles_Android_D.git
- Carpeta del proyecto dentro del repositorio: `Semana 6`
- Rama base: `sin-ia` (Fase 1, con su historial completo). Rama de trabajo: `con-ia`.

# 3. OBJETIVO / TAREA
Paso 0: crear la rama `con-ia` con el historial COMPLETO de `sin-ia` (sección 4). Después, un commit
por cambio, compilado y subido, en este orden:

Mejora obligatoria del laboratorio:
1. Subir minSdk a 26 (solo si el actual es menor).
2. Crear funciones de fechas con LocalDate (util/Fechas.kt).
3. Calendario dinámico en Fecha y hora.
4. Fecha en texto en español en Confirmar cita.

Mejoras adicionales:
5. Médicos favoritos.
6. Búsqueda de médicos por nombre desde Especialidades.
7. Evitar citas solapadas del mismo paciente.
8. Reprogramar una cita.
9. Ayuda y preguntas frecuentes.
10. README y PROMPTS.md.

# 4. INSTRUCCIONES

## Paso 0: copiar el historial de `sin-ia` a `con-ia`
1. `git remote -v`: `origin` debe apuntar a carlosjunco2025/Moviles_Android_D. Si no, detente.
2. `git fetch origin`, `git checkout sin-ia`, `git pull origin sin-ia`.
3. Comprueba que `Semana 6/` existe y contiene el proyecto Gradle (busca `settings.gradle.kts`).
   Si falta, detente e informa; no lo recrees de memoria.
4. `git checkout -b con-ia`. Al nacer de `sin-ia`, hereda todo el historial. Si `con-ia` ya existe,
   haz checkout y confirma con `git merge-base --is-ancestor sin-ia con-ia` que contiene a `sin-ia`;
   si no, detente.
5. Verifica que `git rev-parse sin-ia` y `git rev-parse con-ia` dan el mismo hash, y que
   `git rev-list --count` coincide en ambas. Muestra `git log --format="%h %an %s" -5`.
6. `git push -u origin con-ia`.
Prohibido en este paso: cherry-pick, rebase, filter-repo, copiar archivos a mano o cualquier cosa
que cambie hashes, autores, fechas o mensajes de los commits existentes.

## Autoría de los commits nuevos (restricción obligatoria)
- TODOS los commits nuevos de `con-ia` deben tener como autor Y como committer a "Carlos Junco", con
  el correo de la cuenta carlosjunco2025. Ningún commit nuevo puede quedar a nombre de otra persona
  o cuenta; en particular, ninguno a nombre de DMillonnesZ ni de Daniel Alejandro Millones.
- Antes del primer commit nuevo:
  1. Ejecuta `git config user.name` y `git config user.email` (nivel repositorio, sin --global) y
     repórtalos.
  2. `user.name` debe ser exactamente "Carlos Junco". Si es otro, establécelo SOLO a nivel de
     repositorio: `git config user.name "Carlos Junco"`.
  3. `user.email` debe estar configurado y no vacío. Obtén los correos de los autores heredados con
     `git log sin-ia --format="%an|%ae" | sort -u` y comprueba que el correo configurado NO es el de
     DMillonnesZ ni el de Daniel Alejandro Millones. No inventes ni deduzcas un correo: si falta o
     coincide con el de la otra cuenta, detente y pídelo. Es la ÚNICA pregunta permitida.
- Después de CADA commit y antes del push, ejecuta
  `git log -1 --format="%an|%ae|%cn|%ce"`. Autor y committer deben ser "Carlos Junco" con el correo
  configurado. Si no lo son, corrige ese commit (todavía no subido) con
  `git commit --amend --reset-author --no-edit` y verifica otra vez. Nunca hagas push de un commit
  con otra autoría.
- Prohibido: `--author`, las variables GIT_AUTHOR_NAME, GIT_AUTHOR_EMAIL, GIT_COMMITTER_NAME y
  GIT_COMMITTER_EMAIL, `git config --global`, cambiar o buscar credenciales, y añadir trailers
  `Co-authored-by` o `Signed-off-by` a los mensajes.
- No modifiques la autoría de los commits heredados de `sin-ia`.
- El push se realiza con la cuenta carlosjunco2025. Si falla con 401 o 403, detente e informa; no
  pruebes otras credenciales ni cambies de cuenta.

## Reglas de Git (en cada commit)
- Antes de cada commit, compila con `./gradlew assembleDebug` desde la carpeta del proyecto Gradle.
  Si falla, corrige; no hagas commit de código que no compila.
- Un commit = un cambio coherente. Mensaje en español, sin prefijos como feat o chore, asunto de
  máximo 72 caracteres y descripción de un párrafo en el segundo `-m`.
- Haz push inmediatamente después de cada commit (y de la comprobación de autoría):
  `git push origin con-ia`. Sin `--force`, sin `--amend` sobre commits ya subidos y sin reescribir
  historial publicado.
- Si el push se rechaza por divergencia, usa `git pull --rebase origin con-ia` y reintenta una vez.
- Si no puedes ejecutar comandos, entrega los comandos exactos y en orden, incluida la comprobación
  de autoría.

## Condiciones de parada (únicas excepciones a la autonomía)
Falta la carpeta `Semana 6/`; `origin` no es el repositorio esperado; el correo configurado falta o
coincide con el de la otra cuenta; falla la autenticación; o una compilación no se resuelve tras tres
intentos razonables (informa el error completo y qué probaste). Cualquier otra ambigüedad: decide,
aplica y regístrala en "Supuestos".

## Mejora obligatoria (commits 1 a 4)
- Lee `app/build.gradle.kts`: si `minSdk` es menor a 26, súbelo a 26 en el commit 1 (LocalDate lo
  requiere). Si ya es 26 o más, omite ese commit.
- `util/Fechas.kt` (java.time): esDiaHabil, primerDiaHabil, diasHabiles(desde, cantidad = 5),
  semanaDeCalendario(hoy, indiceSemana), nombreDiaCorto, mesYAnio ("Octubre 2026") y fechaEnTexto
  ("Martes 6 de octubre 2026"; el mes se escribe "setiembre").
- Fecha y hora: los próximos 5 días hábiles desde hoy (LocalDate.now()), sin sábados, domingos ni
  días pasados. Las flechas < y > mueven exactamente 7 días; "<" queda deshabilitada en la semana
  actual. El mes y año salen del primer día visible. Al cambiar de día se recalculan los horarios y
  se reinicia la hora; al cambiar de semana se reinician día y hora. Continuar solo se habilita con
  día y hora elegidos. El bloqueo de horarios reservados por médico y fecha no se rompe. La fecha
  viaja y se guarda en ISO (`fecha.toString()`, "2026-10-06"); solo se convierte a texto al mostrarla.
- Animación sencilla: el cambio de semana usa AnimatedContent (desliza según la dirección) y el día
  actual lleva la marca "Hoy".
- Confirmar cita muestra la fecha con fechaEnTexto.

## Mejoras adicionales (commits 5 a 9)

### Commit 5: Médicos favoritos
- Funciones nuevas en el Repositorio: esFavorito(medicoId), alternarFavorito(medicoId) y
  medicosFavoritos(). Los favoritos son por usuario (clave: correo), viven en memoria y deben ser
  observables por Compose (por ejemplo, SnapshotStateMap o mutableStateListOf) para que la interfaz
  se actualice al instante.
- Médicos: ícono de corazón en cada card (contentDescription "Marcar como favorito" y "Quitar de
  favoritos", área táctil de 48 dp) con una animación breve de escala; chip "Solo favoritos" que
  filtra la lista, con mensaje de lista vacía propio.
- Inicio: sección "Mis médicos favoritos" con un LazyRow de tarjetas pequeñas (foto, nombre,
  especialidad); solo aparece si hay favoritos, y al tocar una abre Fecha y hora de ese médico.
- HomeScreen y MedicosScreen reciben cualquier parámetro nuevo con valor por defecto.

### Commit 6: Búsqueda de médicos por nombre desde Especialidades
- Función nueva `Repositorio.buscarMedicosPorNombre(texto)`: filter + contains sin distinguir
  mayúsculas, mínimo 2 caracteres, ordenada por calificación descendente.
- Especialidades: cuando el texto buscado tiene 2 o más caracteres, debajo de las especialidades
  coincidentes aparece la sección "Médicos" con los resultados (foto, nombre, especialidad,
  calificación). Al tocar un médico se abre Fecha y hora con su id. La búsqueda actual de
  especialidades no cambia. Si no hay coincidencias de ningún tipo, muestra el mensaje vacío.
- EspecialidadesScreen recibe un parámetro nuevo (onMedico) con valor por defecto.

### Commit 7: Evitar citas solapadas del mismo paciente
- Función nueva `Repositorio.tieneCitaEn(fecha, hora, excluirCitaId: Int? = null)`: verdadero si el
  usuario en sesión ya tiene una cita en esa fecha y hora (con cualquier médico), ignorando la cita
  indicada en `excluirCitaId`.
- Confirmar cita: antes de guardar, si tieneCitaEn es verdadero, no agenda y muestra el mensaje
  "Ya tienes una cita el [fecha en texto] a las [hora]. Elige otro horario." La firma de
  `agendarCita` no cambia, y el bloqueo por médico, fecha y hora sigue funcionando.

### Commit 8: Reprogramar una cita
- Función nueva `Repositorio.reprogramarCita(citaId, fecha, hora): Boolean`: solo si la cita
  pertenece al usuario en sesión, ese médico está libre en la nueva fecha y hora, y no hay
  solapamiento del paciente (usa tieneCitaEn excluyendo esa cita). Actualiza la cita conservando su
  id y deja libre el horario anterior.
- Para no duplicar código, extrae el selector de calendario y horarios de FechaHoraScreen a un
  componente compartido (por ejemplo `ui/components/SelectorFechaHora.kt`) y haz que FechaHoraScreen
  lo use SIN cambiar su firma ni su comportamiento. Tras la extracción, vuelve a verificar toda la
  mejora obligatoria.
- Pantalla nueva `ReprogramarCitaScreen(citaId, onAtras, onReprogramada)` en
  ui/screens/citas, con ruta nueva "reprogramar/{citaId}" y función `Rutas.reprogramar(citaId)`.
  Muestra el médico de la cita y el selector compartido; el botón dice "Confirmar nuevo horario".
- Detalle de cita: botón "Reprogramar cita" (parámetro nuevo con valor por defecto) que abre la
  pantalla. Al confirmar, vuelve al Detalle mostrando la nueva fecha y hora, con un aviso
  "Cita reprogramada". Si el horario ya no está disponible, muestra un mensaje de error.

### Commit 9: Ayuda y preguntas frecuentes
- Pantalla nueva `AyudaScreen(onAtras)` en ui/screens/perfil, con ruta nueva "ayuda".
- Acordeón de 6 preguntas con AnimatedVisibility y una flecha que rota; solo una abierta a la vez
  (estado con rememberSaveable). Preguntas, con respuestas veraces sobre ESTA app y en tono cálido:
  cómo agendar una cita, cómo cancelarla, cómo reprogramarla, cómo marcar médicos favoritos, por qué
  un horario no aparece (ya está reservado, o ya tienes una cita a esa hora) y por qué los datos se
  pierden al cerrar la app (es una versión de práctica que guarda la información solo mientras está
  abierta). No inventes teléfonos, direcciones ni políticas de la clínica.
- Perfil: nueva fila "Ayuda y preguntas frecuentes" (parámetro nuevo onAyuda con valor por defecto)
  que abre la pantalla.

## Commit 10: README y PROMPTS.md
- README (`Semana 6/README.md`): añade una sección "Fase 2 (con-ia)" con las mejoras implementadas,
  las limitaciones nuevas y el historial de commits generado con `git log` de la rama. Conserva el
  autor y los datos que ya tenga. Añade una nota de procedencia: "La Fase 2 se desarrolló con Gemini
  en Android Studio; los commits de la Fase 1 conservan sus autores originales." No afirmes que algo
  se hizo sin asistencia ni lo contrario de lo que ocurrió.
- PROMPTS.md (`Semana 6/PROMPTS.md`): Prompt 0 = el texto de este prompt. Después, por cada commit:
  la tarea aplicada, un resumen de la respuesta y "Correcciones realizadas durante la sesión" con los
  errores reales que corregiste (por ejemplo, fallos de compilación). Añade al final "Correcciones
  manuales del usuario: pendiente de completar". No inventes correcciones.

## Qué evitar
- No hacer preguntas fuera de las condiciones de parada.
- No reescribir pantallas que no toca el commit en curso.
- No dejar funciones, botones ni textos provisionales.
- No agregar librerías nuevas.
- No usar @Preview ni emojis (en código ni en la interfaz).
- No cambiar el flujo de navegación de la Fase 1 salvo para agregar destinos.
- No reintroducir `saveState` ni `restoreState` en la función `irA`.
- No subir ningún commit cuyo autor o committer no sea Carlos Junco.

# 5. DATOS / ENTRADAS
- Repositorio: https://github.com/carlosjunco2025/Moviles_Android_D.git, carpeta `Semana 6`.
- Ramas: base `sin-ia`, trabajo `con-ia` (se crea en el Paso 0).
- Autoría de los commits nuevos: Carlos Junco, con el correo de la cuenta carlosjunco2025 ya
  configurado en el repositorio (no se inventa ni se deduce).
- El resto se lee del código real: minSdk, compileSdk y versiones de Compose; modelos (Usuario,
  Especialidad, Medico, Cita, Resultado); funciones del `Repositorio`; rutas y `AppNavigation`;
  componentes de `ui/components`; firmas de las pantallas.
- Hechos de la Fase 1 que debes conservar: la función `irA` de la barra inferior usa `popUpTo(HOME)`
  y `launchSingleTop` sin saveState/restoreState; Login a Home usa `popUpTo(SPLASH){inclusive}`;
  Confirmar a Cita agendada usa `popUpTo(HOME)`; Perfil a Splash usa `popUpTo(HOME){inclusive}`. La
  fecha de las citas es un String ISO y la hora un String como "10:00". Tema: SaludPlusTheme.
- Hoy: fecha real del dispositivo, LocalDate.now().

# 6. RESTRICCIONES
- AUTORÍA: todo commit nuevo se crea y se sube con autor y committer "Carlos Junco" y el correo
  configurado de esa cuenta. Prohibido cualquier otro autor, `--author`, variables GIT_AUTHOR_* o
  GIT_COMMITTER_*, `git config --global`, trailers Co-authored-by o Signed-off-by, y reescribir la
  autoría de commits existentes.
- PROHIBIDO usar base de datos (Room, SQLite, Firebase) y persistencia en disco (SharedPreferences,
  DataStore). Todo vive en memoria en el object Repositorio.
- No cambies nombres ni parámetros existentes de funciones del Repositorio ni de pantallas. Para
  añadir datos usa parámetros nuevos con valor por defecto, o funciones nuevas.
- Mantén la estructura de paquetes y un archivo por pantalla. Rutas nuevas solo en Rutas.kt y
  AppNavigation.kt, sin borrar las existentes. Respeta el estilo visual actual (colores, tarjetas,
  botones azules) en las pantallas nuevas.
- Tecnologías: Kotlin, Jetpack Compose, Material 3, Navigation Compose. Sin dependencias nuevas.
- LocalDate requiere API 26: protege el código con comprobaciones de versión si hiciera falta.
- Git: trabajar solo en `con-ia`, push tras cada commit, sin `--force` ni `--amend` sobre lo subido,
  sin reescribir historial.
- Idioma: español en interfaz, comentarios, commits y explicaciones; tono cálido y cercano en la
  interfaz, sin tecnicismos.
- Sin @Preview ni emojis. Comentarios breves.
- Cada commit compila por sí solo y deja la app ejecutable.
- La mejora obligatoria (commits 2 a 4) no depende de las mejoras adicionales.

# 7. FORMATO DE SALIDA
Por cada commit, informa de forma breve:
1. Qué archivos modificaste o creaste.
2. Decisiones y supuestos (1 a 3 líneas cada uno).
3. Resultado de la compilación y qué probar manualmente (pasos con resultado esperado, incluidos
   casos límite: sin citas, rotar, botón Atrás).
4. Hash y mensaje del commit, la línea de `git log -1 --format="%an|%ae|%cn|%ce"` que demuestra la
   autoría, y confirmación del push.
5. Una línea de resumen para el PROMPTS.md.
Al terminar, entrega:
- El `git log --format="%h|%an|%ae|%s"` completo de `con-ia`.
- La salida de `git log sin-ia..con-ia --format="%an|%ae|%cn|%ce" | sort -u`, que debe ser UNA sola
  línea con Carlos Junco.
- La lista de supuestos y riesgos.
- Qué mejoras no se pudieron completar y por qué.

# 8. CRITERIOS DE CALIDAD
- `con-ia` contiene todos los commits de `sin-ia` sin alteraciones, y encima los nuevos.
- Todos los commits nuevos tienen autor y committer Carlos Junco, con el mismo correo, y ninguno
  figura a nombre de otra persona.
- Cada commit compila, está subido y su mensaje describe fielmente su cambio.
- Los horarios reservados siguen ocultándose por médico y fecha con el calendario dinámico.
- El calendario cumple la mejora obligatoria por sí solo, también después de extraer el selector
  compartido del commit 8.
- Un paciente no puede tener dos citas a la misma fecha y hora, ni al agendar ni al reprogramar.
- Reprogramar conserva el id de la cita y libera el horario anterior.
- Los favoritos son por usuario, se actualizan al instante y se pierden al cerrar la app.
- No quedan textos provisionales ni botones sin función.
- Sin base de datos ni persistencia en disco; las firmas existentes no cambiaron.
- Código legible para estudiantes: nombres claros, funciones pequeñas, comentarios breves.
- README y PROMPTS.md veraces: reflejan lo que realmente ocurrió.

# 9. EJEMPLO

Mensaje de commit:
- Asunto: Reemplazar los días fijos por un calendario dinámico con LocalDate
- Descripción: Genera los próximos cinco días hábiles desde hoy, permite avanzar y retroceder por
  semanas sin ir antes de la semana actual, actualiza el mes y año mostrados y reinicia la hora al
  cambiar de día, manteniendo el bloqueo de horarios reservados.

Comprobación de autoría tras un commit:
- Comando: git log -1 --format="%an|%ae|%cn|%ce"
- Resultado esperado: Carlos Junco|[correo configurado]|Carlos Junco|[el mismo correo]

Cita solapada:
- Hoy es lunes 5 de octubre de 2026. El paciente ya tiene una cita el martes 6 a las 10:00 con la
  Dra. Ana Torres. Al intentar agendar con el Dr. Luis Ramírez el martes 6 a las 10:00, no se guarda
  y aparece: "Ya tienes una cita el Martes 6 de octubre 2026 a las 10:00. Elige otro horario."

Reprogramar:
- Cita con la Dra. Ana Torres el martes 6 a las 10:00. Al reprogramarla al miércoles 7 a las 11:00,
  la cita conserva su id, el horario del martes 6 a las 10:00 vuelve a aparecer disponible y el
  Detalle muestra el miércoles 7 a las 11:00.

# 10. VALIDACIÓN
Antes de terminar cada commit y al final, comprueba y confirma con "Validación: OK" o indica lo que
no pudiste comprobar:
1. Paso 0: `sin-ia` y `con-ia` tenían el mismo HEAD antes del primer commit nuevo.
2. Autores, fechas y mensajes de los commits heredados no cambiaron.
3. Autoría: `git log sin-ia..con-ia --format="%an|%ae|%cn|%ce" | sort -u` devuelve una sola línea,
   con Carlos Junco como autor y committer y el correo configurado, distinto del de la otra cuenta.
4. No se usó --author, GIT_AUTHOR_*, GIT_COMMITTER_*, `git config --global` ni trailers
   Co-authored-by o Signed-off-by.
5. Compila con `assembleDebug` en cada commit.
6. Imports completos y ninguna referencia a funciones, campos o parámetros inexistentes.
7. Ninguna firma existente cambió salvo por parámetros nuevos con valor por defecto.
8. No quedan @Preview, emojis ni TODO sin resolver.
9. `irA` no usa saveState ni restoreState, y los popUpTo existentes se mantienen.
10. Calendario: las flechas mueven 7 días; "<" deshabilitada en la semana actual; fin de semana y
    cambio de mes y de año producen fechas y títulos correctos; el bloqueo de horarios sigue activo.
11. Tras el commit 8, FechaHoraScreen se comporta igual que antes de la extracción.
12. Cada push fue a `origin con-ia`, sin `--force` ni `--amend` sobre commits subidos.
13. README y PROMPTS.md solo contienen hechos verificables y la nota de procedencia.

Comienza con el Paso 0 y continúa en orden. Si no puedes ejecutar comandos, entrégalos listos.
```

## Registro de Commits y Tareas

### Commit 1 (`70b4b78`)
- **Tarea aplicada:** Actualizar `minSdk` a nivel 26 en `app/build.gradle.kts` para habilitar el uso nativo de `java.time.LocalDate`.
- **Resumen de la respuesta:** Se modificó la configuración de Gradle elevando `minSdk` a 26.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 2 (`d070cdf`)
- **Tarea aplicada:** Crear `util/Fechas.kt` con utilidades de `LocalDate` para manejo de fechas hábiles, formato de semanas y texto en español ("Martes 6 de setiembre 2026").
- **Resumen de la respuesta:** Se implementaron las funciones `esDiaHabil`, `primerDiaHabil`, `diasHabiles`, `semanaDeCalendario`, `nombreDiaCorto`, `mesYAnio` y `fechaEnTexto`.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 3 (`efe602d`)
- **Tarea aplicada:** Implementar calendario dinámico en la selección de fecha y hora (`FechaHoraScreen.kt`).
- **Resumen de la respuesta:** Se actualizó la pantalla para mostrar 5 días hábiles a partir de la fecha actual, navegación entre semanas hábiles, indicador "Hoy", animaciones de transición con `AnimatedContent` y reinicio/recálculo de hora seleccionada.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 4 (`e677086`)
- **Tarea aplicada:** Mostrar la fecha en formato de texto en español en la confirmación y detalle de la cita (`ConfirmarCitaScreen.kt` y `DetalleCitaScreen.kt`).
- **Resumen de la respuesta:** Se adaptaron las pantallas para utilizar `Fechas.fechaEnTexto(fechaIso)` al presentar la fecha agendada.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 5 (`8bc65e9`)
- **Tarea aplicada:** Añadir la funcionalidad de Médicos Favoritos por usuario en `Repositorio`, `MedicosScreen` e `HomeScreen`.
- **Resumen de la respuesta:** Se añadieron métodos para alternar y consultar favoritos observables mediante `SnapshotStateMap`, ícono animado de corazón, filtro por chip en el listado y carrusel de acceso directo en el inicio.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 6 (`464035f`)
- **Tarea aplicada:** Implementar la búsqueda de médicos por nombre desde la pantalla de Especialidades (`EspecialidadesScreen.kt`).
- **Resumen de la respuesta:** Se creó la función `Repositorio.buscarMedicosPorNombre` y se integró en `EspecialidadesScreen` para mostrar resultados filtrados cuando la búsqueda tiene al menos 2 caracteres.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 7 (`2becaef`)
- **Tarea aplicada:** Evitar la reserva de citas solapadas para el mismo paciente en la misma fecha y hora (`ConfirmarCitaScreen.kt` y `Repositorio`).
- **Resumen de la respuesta:** Se creó `Repositorio.tieneCitaEn` y se incluyó la validación previa al agendamiento en `ConfirmarCitaScreen` con mensaje claro de advertencia.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 8 (`4f61d7e`)
- **Tarea aplicada:** Reprogramar citas agendadas y extraer el selector de calendario/horarios a un componente compartido (`SelectorFechaHora.kt`).
- **Resumen de la respuesta:** Se extrajo `SelectorFechaHora.kt` sin alterar `FechaHoraScreen`, se creó `ReprogramarCitaScreen` con la función `Repositorio.reprogramarCita` y se enlazó en `DetalleCitaScreen`.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 9 (`5bad8fc`)
- **Tarea aplicada:** Crear la pantalla de Ayuda y Preguntas Frecuentes e integrarla en `PerfilScreen` y `AppNavigation`.
- **Resumen de la respuesta:** Se implementó `AyudaScreen.kt` con un acordeón de 6 preguntas/respuestas veraces, animaciones con `AnimatedVisibility` e `animateFloatAsState`, y se vinculó en el flujo de navegación.
- **Correcciones realizadas durante la sesión:** Se corrigió la advertencia de obsolescencia de `Icons.Default.HelpOutline` por `Icons.AutoMirrored.Filled.HelpOutline` en `PerfilScreen.kt`.

### Commit 10 (`f71a3e9`)
- **Tarea aplicada:** Agregar funcionalidad al menú hamburguesa lateral interactivo en la pantalla de Inicio (`HomeScreen.kt`).
- **Resumen de la respuesta:** Se integró `ModalNavigationDrawer` con `ModalDrawerSheet` en `HomeScreen` para permitir acceso directo deslizable a Ayuda, Términos y Notificaciones.
- **Correcciones realizadas durante la sesión:** Ninguna.

### Commit 11
- **Tarea aplicada:** Actualizar el archivo `README.md` con la documentación de la Fase 2 (con-ia) y crear el archivo `PROMPTS.md`.
- **Resumen de la respuesta:** Se agregaron las secciones explicativas, limitaciones, historial de commits y nota de procedencia en `README.md`, y se generó la bitácora estructurada de desarrollo en `PROMPTS.md`.
- **Correcciones realizadas durante la sesión:** Ninguna.

---

## Prompt 1 (Sistema de Sedes y Mis Doctores)

### Tarea aplicada:
Implementación del sistema de SEDES (flujo de Agendar Cita por sede y sustitución de Mis citas por pantalla Sedes) y del módulo "Mis doctores" (médicos favoritos agrupados por especialidad) con autoría local bajo "Carlos Fernando Junco Santiago".

### Resumen de la respuesta:
1. **Modelos y Repositorio:**
   - Creación de `Sede.kt` y adición de `sedeId` al modelo `Medico` asignando sedes ficticias a los médicos.
   - Funciones en `Repositorio` para consultas por sede (`sedes`, `obtenerSede`, `sedeDelMedico`, `medicosPorSede`, `especialidadesPorSede`, `medicosPorSedeYEspecialidad`, `citasPorSede`, `cantidadCitasUsuario`).
   - Funciones de favoritos por usuario (`esFavorito`, `alternarFavorito`, `medicosFavoritos`, `cantidadFavoritos`, `medicosFavoritosPorEspecialidad`).
2. **Pantallas y Componentes:**
   - `ElegirSedeScreen.kt`: Selección de sede con conteo de especialidades y doctores.
   - `EspecialidadesSedeScreen.kt`: Filtrado de especialidades disponibles por sede.
   - `MedicosScreen.kt`: Adaptada con parámetro opcional `sedeId` e indicador visual de la sede.
   - `ConfirmarCitaScreen.kt` y `DetalleCitaScreen.kt`: Presentación de la sede y dirección correspondiente.
   - `SedesScreen.kt`: Reemplazo de Mis citas por vista de citas agrupadas por sede con encabezados y etiquetas de estado.
   - `TarjetaMedico.kt`: Componente extraído con botón de corazón animado y aviso snackbar.
   - `MisDoctoresScreen.kt`: Listado de médicos favoritos agrupados por especialidad con opción Deshacer.
   - `HomeScreen.kt` y `BarraNavegacion.kt`: Tarjetas "Agendar cita" (a Elegir Sede), "Sedes", y nueva tarjeta de ancho completo "Mis doctores", actualizando la barra inferior de navegación a 4 destinos (Inicio, Sedes, Resultados, Perfil).

### Correcciones realizadas durante la sesión:
- Se ajustaron los límites de caracteres en los asuntos de los mensajes de commit para no superar los 72 caracteres de la norma del proyecto.
- Se configuró la autoría local de Git a `Carlos Fernando Junco Santiago` y `carlos.junco@tecsup.edu.pe`.

---

Correcciones manuales del usuario: pendiente de completar
