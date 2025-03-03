# PMDM Tarea-04
## Mulitimedia

La tarea tiene como objetivo crear una guia al usuario del juego **Spyro the Dragon**. Explicando las diferentes 
pantallas:

 ##### Personajes
- Se muestra layout por encima de la pantalla de aplicación y aparece un bocadillo informativo de los personajes en esta pantalla.
- Botón _salir_ de la guia con efecto de sonido
- Botón _siguiente_ para pasar a al layout guia de los Mundos de Spyro.
           
##### Mundos
- Igual que en la pantalla personajes, layout semitransparente con igual bocadillo informativo de los Mundos. A la vez por debajo en la aplicación se avanza a la pantalla Mundos mediante acción del BottonNavigation de forma programática para que el usuario se haga idea de lo que se le muestra en la guia.
- Botón _salir_ de la guia con efecto de sonido
- Botón _siguiente_ para pasar a al layout guia de los Coleccionables de Spyro.
           

##### Coleccionables
- Igual que en la pantalla personajes y mundos, layout semitransparente con bocadillo informativo. También por debajo en la aplicación se avanza a la pantalla Collectibles mediante acción del BottonNavigation de forma programática para que el usuario se haga idea de lo que se le muestra en la guia.
- Botón _salir_ de la guia con efecto de sonido
- Botón _siguiente_ para pasar a al layout de resumen de lo visto en la guia.
           

Para esta guia se crean principalmente 3 layouts, uno para cada pantalla mencionada. Estos layouts irán superpuestos
encima de la correspondiente pantalla y de forma semitransparente. En cada uno de estos layouts se añaden 2 botones, 
uno para pasar a la siguiente pantalla de la guia y otro para abandonarla e irse al juego de forma directa.


### Elementos que se usan en el código

#### __Transiciones:__ 
Para pasar de una pantalla de la guia a la siguiente.

#### __Animaciones:__ 
Para llamar la atención del usuario sobre elementos como son los botones del BottomNavigation.

#### __Drawables:__ 
Ya sean formas _shape_ en código xml o mediante imágenes "PNG" para dar un estilo diferente a botones.

#### __Efectos de sonido:__ en los botones de la guia.

#### __Mostrar videos:__ 
Mediante la técnica _Easter Egg_. Como elemento oculto y visualizarlo tras un número determianado de pulsaciones en la pantalla sobre un elemento del Recyclerview
  
#### __Canvas:__ 
Para dibujar animación, activada al hacer pulsación prolongada sobre el item del Recycleview "Spyro".


Si la guia ha sido visualizada ya, esta opción se guardará en *SharedPrefereces* para saltársela al arrancar de nuevo el juego en las siguientes ocasiones.


