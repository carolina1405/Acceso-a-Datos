# PAUTAS DE DISEÑO



## RECURSOS



* Todos los recursos que se abran (scanners, ficheros, etc) deben cerrarse. Siempre que sea posible emplea try-with-resources.



## CONTROL DE EXCEPCIONES



* Todos los bloques de código que puedan generar excepciones deben tratarse dentro de un try-catch.
* Debes tratar cada una de las excepciones que se puedan generar por separado. No está permitido hacer cosas como esta:


```
try{

    //Instrucciones que pueden lanzar IllegalArgumentException e InputMismatchException.

}catch(RuntimeException e){

    //Mensaje de error

}
```


## COMENTARIOS



* Realiza comentarios que expliquen el propósito de todas las clases y de los métodos que hay dentro de cada clase.
* Las secciones de código que sean difíciles de entender deben quedar explicadas a través de comentarios.
* Utiliza comentarios para separar las secciones de código facilitando la legibilidad y el mantenimiento posterior (por ejemplo, dentro de una clase separar los atributos, constructores, métodos, getters/setters, etc. mediante comentarios).



## PROGRAMACIÓN FUNCIONAL



* Prioriza la programación funcional sobre los bucles iterativos siempre que sea posible.

