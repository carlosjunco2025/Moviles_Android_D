
## 📱 Descripción del Proyecto
Este proyecto corresponde a la versión optimizada del **Portal Académico**, desarrollada con la ayuda de herramientas de Inteligencia Artificial para la estructuración de la navegación con Jetpack Compose y la optimización de los componentes de la interfaz.

---

## 🤖 Prompts Utilizados en el Desarrollo con IA

A continuación se detalla el prompt principal utilizado durante el laboratorio:

### Prompt Principal:
Crea una aplicación Android llamada NavLab con Kotlin, Jetpack Compose, Material 3 y Navigation Compose. Implementa las cinco pantallas de las imágenes adjuntas con la misma apariencia y distribución.

Las imágenes son la referencia visual principal. Reproduce los colores, degradados, posiciones, tarjetas, espacios vacíos, tipografías, iconos y proporciones. No rediseñes las pantallas ni agregues elementos que no aparezcan.

El usuario principal se llama **Carlos Junco**. Sustituye el nombre de la persona principal de las referencias por este nombre en todas las pantallas. Su correo de ejemplo será **[carlos.junco@tecsup.edu.pe](mailto:carlos.junco@tecsup.edu.pe)**.

## 1. Preparación del proyecto

Revisa la estructura existente antes de implementar.

Utiliza Kotlin y Jetpack Compose.
Usa componentes Material 3 y Navigation Compose.
Si ya existe un proyecto, conserva su configuración y versiones compatibles.
Si debes crear el proyecto desde cero, utiliza el paquete com.junco.navlab.
Implementa los archivos completos y la navegación funcional.
Utiliza únicamente datos locales de demostración.
No agregues servicios externos, bases de datos ni autenticación real.
No añadas funciones, pantallas, mensajes o adornos que no estén solicitados.

## 2. Datos del usuario principal

Utiliza estos valores de manera consistente:

Nombre: Carlos Junco.
Nombre Completo: Carlos Junco.
Correo: `carlos.junco@tecsup.edu.pe`.
ID Estudiante: 2024-0001.
Teléfono: +51 987 654 321.
Carrera en el directorio y expediente: Ingeniería de Sistemas.
Carrera en configuración de perfil: Ingeniería de Software.
Facultad: Ingeniería y Tecnología.
Ciclo Actual: VI Ciclo.
Biografía: Estudiante destacado con interés en desarrollo Android.

Son valores de demostración.

No debe aparecer el nombre ni el correo de Juan León. Tampoco deben aparecer nombres o correos de otra persona en los espacios correspondientes a Carlos Junco.

No reduzcas su nombre a “Carlos” o “Junco”: muestra Carlos Junco en el saludo, directorio, expediente, cabecera del perfil y campo Nombre Completo.

Conserva la diferencia de carrera entre expediente y perfil porque así aparece en las imágenes. No unifiques esos textos.

## 3. Diseño general

Toma como referencia un área útil aproximada de 360 dp de ancho y 760 dp de alto, excluyendo las barras del sistema. Adapta las dimensiones a cada dispositivo conservando la composición.

### Colores

Fondo principal: #FCF8FF.
Morado principal: #67509D.
Morado oscuro: #493278.
Lila claro: #E8DDF8.
Tarjetas del directorio: #E6E0E9.
Tarjeta del expediente: #F0EAF4.
Texto principal: #242126.
Texto secundario: #726B78.
Etiquetas: #938B99.
Bordes de campos: #A69FAB.
Separadores: #D7CEDD.
Texto e iconos de cerrar sesión: #B66D5F.
Fondo del botón de cerrar sesión del perfil: #F3DEDA.

Desactiva los colores dinámicos y mantén estas pantallas con apariencia clara.

### Tipografía y componentes

Fuente Roboto o sans serif de Android.
Títulos principales en negrita.
Títulos de barras superiores: 18 sp, seminegrita.
Títulos de tarjetas: 15 sp, negrita.
Valores: entre 13 y 14 sp.
Textos secundarios: entre 11 y 12 sp.
Etiquetas: 10 sp.
Encabezados de sección: 11 sp, seminegrita y espaciado de letras de 1 sp.
Esquinas redondeadas y sombras discretas según cada pantalla.

Configura explícitamente Material 3 para evitar que sus estilos predeterminados cambien el diseño.

Usa las barras reales del sistema. No dibujes el marco del teléfono, la cámara, una hora falsa ni una barra de navegación falsa.

## 4. Pantalla de acceso

Archivo: screens/LoginScreen.kt.

Esta será la pantalla inicial.

### Fondo y tarjeta

Sin barra superior.
Degradado vertical desde #E8DCFA arriba hasta #FFF9FC abajo.
Tarjeta centrada horizontalmente.
Centro de la tarjeta aproximadamente al 48 % de la altura útil.
Ancho aproximado de 304 dp en una pantalla de 360 dp.
Márgenes laterales de 28 dp.
Altura aproximada de 350 dp, adaptable al contenido.
Fondo #E6E0E9.
Esquinas de 18 dp.
Elevación de 6 dp.
Padding horizontal de 20 dp y vertical de 22 dp.

### Contenido exacto

1. Título Portal Académico, centrado, morado, 23 sp y negrita.
2. Subtítulo Accede a tu cuenta, centrado, gris y 12 sp, separado 2 dp del título.
3. Espacio de 34 dp.
4. Campo de correo.
5. Espacio de 16 dp.
6. Campo de contraseña.
7. Espacio de 28 dp.
8. Botón INICIAR SESIÓN.
9. Espacio de 20 dp.
10. Texto ¿Olvidaste tu contraseña?, centrado, gris, 10 sp y sin subrayado.

### Campo de correo

Placeholder: Correo Institucional.
Icono de sobre a la izquierda.
Altura visual aproximada de 44 dp.
Ancho completo dentro de la tarjeta.
Esquinas de 8 dp.
Borde gris de 1 dp.
Fondo transparente.
Icono de 18 dp.
Texto de 12 sp.
Una sola línea.
Teclado de correo.
Sin etiqueta flotante exterior.
No precargues ningún correo.

### Campo de contraseña

Placeholder: Contraseña.
Mismas dimensiones y estilo del correo.
Candado a la izquierda.
Ojo tachado a la derecha.
Contraseña oculta inicialmente.
El ojo alterna entre mostrar y ocultar.
Una sola línea.

### Botón de acceso

Fondo #67509D.
Texto blanco de 12 sp y negrita.
Altura visual de 44 dp.
Esquinas de 8 dp.
Elevación de 3 dp.
Ancho completo del contenido.
No convertirlo en una píldora.

### Funcionamiento

Los campos permiten escribir y conservan su estado durante recomposiciones. El botón abre el inicio cuando ambos tienen contenido. Si alguno está vacío, permanece en el acceso sin agregar mensajes ni diálogos.

No guardes ni registres contraseñas. El texto de recuperación es únicamente visual, sin una pantalla adicional.

## 5. Pantalla de inicio

Archivo: screens/HomeScreen.kt.

### Fondo

Sin barra superior. Degradado vertical:

Arriba: #67509D.
Centro: #B6A7CA.
Abajo: #FFF9FC.

### Bienvenida

El contenido principal comienza aproximadamente al 18 % de la altura útil.

Encabezado centrado en dos líneas:

Bienvenido,
Carlos Junco

Color blanco.
Tamaño de 26 sp.
Negrita.
Interlineado aproximado de 30 sp.

Después de 32 dp, muestra:

¿Qué deseas gestionar hoy?

Centrado, 12 sp y blanco con opacidad del 90 %.

### Tarjetas

Primera tarjeta a 16 dp del subtítulo.

Ambas tarjetas tienen:

Márgenes horizontales de 22 dp.
Altura aproximada de 70 dp.
Fondo #FFF9FF.
Esquinas de 16 dp.
Elevación de 4 dp.
Padding horizontal de 16 dp.
Contenido centrado verticalmente.
Separación entre tarjetas de 14 dp.

A la izquierda, un recuadro de 46 × 46 dp, fondo #E8DDF8, esquinas de 10 dp e icono morado de 24 dp.

A la derecha, separados 14 dp del recuadro, el título y subtítulo.

Primera tarjeta:

Icono de grupo de personas.
Título: Directorio de Alumnos.
Subtítulo: Ver y gestionar estudiantes.

Segunda tarjeta:

Icono de persona.
Título: Mi Perfil Académico.
Subtítulo: Datos personales y progreso.

Títulos de 15 sp, negrita y color oscuro. Subtítulos de 11 sp y gris.

No agregues flechas a estas tarjetas.

### Cerrar sesión

Coloca Cerrar Sesión Segura centrado horizontalmente a 28 dp del borde inferior útil.

Icono de salida de 18 dp a la izquierda.
Separación de 6 dp.
Texto de 11 sp y seminegrita.
Texto e icono en #B66D5F.
Sin fondo sólido, borde ni tarjeta.

Mantén el espacio vacío amplio entre las tarjetas y esta acción. No la coloques inmediatamente debajo de las tarjetas.

### Navegación

Directorio de Alumnos abre el directorio.
Mi Perfil Académico abre el perfil.
Cerrar Sesión Segura vuelve al acceso y limpia la pila de navegación.

## 6. Directorio de alumnos

Archivo: screens/ListScreen.kt.

### Barra superior

Fondo general #FCF8FF.
Barra superior de 56 dp con fondo #E8DDF8.
Flecha de regreso a la izquierda.
Título Directorio de Alumnos.
Título de 18 sp, seminegrita y color #493278.

### Lista

Utiliza LazyColumn.

Margen superior de 12 dp desde la barra.
Márgenes laterales de 12 dp.
Cinco tarjetas.
Separación vertical de 10 dp.
Sin buscador, filtros ni encabezados adicionales.

Cada tarjeta:

Altura mínima de 84 dp.
Fondo #E6E0E9.
Esquinas de 14 dp.
Elevación de 2 dp.
Padding horizontal de 16 dp.
Retrato circular de 52 dp a la izquierda.
Separación de 14 dp entre retrato y textos.
Nombre de 15 sp, negrita y color oscuro.
Carrera debajo, de 12 sp y morada.
Chevron derecho de 18 dp y color #938B99.

### Registros exactos

1. Carlos Junco — Ingeniería de Sistemas.
2. Maria Garcia — Arquitectura.
3. Carlos Perez — Medicina.
4. Ana Lopez — Derecho.
5. Luis Ramirez — Administración.

Respeta los nombres, las carreras y el orden. Carlos Junco y Carlos Perez son registros distintos.

Toda la tarjeta permite seleccionar al estudiante y abrir su expediente mediante un identificador estable.

La flecha superior vuelve al inicio. Conserva vacío el espacio debajo de las cinco tarjetas.

## 7. Expediente académico

Archivo: screens/DetailScreen.kt.

Recibe el identificador entero del estudiante y consulta sus datos locales.

### Parte superior

Fondo #FCF8FF.
Barra superior de 56 dp, con el mismo fondo.
Flecha de regreso oscura.
Título Expediente Académico, de 18 sp y seminegrita.

Debajo de la barra:

Cabecera de ancho completo y 148 dp de altura.
Degradado vertical de #67509D a #625A6D.
Esquinas inferiores de 26 dp.
Esquinas superiores rectas.

### Retrato y nombre

Retrato circular de 112 dp.
Centrado horizontalmente.
Superpuesto al borde inferior de la cabecera.
Su centro coincide aproximadamente con ese borde.
Borde blanco de 3 dp.
Sombra discreta.
Usa ContentScale.Crop cuando exista fotografía.

A 16 dp debajo del retrato:

Carlos Junco, centrado, 22 sp y negrita.
Ingeniería de Sistemas, centrado, morado y 12 sp, separado 2 dp del nombre.

### Tarjeta de información

Ubicada a 28 dp de la carrera.

Márgenes horizontales de 20 dp.
Fondo #F0EAF4.
Esquinas de 18 dp.
Padding de 18 dp.
Sin sombra marcada.

Tres filas:

1. Icono de identificación.

   * Etiqueta: ID Estudiante.
   * Valor: 2024-0001.

2. Icono de sobre.

   * Etiqueta: Correo Electrónico.
   * Valor: `carlos.junco@tecsup.edu.pe`.

3. Icono de birrete.

   * Etiqueta: Facultad.
   * Valor: Ingeniería y Tecnología.

En cada fila:

Icono morado de 18 dp.
Separación de 14 dp entre icono y texto.
Etiqueta de 10 sp y color #938B99.
Valor de 13 sp, peso medio y color oscuro.
Separación vertical de 14 dp entre filas.

Dentro de la misma tarjeta, después de las filas:

Espacio de 18 dp.
Separador de 1 dp en #D7CEDD.
Espacio de 14 dp.
Título Biografía, 14 sp y negrita.
Espacio de 8 dp.
Texto Estudiante destacado con interés en desarrollo Android., de 13 sp y gris, alineado a la izquierda.

Para los demás estudiantes, muestra su nombre y carrera correspondientes. En ID, correo, facultad y biografía utiliza — cuando no existan datos proporcionados.

No agregues cursos, notas, promedios ni botones de edición. La flecha superior vuelve al directorio.

## 8. Configuración de perfil

Archivo: screens/ProfileScreen.kt.

### Barra superior y cabecera

Fondo general #FCF8FF.
Barra superior de 56 dp.
Flecha de regreso.
Título Configuración de Perfil, 18 sp, seminegrita y color oscuro.

Cabecera:

Ancho completo.
Altura aproximada de 160 dp.
Esquinas rectas.
Degradado horizontal desde #67509D a la izquierda hasta #745869 a la derecha.

Dentro de la cabecera:

Retrato circular de 76 dp, centrado.
Margen superior de 18 dp.
Borde claro de 3 dp.
Separación de 10 dp debajo del retrato.
Nombre Carlos Junco, blanco, centrado, 17 sp y negrita.

### Información personal

Contenido con padding horizontal de 24 dp.

A 20 dp debajo de la cabecera, coloca:

INFORMACIÓN PERSONAL

Tamaño de 11 sp.
Seminegrita.
Color #806A9F.
Espaciado de letras de 1 sp.

Primera fila a 16 dp del encabezado.

Filas:

1. Icono de persona.

   * Etiqueta: Nombre Completo.
   * Valor: Carlos Junco.

2. Icono de sobre.

   * Etiqueta: Correo.
   * Valor: `carlos.junco@tecsup.edu.pe`.

3. Icono de teléfono.

   * Etiqueta: Teléfono.
   * Valor: +51 987 654 321.

Cada fila:

Altura mínima de 52 dp.
Recuadro de icono de 32 × 32 dp.
Fondo del recuadro #E6E0E9.
Esquinas de 8 dp.
Icono gris oscuro de 18 dp.
Separación de 14 dp entre recuadro y textos.
Etiqueta de 10 sp y color #938B99.
Valor de 14 sp, seminegrita y color oscuro.

### Información académica

Después de 24 dp, coloca:

ACADÉMICO

Con el mismo estilo del encabezado anterior.

A 16 dp del encabezado:

1. Icono de birrete.

   * Etiqueta: Carrera.
   * Valor: Ingeniería de Software.

2. Icono de calendario.

   * Etiqueta: Ciclo Actual.
   * Valor: VI Ciclo.

### Botón inferior

Coloca Cerrar Sesión en la parte inferior, a 20 dp del borde útil, con márgenes laterales de 24 dp.

Altura visual aproximada de 30 dp.
Área táctil mínima de 48 dp.
Fondo #F3DEDA.
Esquinas de 15 dp.
Sin sombra.
Icono de salida de 18 dp.
Texto de 12 sp y negrita.
Texto e icono en #B66D5F.
Contenido centrado.

Conserva el espacio vacío amplio entre los datos académicos y el botón. No subas el botón junto a las filas.

La flecha vuelve al inicio. Cerrar sesión vuelve al acceso y limpia la pila.

## 9. Iconos y fotografías

Incluye los iconos que aparecen en las referencias:

Flecha de regreso.
Sobre.
Candado.
Ojo y ojo tachado.
Grupo de personas.
Persona.
Salida.
Chevron derecho.
Identificación.
Birrete.
Teléfono.
Calendario.

Mantén su ubicación, tamaño relativo y color. Reutiliza iconos disponibles o recursos vectoriales locales equivalentes. No uses emojis ni caracteres de texto para reemplazarlos.

Para las fotografías, utiliza los recursos adjuntos o locales correspondientes, si están disponibles. Mantén el mismo retrato del usuario principal en directorio, expediente y perfil.

No sustituyas los retratos por fotografías aleatorias. Si falta un recurso utilizable, conserva su espacio circular con fondo neutro e informa de esa limitación al entregar. No afirmes que las fotografías coinciden si no dispones de ellas.

## 10. Navegación funcional

Define estas rutas:

login.
home.
list.
detail/{itemId}.
profile.

Recorridos:

Acceso → inicio.
Inicio → directorio.
Directorio → expediente del alumno seleccionado.
Expediente → directorio.
Directorio → inicio.
Inicio → perfil.
Perfil → inicio.
Cerrar sesión desde inicio o perfil → acceso.

Al iniciar sesión, retira el acceso de la pila para que Atrás no vuelva al formulario.

Al cerrar sesión, elimina las pantallas internas de la pila y borra la contraseña. Atrás no debe reabrir el portal.

No agregues persistencia de sesión.

## 11. Componentes independientes de MainActivity

Entrega las pantallas y los componentes en archivos Kotlin separados.

MainActivity.kt solo debe configurar el tema y llamar al componente principal de navegación. No coloques allí las pantallas, tarjetas, formularios ni datos de estudiantes.

Organiza el código así:

MainActivity.kt.
navigation/Screen.kt.
navigation/AppNavigation.kt.
screens/LoginScreen.kt.
screens/HomeScreen.kt.
screens/ListScreen.kt.
screens/DetailScreen.kt.
screens/ProfileScreen.kt.
model/Alumno.kt.
data/AlumnosLocalData.kt.
ui/theme/ para colores, tipografía y tema.

Extrae los componentes visuales a components/, cada uno en su archivo:

AcademicTopBar.kt.
LoginCard.kt.
HomeOptionCard.kt.
StudentCard.kt.
StudentAvatar.kt.
AcademicInfoCard.kt.
ProfileHeader.kt.
ProfileInfoRow.kt.
LogoutAction.kt.

Define cada componente como una función @Composable independiente, con parámetros y callbacks. No los declares como funciones locales dentro de MainActivity, onCreate u otra pantalla.

Reutiliza componentes sin alterar las diferencias visuales. La salida del inicio debe mantenerse sin fondo y la del perfil debe conservar el fondo rosado.

## 12. Adaptación y límites

Usa dp y sp.
Respeta los insets del sistema sin duplicarlos.
Evita contenido oculto bajo las barras.
Mantén áreas táctiles de al menos 48 dp cuando corresponda.
No recortes nombres, carreras ni correos.
Permite desplazamiento si la altura disponible no alcanza.
Evita que el teclado cubra los campos.
Proporciona descripciones de accesibilidad para iconos interactivos.
Construye la interfaz con componentes reales, no con capturas usadas como fondo.

No agregues:

Registro de usuarios.
Recuperación real de contraseña.
Formularios de edición.
Buscadores o filtros.
Menús laterales.
Barra de navegación inferior.
Botones flotantes.
Estadísticas, cursos o calificaciones.
Chatbots.
Logos.
Animaciones decorativas.
Pantallas adicionales.
Textos explicativos dentro de la interfaz.

## 13. Comprobación y entrega

Antes de finalizar, comprueba:

1. La app comienza en Portal Académico.
2. Los campos permiten escribir y el ojo cambia la visibilidad.
3. El acceso con ambos campos completos abre el inicio.
4. El saludo muestra Carlos Junco.
5. El directorio contiene exactamente los cinco registros indicados.
6. Cada estudiante abre su propio expediente.
7. Carlos Junco y su correo aparecen correctamente en todas sus vistas.
8. Las flechas regresan al destino correspondiente.
9. Cerrar sesión limpia la navegación.
10. Los dos controles de salida permanecen abajo, como en las imágenes.
11. No existen superposiciones ni textos recortados.
12. Los colores, iconos y distribución coinciden con las referencias.
13. Los componentes están separados de MainActivity.
14. El proyecto compila.

Implementa la solución completa. Presenta cada archivo creado o modificado por separado, indicando su ruta y mostrando el código completo con sus imports. No entregues pseudocódigo, fragmentos incompletos ni secciones omitidas.

Indica qué comprobaciones realizaste realmente y si falta algún recurso. Si no puedes ejecutar la aplicación o revisar las pantallas en un emulador, dilo claramente.

---

## 📋 Requerimientos y Funcionalidades Implementadas
1. **Flujo de Autenticación (`LoginScreen`):** Formulario de inicio de sesión con navegación a Home.
2. **Menú Principal (`HomeScreen`):** Opciones para acceder al directorio de alumnos y perfil del usuario.
3. **Directorio de Alumnos (`ListScreen`):** Lista dinámica renderizada con `LazyColumn`.
4. **Expediente Académico (`DetailScreen`):** Vista detallada con recepción de parámetros de ruta.
5. **Configuración de Perfil (`ProfileScreen`):** Pantalla de perfil con opciones de configuración.
6. **Navegación Robusta (`AppNavigation` & `Screen`):** Manejo centralizado de rutas y eventos.

<img width="239" height="471" alt="image" src="https://github.com/user-attachments/assets/578a48f6-d361-4c3e-b326-fa4dbb54a859" />

<img width="256" height="493" alt="image" src="https://github.com/user-attachments/assets/269c15f8-e2cf-4ab4-b291-c75266b5152d" />

<img width="243" height="483" alt="image" src="https://github.com/user-attachments/assets/a5d7b6e6-b945-4e11-bbf2-c46b56698a41" />

<img width="267" height="467" alt="image" src="https://github.com/user-attachments/assets/35c3f998-4dfc-46e5-873e-ac7e0f67bf34" />

<img width="258" height="479" alt="image" src="https://github.com/user-attachments/assets/9b4b4529-5482-4da4-89c2-b130faf5bee4" />


