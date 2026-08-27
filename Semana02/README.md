# Lab02 - Carrito de Compras Kotlin

## Respuestas a las preguntas de análisis (Parte 2)

1. **¿Por qué `nombre` y `precio` son `val` pero `cantidad` es `var`?**  
   `nombre` y `precio` se ponen como `val` porque son los datos fijos del producto que vienen del catálogo y no deberían cambiar en medio de una compra. En cambio, `cantidad` es `var` porque el usuario sí necesita poder subir o bajar el número de unidades que se va a llevar en su carrito.

2. **¿Qué pasaría si intentas cambiar el precio después de crear el producto?**  
   Kotlin te va a dar un error de compilación (`Val cannot be reassigned`) y el programa no va a correr, ya que las variables declaradas con `val` son solo de lectura y no se pueden modificar una vez creadas.