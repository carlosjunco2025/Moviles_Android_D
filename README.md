# Lab03 - Registro de Producto (Jetpack Compose)

## Descripción
Aplicación Android desarrollada en Jetpack Compose para el registro e ingreso de datos de productos en tiempo real utilizando manejo de estado local con emember y mutableStateOf.

## Uso de remember
Se utilizó emember { mutableStateOf(...) } para preservar el estado de las variables (
ombre, precio, cantidad, mostrarResumen) a lo largo de las recomposiciones de la interfaz. Esto asegura que la UI reaccione automáticamente a las entradas del usuario sin perder los datos tipeados.

## Capturas de Pantalla

### 1. Formulario Vacío
![Pantalla Vacía](captura_vacia.png)

### 2. Producto Registrado
![Producto Registrado](captura_registrado.png)
