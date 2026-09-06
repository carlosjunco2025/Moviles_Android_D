# Lab03 - Registro de Producto (Tarea S3)

## Capturas de Pantalla

 <img width="408" height="460" alt="image" src="https://github.com/user-attachments/assets/893316d0-02dd-490f-9fd3-dfaaaff055f5" />

 <img width="719" height="706" alt="image" src="https://github.com/user-attachments/assets/92ee714f-40d0-4bfb-80e6-b98c7b0137f0" />

 <img width="823" height="884" alt="image" src="https://github.com/user-attachments/assets/2b85d246-b931-4a03-b878-6b1ddca41c40" />

<img width="808" height="859" alt="image" src="https://github.com/user-attachments/assets/b658629a-bb09-4bca-89ec-3b504c8ae38f" />


## Documentación Teórica

### ¿Qué pasaría si declaras las variables de los campos SIN remember?

**Respuesta:** Si declaras las variables utilizando únicamente mutableStateOf sin envolverlas en remember, cada vez que la pantalla sufra una recomposición (por ejemplo, al teclear una letra), la función Composable se ejecutará nuevamente desde el inicio y re-inicializará la variable a su valor inicial vacío . Como consecuencia, el estado ingresado por el usuario se perderá de inmediato y la caja de texto parecerá no registrar lo que se escribe.
