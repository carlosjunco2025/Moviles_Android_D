# Prompts utilizados

Fase 2 · rama `mejora-ia` · HuellitasVet (`com.junco.huellitasvet`)

> Completa los campos marcados con **[COMPLETAR]** con lo que realmente pasó al usar la IA: qué generó, qué error tenía y qué corregiste tú. Este documento solo sirve si es verdadero.

## Prompt 1 · Grafico de peso en la ficha de la mascota

**Texto enviado a Gemini:**

```text
ROL
Eres un desarrollador Android senior experto en Jetpack Compose, Room y MVVM.

CONTEXTO
Proyecto Android en Kotlin con Jetpack Compose y Material 3. Paquete com.junco.huellitasvet. Arquitectura MVVM + Repository: la View (ui/) solo conoce su ViewModel; el ViewModel solo conoce la interface VetRepository; VetRepositoryRoom la implementa y llama a los DAO de Room 2.6.1 (VetDatabase, KSP). Las fechas son texto 'aaaa-MM-dd' y las horas 'HH:mm'. Lecturas que refrescan la pantalla = Flow; escrituras = suspend.

TAREA
En la ficha de la mascota (pestaña Historial) mostrar una linea con el peso registrado en cada cita ATENDIDA, de la mas antigua a la mas nueva.

RESTRICCIONES
- La consulta va en CitaDao (solo citas ATENDIDAS con pesoRegistrado no nulo, ordenadas por fecha).
- Declara primero la funcion en VetRepository y luego implementala en VetRepositoryRoom.
- Crea el modelo PuntoPeso(fecha: String, peso: Double) en el paquete model.
- MascotaViewModel expone la lista como StateFlow.
- La View solo dibuja el grafico con Canvas en un composable GraficoLineas; si hay menos de 2 puntos muestra un texto.
- No uses XML ni librerias externas.

FORMATO DE SALIDA
Muestrame solo los archivos que cambian, completos, indicando la ruta de cada uno.
```

**Qué generó la IA:** [COMPLETAR]

**Qué tuve que corregir yo:** [COMPLETAR]

**¿Respeta MVVM?:** [COMPLETAR: Sí / No (explica)]

## Prompt 2 · Reprogramar cita

**Texto enviado a Gemini:**

```text
ROL
Eres un desarrollador Android senior experto en Jetpack Compose, Room y MVVM.

CONTEXTO
Proyecto Android en Kotlin con Jetpack Compose y Material 3. Paquete com.junco.huellitasvet. Arquitectura MVVM + Repository: la View (ui/) solo conoce su ViewModel; el ViewModel solo conoce la interface VetRepository; VetRepositoryRoom la implementa y llama a los DAO de Room 2.6.1 (VetDatabase, KSP). Las fechas son texto 'aaaa-MM-dd' y las horas 'HH:mm'. Lecturas que refrescan la pantalla = Flow; escrituras = suspend.

TAREA
Permitir que el dueño cambie la fecha y la hora de una cita PENDIENTE o CONFIRMADA desde Mis citas.

RESTRICCIONES
- Solo se puede reprogramar si faltan mas de 2 horas para la cita (misma regla que cancelar).
- El nuevo horario debe estar libre: no puede haber otra cita no cancelada en esa fecha y hora; el horario actual de la propia cita no cuenta como ocupado.
- Las horas que ya pasaron hoy deben estar deshabilitadas.
- Consulta en CitaDao, funcion declarada en VetRepository (devuelve Boolean) e implementada en VetRepositoryRoom.
- Crea ReprogramarCitaViewModel y ReprogramarCitaScreen con una ruta nueva en Rutas y NavVet; en Mis citas aparece un boton 'Reprogramar'.
- Reutiliza el selector de fecha y hora de Pedir cita. No cambies el esquema de la base.

FORMATO DE SALIDA
Muestrame solo los archivos que cambian, completos, indicando la ruta de cada uno.
```

**Qué generó la IA:** [COMPLETAR]

**Qué tuve que corregir yo:** [COMPLETAR]

**¿Respeta MVVM?:** [COMPLETAR: Sí / No (explica)]

## Prompt 3 · Busqueda de pacientes

**Texto enviado a Gemini:**

```text
ROL
Eres un desarrollador Android senior experto en Jetpack Compose, Room y MVVM.

CONTEXTO
Proyecto Android en Kotlin con Jetpack Compose y Material 3. Paquete com.junco.huellitasvet. Arquitectura MVVM + Repository: la View (ui/) solo conoce su ViewModel; el ViewModel solo conoce la interface VetRepository; VetRepositoryRoom la implementa y llama a los DAO de Room 2.6.1 (VetDatabase, KSP). Las fechas son texto 'aaaa-MM-dd' y las horas 'HH:mm'. Lecturas que refrescan la pantalla = Flow; escrituras = suspend.

TAREA
Mejorar el buscador de Pacientes del veterinario: buscar por nombre de mascota, raza o nombre del dueño y filtrar por especie al mismo tiempo.

RESTRICCIONES
- La busqueda ignora mayusculas y tildes ('siames' encuentra 'Siamés').
- Cada chip de especie muestra cuantos resultados habria entre parentesis, segun el texto escrito.
- Si no hay resultados, la pantalla muestra un mensaje.
- El cambio es solo de ViewModel y View: no toques DAO, Repository ni la base.
- PacientesViewModel expone los resultados y los contadores como StateFlow (combine de los flujos).

FORMATO DE SALIDA
Muestrame solo los archivos que cambian, completos, indicando la ruta de cada uno.
```

**Qué generó la IA:** [COMPLETAR]

**Qué tuve que corregir yo:** [COMPLETAR]

**¿Respeta MVVM?:** [COMPLETAR: Sí / No (explica)]

## Prompt 4 · Carnet de vacunas

**Texto enviado a Gemini:**

```text
ROL
Eres un desarrollador Android senior experto en Jetpack Compose, Room y MVVM.

CONTEXTO
Proyecto Android en Kotlin con Jetpack Compose y Material 3. Paquete com.junco.huellitasvet. Arquitectura MVVM + Repository: la View (ui/) solo conoce su ViewModel; el ViewModel solo conoce la interface VetRepository; VetRepositoryRoom la implementa y llama a los DAO de Room 2.6.1 (VetDatabase, KSP). Las fechas son texto 'aaaa-MM-dd' y las horas 'HH:mm'. Lecturas que refrescan la pantalla = Flow; escrituras = suspend.

TAREA
Agregar un carnet de vacunas por mascota, visible en una pestaña 'Vacunas' de la ficha.

RESTRICCIONES
- Nueva entidad Vacuna(id, mascotaId con FK a Mascota, nombre, fecha, proximaDosis nullable) y su VacunaDao.
- Registrala en VetDatabase y sube la version de la base (la estrategia actual es fallbackToDestructiveMigration).
- Declara en VetRepository observarVacunas, guardarVacuna y eliminarVacuna, e implementalas en VetRepositoryRoom.
- MascotaViewModel expone las vacunas como StateFlow y valida: nombre de 3+ letras, fecha valida no futura, proxima dosis opcional y posterior a la aplicada.
- El dueño puede agregar y quitar vacunas; el veterinario solo consulta.
- Agrega 3 vacunas de ejemplo en DatosIniciales.

FORMATO DE SALIDA
Muestrame solo los archivos que cambian, completos, indicando la ruta de cada uno.
```

**Qué generó la IA:** [COMPLETAR]

**Qué tuve que corregir yo:** [COMPLETAR]

**¿Respeta MVVM?:** [COMPLETAR: Sí / No (explica)]

## Prompt 5 · Recordatorio de proximo control

**Texto enviado a Gemini:**

```text
ROL
Eres un desarrollador Android senior experto en Jetpack Compose, Room y MVVM.

CONTEXTO
Proyecto Android en Kotlin con Jetpack Compose y Material 3. Paquete com.junco.huellitasvet. Arquitectura MVVM + Repository: la View (ui/) solo conoce su ViewModel; el ViewModel solo conoce la interface VetRepository; VetRepositoryRoom la implementa y llama a los DAO de Room 2.6.1 (VetDatabase, KSP). Las fechas son texto 'aaaa-MM-dd' y las horas 'HH:mm'. Lecturas que refrescan la pantalla = Flow; escrituras = suspend.

TAREA
Al atender una cita, el veterinario puede fijar una fecha de proximo control, y el dueño la ve como recordatorio en su Inicio.

RESTRICCIONES
- Agrega el campo fechaControl (String?, nullable) a la entidad Cita y sube la version de la base.
- registrarAtencion recibe la fecha de control y sigue siendo una sola transaccion que tambien actualiza el peso.
- Valida en AtenderCitaViewModel: si se escribe, debe ser 'aaaa-MM-dd' y posterior a hoy.
- Consulta en CitaDao con las citas ATENDIDAS del dueño que tienen fechaControl; InicioViewModel calcula el control mas cercano que no haya vencido y lo expone como StateFlow.
- InicioScreen muestra una tarjeta 'Proximo control de <mascota>' solo si existe uno.
- Cuidado: no uses smart cast sobre propiedades delegadas con 'by collectAsState()'.

FORMATO DE SALIDA
Muestrame solo los archivos que cambian, completos, indicando la ruta de cada uno.
```

**Qué generó la IA:** [COMPLETAR]

**Qué tuve que corregir yo:** [COMPLETAR]

**¿Respeta MVVM?:** [COMPLETAR: Sí / No (explica)]

## Prompt 6 · Calendario mensual en la agenda

**Texto enviado a Gemini:**

```text
ROL
Eres un desarrollador Android senior experto en Jetpack Compose, Room y MVVM.

CONTEXTO
Proyecto Android en Kotlin con Jetpack Compose y Material 3. Paquete com.junco.huellitasvet. Arquitectura MVVM + Repository: la View (ui/) solo conoce su ViewModel; el ViewModel solo conoce la interface VetRepository; VetRepositoryRoom la implementa y llama a los DAO de Room 2.6.1 (VetDatabase, KSP). Las fechas son texto 'aaaa-MM-dd' y las horas 'HH:mm'. Lecturas que refrescan la pantalla = Flow; escrituras = suspend.

TAREA
En la agenda del veterinario, reemplazar los chips de dias por una vista de mes con un punto en los dias que tienen citas.

RESTRICCIONES
- Consulta en CitaDao: las fechas de citas no canceladas dentro de un rango (BETWEEN desde y hasta); declara la funcion en VetRepository.
- AgendaViewModel expone el mes visible (YearMonth) y el conjunto de dias con citas como StateFlow, y funciones para pasar al mes anterior y siguiente.
- Crea un composable CalendarioMes con la semana empezando el lunes, el dia de hoy resaltado y el dia elegido relleno.
- Al tocar un dia, la lista de abajo muestra las citas de ese dia.
- No cambies el esquema de la base. No uses librerias externas.

FORMATO DE SALIDA
Muestrame solo los archivos que cambian, completos, indicando la ruta de cada uno.
```

**Qué generó la IA:** [COMPLETAR]

**Qué tuve que corregir yo:** [COMPLETAR]

**¿Respeta MVVM?:** [COMPLETAR: Sí / No (explica)]

## Reflexión (5 líneas)

- ¿Qué mejoró la IA y qué no? [COMPLETAR]
- ¿Qué error cometió? [COMPLETAR]
- ¿Qué aprendí sobre MVVM, Room y reglas de negocio? [COMPLETAR]
