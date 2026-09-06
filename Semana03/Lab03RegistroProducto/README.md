# Lab03 - Registro de Notas (Tarea Casa S3)

## Evidencias de Ejecución

### 1. Estado Inicial (Notas en 0 y botón deshabilitado)
<img width="1411" height="542" alt="image" src="https://github.com/user-attachments/assets/89817298-0658-4375-8c28-2b5fda4b7f01" />


### 2. Estado Calculado (Con notas, promedios y retos opcionales)
<img width="298" height="491" alt="image" src="https://github.com/user-attachments/assets/83ab71b7-16bd-4f84-bd4b-62e4674768b9" />

---

## Descripción de la Solución
Se implementó la pantalla **Registro de Notas** aplicando los siguientes controles y componentes de Jetpack Compose:

* **Sliders & Semáforo:** Asignación de notas en tiempo real (0 a 20) con badges dinámicos que cambian a rojo (< 13) o verde (>= 13).
* **Switch & Checkbox:** Switch para activar el redondeo (`roundToInt`) y Checkbox obligatorio para habilitar el botón "CALCULAR PROMEDIO".
* **Lógica & Categorización:** Cálculo del promedio ponderado (20%, 25%, 30%, 25%) y evaluación de la observación mediante una expresión `when` para definir el chip de estado.
* **Retos Opciones Incluidos:** 
  - Visualización del aporte de cada curso (`nota × peso`).
  - Semáforo en badges de las notas.
  - Botón **LIMPIAR** para resetear todo el formulario.

---
**Desarrollado por:** Carlos Junco
