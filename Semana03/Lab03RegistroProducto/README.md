# Lab03 - Registro de Producto (Tarea S3)

## Capturas de Pantalla

## Documentación Teórica

### ¿Qué pasaría si declaras las variables de los campos SIN remember?

**Respuesta:** Si declaras las variables utilizando únicamente mutableStateOf sin envolverlas en remember, cada vez que la pantalla sufra una recomposición (por ejemplo, al teclear una letra), la función Composable se ejecutará nuevamente desde el inicio y re-inicializará la variable a su valor inicial vacío . Como consecuencia, el estado ingresado por el usuario se perderá de inmediato y la caja de texto parecerá no registrar lo que se escribe.
