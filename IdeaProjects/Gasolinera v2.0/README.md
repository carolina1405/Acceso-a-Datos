# Gasolinera v2.0

## Descripción

Este proyecto es una aplicación de consola desarrollada en Java para la gestión de clientes y repostajes en una estación de servicio. Implementa una arquitectura modular desacoplada mediante una interfaz `Almacenamiento` con persistencia dual en archivos **CSV** y **JSON**, optimizada mediante el uso de programación funcional (Java Streams) e interfaces funcionales (`Function<T, U>`).

Cuenta con una clase traductora `MigraCSVToJson` que automatiza la migración de datos bajo una estricta regla transaccional de "Todo o Nada" en el primer arranque del sistema.

## Requisitos

- Java Development Kit (JDK) 21 o superior.
- Terminal o consola de comandos.

## Estructura del Formato JSON

El almacenamiento jerárquico se genera de forma artesanal respetando las tabulaciones anidadas requeridas:

```json
{
	[
		{"id": 1,"nombre": "Ana López","telefono": "600123456","matricula": "1234ABC"},
		{"id": 2,"nombre": "Óscar López","telefono": "654321098","matricula": "1234CBC"}
	]
}
```

*Nota: Los importes y litros se almacenan estrictamente con puntos decimales sin comillas mediante el estándar `java.util.Locale.US`, mientras que las fechas guardan el formato español `dd/MM/yyyy`.*

## Procedimiento de Migración

1. **Exploración Inicial:** En cada arranque, el programa escanea el directorio en busca de archivos `.json` existentes.
2. **Intercepción:** Si no se localiza ningún archivo JSON (primer arranque), se pregunta al usuario: `¿Hay ficheros .csv que migrar (S/N)?: `.
3. **Validación Atómica:** Si se responde `S`, se solicitan las rutas. Los datos se cargan y validan en la memoria RAM. Si una sola línea del CSV contiene datos corruptos, la migración se cancela con un mensaje de error y el destino permanece intacto (sin generar archivos vacíos ni parciales).
4. **Arranques Posteriores:** Si la migración se completa o ya existían datos, el programa se auto-configura localizando la carpeta de trabajo dinámicamente y carga el menú directo sin volver a preguntar.

## Compilación

Desde la raíz del proyecto, ejecutar:

```bash
javac -d bin src\*.java
```

Esto generará los archivos `.class` en el directorio `bin`.

## Ejecución

Una vez compilado, ejecutar el siguiente comando (forzando la codificación universal para asegurar la correcta visualización de tildes, eñes y el símbolo del euro `€`):

```bash
java -Dfile.encoding=UTF-8 -cp bin Gasolinera
```

## Guía de Operaciones del Menú

* **1. Dar de alta un cliente:** Añade un usuario y guarda los cambios en el disco inmediatamente después de la operación.
* **2. Listar clientes:** Muestra los registros alineados geométricamente por columnas mediante `printf`.
* **3. Buscar clientes:** Filtro funcional avanzado por nombre, teléfono o matrícula (Ignora mayúsculas/minúsculas).
* **4. Procesar un pago de repostaje:** Vincula un ticket de combustible a un cliente existente validado mediante Streams.
* **5. Consultar pagos:** Muestra el historial completo de repostajes formateado con sus unidades de medida (`€` y `L`).
* **0. Salir:** Finaliza la ejecución del programa de forma segura.

## Dependencias

Este proyecto no tiene dependencias de librerías externas.

Dependencias nativas del sistema: 

- java.base / java.nio.file: Encargado de la gestión moderna de rutas, directorios y escritura atómica en el disco duro (Path, Files)
- java.util.stream: Utilizado para el procesamiento funcional de los datos en las lecturas, búsquedas y escrituras lineales (Stream, Collectors)
- java.util.function: Responsable del acoplamiento y optimización de tus conversores mediante funciones encadenables (Function<T, U>)
- java.time: Utilizado para el manejo estructurado y formateo de los campos temporales (LocalDate, DateTimeFormatter)

## Detalles de la aplicación

### Interfaz Almacenamiento

Interfaz encargada de definir los métodos de escritura/lectura para cada objeto dependiendo del formato del archivo. La implementarán las clases AlmacenamientoCSV y AlmacenamientoJSON.

### AlmacenamientoCSV

Clase encargada de gestionar la lectura/escritura de clientes y pagos en archivos CSV. Implementa la interfaz Almacenamiento. En caso de no existir archivos CSV con los nombres y el directorio pasados como parámetros de su constructor los crea automáticamente. 

En ella se realizan las conversiones de objetos a formato CSV mediante instancias de las clases convertidoras.

###  AlmacenamientoJSON

Es la equivalente de AlmacenamientoCSV pero para formato JSON.

### Interfaz CSVConverter

Interfaz funcional que define los métodos para convertir a CSV o reconvertir a un objeto. Mediante la implementación de Function<T, U> los métodos podrán concatenarse en streams.

### Interfaz JSONConverter

Equivalente de CSVConverter para formato JSON.

### ClienteConverter

Clase encargada de implementar los métodos de la interfaz CSVConverter. Los métodos están pensados para convertir de CSV a objeto de la clase Cliente y viceversa de forma manual, sin dependencias externas.

### PagoConverter

Equivalente de la clase ClienteConverter para los pagos. Implemena la interfaz JSONConverter.

### Combustible

Enumeración para definir los tipos de combustibles disponibles en la gasolinera. En caso de ofrecerse nuevos combustibles, solo hay que añadirlos aquí. Esto evita que se introduzcan valores erróneos.

### Cliente

Clase que define los objetos de tipo Cliente. Los clientes poseen un id, nombre, teléfono y matrícula como atributos. Además implementa la interfaz Comparable, la cual establece un criterio de ordenación natural basado en el nombre (ascendente), y luego en el id (ascendente). 

Además, también implementa los métodos equals y hashCode para establecer los criterios que determinan la diferencia entre 2 clientes.

La clase cuenta con getters/setters para todos los atributos.

### PagoRepostaje

Clase que define los objetos de tipo PagoRepostaje. Los pagos poseen un id, idCliente, fecha, importe, litros y combustible como atributos. Además implementa la interfaz Comparable, la cual establece un criterio de ordenación natural basado en la fecha (descendente), y luego en el id (descendente). 

Además, también implementa los métodos equals y hashCode para establecer los criterios que determinan la diferencia entre 2 pagos.

La clase cuenta con getters/setters para todos los atributos.

### Menu

Clase encargada de mostrar el menú y solicitar al usuario que elija una de las opciones que ofrece. Antes de mostrarlo, se lleva a cabo una exploración en el directorio actual para comprobar que no existan archivos .json ya creados. Si no los encuentra (lo normal si se trata del primer arranque) se le pregunta al usuario si desea migrar los datos existentes en archivos CSV a JSON. Si el usuario responde "S" se piden el directorio de origen y de destino y se inicia la migración. 

En caso de que el usuario responda "N" o la migración falle (se informará al usuario del error antes de pasar a mostrar el menú y no actuará como si no hubiese fallado), simplemente pasará directamente a mostrar el menú, no sin antes crear un nuevo directorio con archivos .json con nombres pasados por defecto. Para que en arranques posteriores se pueda acceder a las rutas de archivos .json ya existentes, se vuelve a realizar una exploración para buscar esos archivos. En caso de que no existan o no cumplan con los requisitos hay nombres por defectos ya creados (como he mencionado antes) para el directorio y los archivos y poder continuar con la ejecución. 

En cualquier caso se creará un objeto de tipo Almacenamiento para abstraer los métodos implementados por cada tipo de almacenamiento.

En el menú se abre y cierra el Scanner que se utilizará a lo largo del programa.

### OperacionesMenu

Clase encargada de implementar la lógica detrás de cada opción del menú. Esta clase utiliza métodos estáticos ya que no hay necesidad de crear una instancia de esta clase pues solo contiene métodos. También alberga métodos privados para controlar los datos introducidos en cada caso, evitando que el usuario rompa el programa.

### MigraCSVToJson

Clase encargada de ofrecer un único método estático encargado de realizar la migración oficialmente de formato CSV a JSON. Está diseñada para no modificar en lo absoluto los datos existentes en los archivos .csv y en caso de fallar en algún momento la migración se detendrá inmediatamente mostrando la causa del error. 

Suponiendo que se complete con éxito se informará al usuario de los clientes y pagos migrados, y en todo momento se le informará del estado del proceso.

### Gasolinera

Es el main de la app. Su única tarea es llamar al menú.

Autora: Carolina Barrameda Domínguez




















