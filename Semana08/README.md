# HuellitasVet 🐾

App móvil de una veterinaria con **dos perfiles en el mismo dispositivo**. El **Dueño** registra a sus mascotas, pide citas eligiendo servicio, fecha y hora, y revisa el historial clínico de cada una. El **Veterinario** ve la agenda, confirma citas, registra la atención (diagnóstico, tratamiento y peso), consulta a los pacientes y administra los servicios. Los dos perfiles comparten la misma base de datos Room: cuando el veterinario registra una atención, el dueño la ve al instante en el historial de su mascota.

> Laboratorio 08 · Programación en Móviles · Tecsup — **MVVM + Room**
> Todos los datos son ficticios (Ley 29733).

## Tecnología

- Kotlin 2.0 + Jetpack Compose + Material 3 (sin XML de pantallas)
- Room 2.6.1 (relaciones anidadas, transacciones, JOIN / SUM / GROUP BY)
- DataStore Preferences (perfil activo, modo oscuro, notificaciones, aceptar citas)
- Navigation Compose 2.8
- Patrón **MVVM + Repository (interface)**
- `applicationId` / paquete: `com.junco.huellitasvet` · `minSdk 26` (usa `java.time`)

## Cuentas de prueba

| Perfil | Correo | Clave |
| --- | --- | --- |
| Dueño | ana@correo.test | 1234 |
| Dueño | luis@correo.test | 1234 |
| Veterinario | vet@huellitas.test | admin |

También se puede crear una cuenta nueva desde **Regístrate** en la pantalla de inicio de sesión (dueño o veterinario).

## Arquitectura MVVM + Repository

```
VIEW (Composable)
  │ eventos (clics)           ▲ collectAsState()
  ▼                           │
VIEWMODEL (StateFlow + reglas de negocio)
  │ llama                     ▲ Flow / resultados
  ▼                           │
VetRepository (interface)
  │ la implementa
  ▼
VetRepositoryRoom ──► DAO ──► SQLite (huellitasvet.db)

DataStore (solo preferencias) ──► ViewModel
```

| Capa | Conoce a | NO debe |
| --- | --- | --- |
| View (`ui/`) | Solo a su ViewModel | Usar DAO, Repository ni reglas de negocio |
| ViewModel | Solo a la interface `VetRepository` | Conocer Composables ni DAO |
| Repository | Los DAO (en la implementación) | Conocer pantallas ni ViewModels |
| DAO / Room | SQL y entidades | Conocer ViewModels |

Regla que se siguió: una lectura que debe refrescar la pantalla es un `Flow` (sin `suspend`); una escritura o acción única es `suspend`.

## Base de datos

| Tabla | Relación |
| --- | --- |
| `usuarios` | — |
| `servicios` | — |
| `mascotas` | FK → `usuarios` (N mascotas por dueño) |
| `citas` | FK → `mascotas` y FK → `servicios` |

Las fechas se guardan como texto `aaaa-MM-dd` y las horas como `HH:mm`. El precio se copia dentro de la cita al pedirla.

## Estructura de paquetes

```
com.junco.huellitasvet
├── MainActivity.kt
├── VetApp.kt                      crea la base, el repository y las preferencias
├── data
│   ├── local
│   │   ├── entity    Servicio · Usuario · Mascota · Cita
│   │   ├── dao       ServicioDao · UsuarioDao · MascotaDao · CitaDao
│   │   ├── relation  MascotaConDueno · CitaConDetalle
│   │   ├── VetDatabase.kt
│   │   └── DatosIniciales.kt
│   ├── repository    VetRepository (interface) · VetRepositoryRoom
│   └── prefs         PreferenciasStore (DataStore)
├── model             Enums · ConteoServicio · Horarios (+ Formato)
├── viewmodel         Sesion · Inicio · Mascota · PedirCita · MisCitas
│                     Panel · Agenda · AtenderCita · Pacientes · Servicios
└── ui
    ├── theme         Color · Type · Theme (VetTheme por perfil + degradado)
    ├── components    Contenedores (cabecera, tarjeta, datos destacados)
    │                 AppTopBar · VetButton · VetTextField · EstadoCitaChip
    │                 MascotaAvatar · CitaCard · SelectorFechaHora
    │                 HistorialItem · OpcionChip · FilaSwitch · graficos/
    ├── navigation    Rutas · NavVet
    ├── dueno         Splash · Login · Registro · Inicio · Mascotas
    │                 FormularioMascota · FichaMascota · PedirCita
    │                 MisCitas · Perfil
    └── veterinario   Panel · Agenda · AtenderCita · Pacientes
                      Servicios · FormularioServicio · Ajustes
```

## Capturas

### Dueño

| Inicio de sesión | Inicio | Pedir cita |
| --- | --- | --- |
| ![Inicio de sesión](docs/capturas/01-login.png) | ![Inicio del dueño](docs/capturas/02-inicio-dueno.png) | ![Pedir cita](docs/capturas/03-pedir-cita.png) |

### Veterinario

| Panel | Agenda | Atender cita |
| --- | --- | --- |
| ![Panel](docs/capturas/04-panel-vet.png) | ![Agenda](docs/capturas/05-agenda-vet.png) | ![Atender cita](docs/capturas/06-atender-cita.png) |

## Para pensar

**¿Por qué la fecha y la hora se guardan como texto (`aaaa-MM-dd` y `HH:mm`)?**
Con ese formato el texto se ordena igual que las fechas (`2026-10-21` va antes que `2026-10-22`), se compara directamente en SQL con `=`, `>=` o `BETWEEN`, y se convierte con `LocalDate.parse()`. Así el laboratorio se concentra en Room y no en convertidores de tipos.

**¿Por qué el precio se guarda dentro de la cita?**
Es una "foto" del momento. Si mañana la clínica cambia el precio de la vacuna, las citas antiguas conservan lo que costaron y los ingresos del panel no se alteran.

**¿Por qué `registrarAtencion` es `suspend` y los `observar*` devuelven `Flow`?**
Registrar una atención cambia los datos una sola vez (acción única → `suspend`), y se hace dentro de `db.withTransaction` para que la cita y el peso de la mascota se actualicen juntos o no se actualice nada. Las pantallas, en cambio, necesitan enterarse cada vez que los datos cambian, y eso lo resuelve un `Flow`.

**¿Por qué las preferencias van en DataStore y no en Room?**
DataStore sirve para preferencias pequeñas de clave-valor (perfil activo, modo oscuro...). Las mascotas, citas y servicios son datos relacionados que necesitan consultas, JOIN y transacciones, y eso es de Room.

## Fases del laboratorio

- **Fase 1 (rama `main`)**: construcción paso a paso, un commit por paso (15 commits).
