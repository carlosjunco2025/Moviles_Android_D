# Lab03 - Registro de Producto (Jetpack Compose)

## Nombre 
Carlos Fernando Junco Santiago

## Descripción
Aplicación Android desarrollada en Jetpack Compose para el registro e ingreso de datos de productos en tiempo real utilizando manejo de estado local con `remember` y `mutableStateOf`.

## Capturas de Pantalla (Parte A)

### 1. Formulario Vacío
<img width="258" height="481" alt="image" src="https://github.com/user-attachments/assets/4e585374-1d20-4ab9-a3e5-2abd0564e301" />


### 2. Producto Registrado
<img width="271" height="361" alt="image" src="https://github.com/user-attachments/assets/9459df3b-81db-4d8e-ba3d-98c845297b28" />


## Pregunta de Reflexión

¿Qué pasaría si declaras las variables de los campos SIN remember?
Si pones solo mutableStateOf sin usar remember, los campos se borran solos al instante. Intentas escribir cualquier letra en el formulario y el cuadro de texto se queda completamente en blanco.

Esto pasa porque al presionar una tecla, Jetpack Compose vuelve a procesar toda la pantalla para actualizar la interfaz. Al no tener la función remember, la variable vuelve a crearse desde cero con las comillas vacías en cada actualización, perdiendo cualquier dato que el usuario haya escrito. El uso de remember es fundamental para mantener guardada la información en memoria mientras la pantalla se redibuja.


<img width="355" height="152" alt="image" src="https://github.com/user-attachments/assets/8da7c6c3-c1ad-4a3b-890c-442b50819fb1" />


<img width="727" height="641" alt="image" src="https://github.com/user-attachments/assets/2ddb2c81-290d-4c41-8a3a-5c473bb4e727" />


<img width="496" height="220" alt="image" src="https://github.com/user-attachments/assets/10e35da1-fef9-45ca-9bb8-26d0e5248667" />


<img width="264" height="492" alt="image" src="https://github.com/user-attachments/assets/e6d2c345-8670-4fcf-ad10-41d071bad43f" />


<img width="256" height="334" alt="image" src="https://github.com/user-attachments/assets/cdab91a9-5098-4d0a-b0f0-2afd92c42d93" />
