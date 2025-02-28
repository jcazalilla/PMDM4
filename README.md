# PMDM Tarea-04
## Mulitimedia

La tarea tiene como objetivo crear una guia al usuario del juego **Spyro the Dragon**. Explicando las diferentes 

pantallas:

     - Personajes
     - Mundos
     - Coleccionables

Para esta guia se crean principalmente 3 layouts, uno para cada pantalla mencionada. Estos layouts irán superpuestos

encima de la correspondiente pantalla y de forma semitransparente. En cada uno de estos layouts se añaden 2 botones, 

uno para pasar a la siguiente pantalla de la guia y otro para abandonarla e irse al juego de forma directa.


### Elementos que se usan en el código

· __Transiciones:__ para pasar de una pantalla de la guia a la siguiente.

· __Animaciones:__ para llamar la atención del usuario sobre elementos como son los botones del BottomNavigation.

· __Drawables:__ ya sean formas _shape_ en código xml o mediante imágenes "png" para dar un estilo diferente a botones.

· __Efectos de sonido:__ en los botones de la guia.

· __Mostrar videos:__ mediante la técnica _Easter Egg_. Como elemento oculto y visualizarlo tras un número determianado de pulsaciones.

en la pantalla sobre un elemento del Recyclerview

· __Canvas:__ para dibujar animación, activada al hacer pulsación prolongada sobre el item del Recycleview "Spyro".




Si la guia ha sido visualizada ya, esta opción se guardará en *SharedPrefereces* para saltársela al arrancar de nuevo el juego en las

siguientes veces.

