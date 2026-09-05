# Lab03 - Registro de Producto (Parte B)

## Mejora con IA (Parte B)

| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
| :--- | :--- | :--- |
| Generación inicial: Solicité agregar validación de campos vacíos, ocultar la Card de resumen ante errores, mostrar un mensaje rojo y añadir el botón Limpiar conservando el estado con remember. | Código funcional en PantallaRegistro.kt con el botón Limpiar, manejo de estados para reiniciar la UI y un bloque de validación básico con condicionales if-else. | Acepté: La estructura de estados, el botón de limpiado y la visualización del mensaje rojo.<br>Corregí: La lógica de validación resultaba permisiva ante entradas alfabéticas en campos numéricos y faltaba verificar números mayores a cero. |
| Refinamiento y revisión crítica: Solicité estructurar la validación mediante una sentencia when, parsear números de forma segura con toDoubleOrNull() y toIntOrNull(), y verificar que precio y cantidad sean estrictamente positivos. | Código refactorizado para el evento onClick con la estructura when, manejo de nulos en la conversión numérica y mensajes de error específicos para valores menores o iguales a cero. | Acepté todo sin cambios: Mejoró la robustez de la aplicación, evitando excepciones en tiempo de ejecución por entrada de texto no válido y garantizando datos coherentes en el cálculo del importe. |

##Pronts

<img width="391" height="769" alt="image" src="https://github.com/user-attachments/assets/0bbe4fc1-0004-4a0c-86b2-ba476b31afdf" />


<img width="348" height="155" alt="image" src="https://github.com/user-attachments/assets/ffbebc41-67c9-49bd-aad3-441c171e74f5" />


<img width="373" height="328" alt="image" src="https://github.com/user-attachments/assets/94f0b8fc-8e97-4c8b-85cb-7a9f220da18f" />


<img width="375" height="394" alt="image" src="https://github.com/user-attachments/assets/428250a1-0326-4353-909e-74e61d4a2f02" />

##Qué generó Gemini 

<img width="594" height="841" alt="image" src="https://github.com/user-attachments/assets/9652d585-a400-43c9-a1a5-f894da88dc76" />


<img width="603" height="850" alt="image" src="https://github.com/user-attachments/assets/5e18235b-e5e6-407c-9e4a-3b0eaa6b9c17" />


<img width="615" height="852" alt="image" src="https://github.com/user-attachments/assets/d1e0d92a-1eb8-4098-ba3b-13c1b89821f7" />


<img width="581" height="827" alt="image" src="https://github.com/user-attachments/assets/4c91f97c-0ab8-4f5c-b926-2343a3f1b038" />


