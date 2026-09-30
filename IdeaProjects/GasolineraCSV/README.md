# GasolineraCSV
 
## Descripción
Este proyecto está desarrollado en Java y ha sido compilado y probado utilizando **JDK 21**.
 
## Requisitos
 
- Java Development Kit (JDK) 21 o superior.
- Terminal o consola de comandos.

## Compilación
 
Desde la raíz del proyecto, ejecutar:
 
```bash
javac -d bin src\*.java
```
 
Esto generará los archivos `.class` en el directorio `bin`.

## Ejecución
 
Una vez compilado, ejecutar:
 
```bash
java -cp bin Main
```

## Ubicación y formato de los ficheros

Por defecto, los ficheros se generarán en un directorio llamado ArchivosCSV, que debe estar en el directorio actual (de no existir o no encontrarse en el directorio actual se generará uno nuevo).

Cada fichero será un archivo con extensión .csv, y se creará uno para clientes y otro para pagos, llamándose Clientes y Pagos. Si los ficheros ya existen en el directorio ArchivosCSV no se generarán nuevos.

Se han incluido encabezados en los ficheros.

## Decisiones de diseño

Incluyendo al Main, el programa se divide en un total de 10 clases, enumeración (enum) y una interfaz. Todos los componentes se encuentran dentro del mismo paquete.

1- Cliente

### Identificación única mediante id

Se ha decidido que el atributo id sea el identificador único de cada cliente. Por ello, los métodos equals() y hashCode() utilizan exclusivamente este atributo para determinar si dos objetos representan al mismo cliente.

Ventajas:

-Evita duplicidades en colecciones como HashSet.
-Permite localizar clientes de forma eficiente.
-El nombre, teléfono o matrícula pueden modificarse sin afectar a la identidad del objeto.
-Ordenación natural mediante Comparable

### La clase implementa la interfaz Comparable<Cliente> para definir un criterio de ordenación natural.

El método compareTo() sigue dos niveles de comparación:

-Comparación alfabética del nombre (nombre) ignorando mayúsculas y minúsculas mediante compareToIgnoreCase().
-Si los nombres coinciden, se utiliza el id como criterio de desempate.

Con ello se consigue una ordenación estable y predecible cuando los objetos se almacenan en estructuras ordenadas como TreeSet o se ordenan mediante Collections.sort().


2- PagoRepostaje

### Identificación única mediante id

Cada pago de repostaje posee un identificador único (id). Por este motivo, los métodos equals() y hashCode() utilizan exclusivamente este atributo para determinar si dos objetos representan el mismo pago.

Ventajas:

-Evita registros duplicados en colecciones basadas en hash.
-Facilita la búsqueda y gestión de pagos.
-Permite modificar otros atributos sin afectar a la identidad del objeto.
-Relación con el cliente mediante idCliente

En lugar de almacenar una referencia directa a un objeto Cliente, la clase guarda únicamente el identificador del cliente (idCliente). Esta decisión reduce el acoplamiento entre clases y simplifica la persistencia y recuperación de datos.

### Ordenación natural mediante Comparable

La clase implementa la interfaz Comparable<PagoRepostaje> para definir una ordenación natural basada en la fecha del repostaje.

El criterio de ordenación es:

Fecha (fecha) en orden descendente, mostrando primero los repostajes más recientes.
En caso de empate, se utiliza el identificador (id) en orden descendente como criterio de desempate.

Esto resulta especialmente útil para mostrar historiales de repostajes en orden cronológico inverso.

### Uso de LocalDate

Se ha utilizado la clase LocalDate para representar la fecha del repostaje, ya que únicamente es necesario almacenar la fecha y no la hora. Esta solución proporciona una representación más clara y evita problemas relacionados con zonas horarias.

Uso de enumeración para el combustible

El atributo combustible utiliza el tipo enumerado Combustible, lo que garantiza que únicamente puedan asignarse valores válidos y predefinidos.

Ventajas:

-Evita errores de escritura.
-Mejora la legibilidad del código.
-Facilita futuras ampliaciones de tipos de combustible.

3- Menu

### Centralización de la interacción con el usuario

La clase Menu actúa como punto de entrada de la aplicación y centraliza toda la interacción con el usuario a través de la consola.

Su responsabilidad principal es:

-Mostrar las opciones disponibles.
-Solicitar la opción elegida.
-Invocar la funcionalidad correspondiente.
-Gestionar la persistencia de datos al finalizar el programa.

De esta forma se separa la lógica de presentación de la lógica de negocio.

Uso de un bucle de ejecución continuo

Se ha utilizado una estructura do-while para mantener el menú activo hasta que el usuario seleccione la opción de salida (0).

Esto permite realizar múltiples operaciones durante la misma ejecución sin necesidad de reiniciar el programa.

### Separación entre menú y lógica de negocio

Las operaciones reales no se implementan directamente en la clase Menu, sino que se delegan a métodos estáticos de la clase Operaciones.

Ventajas:

-Menor acoplamiento.
-Código más organizado.
-Mayor facilidad de mantenimiento.
-Posibilidad de reutilizar la lógica de negocio desde otras interfaces.
-Persistencia mediante repositorios

Se emplean las clases RepoCliente y RepoPagos para gestionar la lectura y escritura de los archivos CSV.

Esta decisión permite aislar la gestión de datos del resto de la aplicación y aplicar el principio de responsabilidad única.

Al iniciar el programa:

-Se cargan los clientes desde el repositorio.
-Se cargan los pagos desde el repositorio.

Al finalizar:

Se guardan los cambios realizados en ambas colecciones.
Uso de colecciones en memoria

Tras la carga inicial, los datos se almacenan en listas (List<Cliente> y List<PagoRepostaje>).

Esto evita acceder continuamente a los archivos CSV y mejora el rendimiento durante la ejecución de la aplicación.

### Validación robusta de la entrada del usuario

La selección de opciones del menú se realiza mediante el método privado comprobarOpcion().

Este método valida que:

-El dato introducido sea numérico.
-El valor esté comprendido entre las opciones disponibles del menú.

Para ello se utilizan excepciones:

-InputMismatchException: cuando el usuario introduce un dato no numérico.
-IllegalArgumentException: cuando el número está fuera del rango permitido.

### Encapsulación de la validación

La validación de la opción elegida se ha aislado en un método privado independiente (comprobarOpcion), evitando duplicar código y mejorando la legibilidad del método principal mostrarMenu().

### Gestión de recursos

Se utiliza un único objeto Scanner durante toda la ejecución del programa y se cierra al finalizar mediante sc.close();

Con ello se liberan correctamente los recursos asociados a la entrada estándar.

### Creación automática de los archivos de datos

Los repositorios están diseñados para crear automáticamente el directorio y los archivos CSV necesarios en caso de que no existan.

Esto facilita la primera ejecución de la aplicación y evita configuraciones previas por parte del usuario.

4- CSVConverter

### Uso de una interfaz genérica

La interfaz se ha definido utilizando genéricos (<T>) para permitir que cualquier tipo de objeto pueda convertirse a formato CSV y reconstruirse posteriormente sin necesidad de crear interfaces específicas para cada clase.

Ventajas:

-Reutilización de código.
-Mayor flexibilidad.
-Independencia respecto a las clases de dominio.
-Facilita la incorporación de nuevos tipos de objetos en el futuro.
-Separación de responsabilidades

5- Repositorio

### Uso de una clase abstracta genérica

La clase se ha diseñado como una clase abstracta genérica. Esto permite reutilizar la misma estructura de persistencia para distintos tipos de objetos sin duplicar código.


Ventajas:

-Reutilización.
-Mayor mantenibilidad.
-Facilita la incorporación de nuevas entidades.
-Reduce la duplicación de código.

### Aplicación de herencia para repositorios específicos

La clase define el comportamiento común a todos los repositorios, mientras que las clases hijas implementan los detalles concretos para cada tipo de dato.

De esta forma se comparte la funcionalidad general y cada repositorio se especializa en su entidad correspondiente.

### Creación automática del directorio de almacenamiento

El constructor recibe la ruta del directorio donde se almacenarán los archivos y garantiza su existencia mediante Files.createDirectories(this.directorio). Esta decisión evita configuraciones manuales previas y permite que la aplicación funcione correctamente desde la primera ejecución. Files.createDirectories(this.directorio) solo se ejecutará si el directorio en cuestión no existe.

### Abstracción de las operaciones de persistencia

La clase define los métodos fundamentales de persistencia como abstractos:

protected abstract void guardarCSV(List<T> lista);
protected abstract List<T> listarCSV();

Con ello se obliga a cada repositorio concreto a proporcionar su propia implementación para guardar y recuperar datos.

### Preparación para futuras ampliaciones

Además de las operaciones básicas de lectura y escritura, se define el método abstracto:

protected abstract void generarCSV(String directorio, String archivo);

Este diseño permite que cada repositorio pueda especificar cómo se genera inicialmente su archivo de almacenamiento.

Asimismo, la arquitectura facilita incorporar en el futuro otros mecanismos de persistencia (XML, JSON o bases de datos) creando nuevas implementaciones sin modificar la estructura general.

### Principio de responsabilidad única

La responsabilidad de esta clase se limita exclusivamente a proporcionar la infraestructura común de acceso a los datos.

Las clases de negocio (Cliente, PagoRepostaje, etc.) no necesitan conocer cómo se almacenan los datos, mientras que los repositorios especializados se encargan únicamente de la persistencia.

### Gestión de errores de entrada y salida

La creación del directorio se encuentra protegida mediante un bloque try-catch que captura posibles excepciones de tipo IOException.

De esta forma se evita que un fallo en el sistema de archivos provoque la terminación inesperada de la aplicación y se informa al usuario del problema detectado.

### Desacoplamiento entre almacenamiento y dominio

El uso de genéricos permite que la lógica de persistencia sea independiente de los modelos de negocio.

Gracias a ello, cualquier nueva entidad del sistema puede disponer de su propio repositorio simplemente heredando de Repositorio<T> e implementando los métodos necesarios, manteniendo una arquitectura flexible y escalable.

6- RepoPagos

### Especialización del repositorio genérico

La clase RepoPagos hereda de Repositorio<PagoRepostaje>

De esta forma reutiliza la infraestructura común de persistencia definida en la clase abstracta Repositorio y proporciona una implementación específica para la gestión de los pagos de repostaje.

### Uso de cabeceras

Tanto en esta clase como en RepoClientes, el método guardarCSV(List<T> lista) agrega automáticamente las cabeceras al escribir en el documento.

### Conversión desacoplada mediante convertidores

La transformación entre objetos y líneas CSV no se realiza directamente en el repositorio, sino mediante la clase PagoRepostajeConverter.

Esta decisión separa la lógica de persistencia de la lógica de conversión.

Ventajas:

-Menor acoplamiento.
-Mayor reutilización.
-Código más mantenible.
-Facilita cambios futuros en el formato de almacenamiento.

### Gestión automática de recursos

La lectura y escritura se realizan mediante bloques try-with-resources. Esto garantiza el cierre automático de los flujos de entrada y salida, incluso cuando se produce una excepción.

### Encapsulación de la ruta del archivo

La ubicación física del fichero se almacena en el atributo: 

private Path archivoPagos;

De esta forma la gestión de rutas queda centralizada dentro del repositorio y no es necesario que otras clases conozcan dónde se encuentra el archivo.

### Gestión de excepciones de entrada y salida

Las operaciones de acceso al sistema de archivos están protegidas mediante bloques try-catch para capturar posibles excepciones de tipo IOException.

Esto permite informar al usuario de los errores detectados sin provocar la finalización inesperada de la aplicación.

### Separación entre dominio y persistencia

Los objetos PagoRepostaje no contienen información sobre cómo se almacenan o recuperan sus datos.

Toda la responsabilidad de persistencia se concentra en RepoPagos, siguiendo el principio de responsabilidad única y manteniendo desacopladas las capas de dominio y almacenamiento.

7- RepoCliente

RepoCliente es el equivalente de RepoPagos para la entidad Cliente.

8- Operaciones

### Centralización de la lógica de negocio

La clase Operaciones se ha creado para agrupar toda la lógica asociada a las distintas opciones del menú.

De esta manera se evita sobrecargar la clase Menu con responsabilidades adicionales, manteniendo separadas la interfaz de usuario y la lógica de negocio.


### Uso exclusivo de métodos estáticos

Todos los métodos de la clase son estáticos.

Esto se debe a que la clase no necesita almacenar estado propio ni mantener información entre llamadas.

Por tanto, no es necesario crear instancias de Operaciones para ejecutar sus funcionalidades.

Ventajas:

-Menor consumo de memoria.
-Uso más sencillo desde otras clases.
-Refleja claramente que la clase funciona como un conjunto de utilidades de negocio.

### Programación defensiva

Todos los datos introducidos por el usuario son validados antes de procesarse.

Se comprueba, entre otros aspectos:

-Campos obligatorios vacíos.
-Formatos numéricos incorrectos.
-Fechas inválidas.
-Valores negativos.
-Identificadores incorrectos.
-Tipos de combustible inexistentes.

Con ello se aumenta la robustez de la aplicación y se evita almacenar información inconsistente.

### Búsqueda flexible de clientes

La funcionalidad de búsqueda permite localizar clientes utilizando coincidencias parciales sobre:

-Nombre.
-Teléfono.
-Matrícula.

Para ello se utiliza contains() e ignorando diferencias entre mayúsculas y minúsculas mediante toUpperCase(). 

Esta solución ofrece una experiencia más cómoda para el usuario al no exigir coincidencias exactas.

### Garantía de unicidad de matrículas

Durante el alta de clientes se verifica que la matrícula no exista previamente:

if(c.getMatricula().equalsIgnoreCase(matricula))

Esto evita registrar múltiples clientes asociados a la misma matrícula.


### Ordenación mediante el criterio natural de las entidades

Antes de mostrar los resultados se utiliza:

sort(null)

De esta forma la ordenación queda delegada en la implementación de Comparable de cada entidad.

Clientes

-Nombre ascendente.
-ID como criterio de desempate.

Pagos

-Fecha descendente.
-ID descendente como criterio de desempate.


### Uso de enumeraciones para los combustibles

Los tipos de combustible se validan mediante:

Combustible.valueOf(...)

Esta decisión garantiza que únicamente puedan registrarse combustibles definidos previamente en la enumeración Combustible.

### Admisión de distintos formatos decimales

Para facilitar la introducción de datos, los importes y litros aceptan tanto punto como coma decimal:

line.replace(",", ".")

Esto mejora la usabilidad para usuarios acostumbrados a formatos numéricos diferentes.

### Uso de fechas modernas de Java

Las fechas se gestionan mediante:

LocalDate
DateTimeFormatter

lo que proporciona una gestión más segura y legible que las antiguas clases de fecha de Java.

Además, si el usuario deja el campo vacío durante el registro de un pago, se utiliza automáticamente la fecha actual.

### Gestión de operaciones sobre colecciones en memoria

La clase trabaja directamente sobre las listas cargadas por los repositorios:

List<Cliente>
List<PagoRepostaje>

Esto permite realizar todas las modificaciones en memoria y persistir los cambios únicamente cuando el usuario abandona la aplicación.

### Identificadores autogenerados

Los identificadores de clientes y pagos se generan automáticamente mediante:

clientes.size() + 1
pagos.size() + 1

Esta decisión simplifica la creación de registros y evita que el usuario tenga que gestionar manualmente los IDs.

### Retroalimentación constante al usuario

Todas las operaciones muestran mensajes informativos sobre su resultado:

-Altas realizadas correctamente.
-Pagos registrados.
-Errores de validación.
-Ausencia de resultados.
-Operaciones canceladas.

Esto mejora la experiencia de uso y facilita la detección de errores durante la interacción con la aplicación.


9- Combustibles

### Uso de una enumeración para representar los combustibles

Los tipos de combustible disponibles se han definido mediante una enumeración (enum).

Esta decisión garantiza que únicamente puedan utilizarse valores válidos y previamente definidos por la aplicación.

### Integridad de los datos

Al utilizar una enumeración, se evita que el usuario o el programador pueda registrar combustibles inexistentes o con errores tipográficos.


### Mejora de la mantenibilidad

Si en el futuro la gasolinera incorpora nuevos combustibles, únicamente será necesario añadir una nueva constante a la enumeración sin modificar el resto de la lógica de la aplicación..

10- ClienteConverter

### Implementación de la interfaz CSVConverter

La clase implementa la interfaz genérica CSVConverter<Cliente> lo que la especializa en la conversión entre objetos Cliente y su representación en formato CSV.

Esta decisión permite reutilizar una estructura común para todos los convertidores de la aplicación.


### Separación entre conversión y persistencia

La responsabilidad de esta clase se limita exclusivamente a transformar datos entre dos formatos:

Objeto Cliente → línea CSV.
Línea CSV → objeto Cliente.

La lectura y escritura de archivos se delega al repositorio (RepoCliente), respetando el principio de responsabilidad única.

### Conversión de objetos a CSV

El método toCSV(Cliente cliente, String separador) genera una representación textual del cliente utilizando el separador indicado que permite almacenar los datos de forma sencilla y compatible con el formato CSV.

### Reconstrucción de objetos desde CSV

El método fromCSV(String line, String separador) permite reconstruir un objeto Cliente a partir de una línea leída del archivo.


Para ello:

-Divide la línea utilizando el separador especificado.
-Convierte los datos al tipo correspondiente.
-Crea una nueva instancia de Cliente.

### Uso de separadores parametrizados

Aunque actualmente se utiliza la coma (",") como separador, el diseño permite utilizar cualquier otro carácter. 

Esto aumenta la flexibilidad de la solución y facilita futuros cambios de formato.

### Patrón de adaptación de datos

La clase actúa como un adaptador entre el modelo de dominio (Cliente) y el formato de almacenamiento (CSV).

De esta forma:

El objeto Cliente no necesita conocer el formato CSV.
El repositorio no necesita conocer la estructura interna del objeto.
La lógica de conversión queda centralizada en una única clase especializada.

### Facilita el mantenimiento y la extensibilidad

Si en el futuro cambia la estructura del archivo CSV o se añaden nuevos atributos al cliente, únicamente será necesario modificar esta clase sin afectar al resto del sistema.

Esto reduce el impacto de los cambios y mejora la mantenibilidad de la aplicación.

11- PagoRepostajeConverter

Es el equivalente de ClienteConverter para PagosRepostaje.

12- Main

### Delegación de responsabilidades

La clase no contiene lógica de negocio ni lógica de presentación.

Su única función es crear una instancia de la clase Menu y transferirle el control de la aplicación:

Menu menu = new Menu();
menu.mostrarMenu();

Esta decisión mantiene un diseño limpio y favorece la separación de responsabilidades.

### Bajo acoplamiento

Main únicamente conoce la existencia de la clase Menu.

No necesita conocer:

Cómo se gestionan los clientes.
Cómo se registran los pagos.
Cómo funciona la persistencia.
Cómo se realizan las validaciones.

Esto reduce el acoplamiento entre componentes y mejora la mantenibilidad de la aplicación.



### Facilita futuras ampliaciones

Al mantener el punto de entrada aislado, resulta sencillo incorporar futuras tareas de inicialización, como:

Cargar configuraciones.
Inicializar una base de datos.
Registrar logs.
Configurar servicios externos.

sin modificar la lógica del resto del sistema.


### Principio de responsabilidad única

La responsabilidad de Main se limita exclusivamente a arrancar la aplicación.

Toda la lógica funcional queda delegada a las clases especializadas (Menu, Operaciones, repositorios y entidades), manteniendo una arquitectura más organizada y fácil de mantener.


---
Autora: Carolina Barrameda Domínguez