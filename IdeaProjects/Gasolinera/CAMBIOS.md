# Cambios en la estructura básica de la app original

La versión original de la aplicación (la cual utilizaba solo CSV), resultó ser bastante más complicada de lo que pensaba para adaptarla a cualquier otro tipo de archivo, además de ser bastante caótica. 

Por ello, he decidido aprender a usar streams para sustituir la mayoría de bucles for-each que había utilizado originalmente.

Ahora las clases repositorias ya no existen. En su lugar, he creado una interfaz Almacenamiento, que la implementarán las clases de almacenamiento, como AlmacenamientoCSV y AlmacenamientoJSON, para realizar cada una a su manera la lectura y escritura en el formato en el que se especialicen. También generarán los directorios y documentos necesarios en caso de no existir. 

Las interfaces de conversión así como las clases que las implementan se han mantenido, pero ahora funcionan de una manera más óptima, pues he aprendido a utilizar Function<T, U>, lo cual me permite generar funciones que puedo encadenar luego en los flujos de streams y ahorrarme trabajo.

El menú, salvo por los ajustes para la migración y los ajustes que exigía la práctica2, se mantiene exactamente igual, solo que ahora solo hay que crear un objeto almacenamiento (antes había que crear 2 objetos repositorios), que al ser del tipo de la interfaz Almacenamiento, me permite abstraer los métodos de cada tipo de almacenamiento.

Las clases Cliente, PagoRepostaje, el main (ahora Gasolinera) y el enumerado Combustible siguen igual que en la versión anterior, y las clases ClienteConverter y PagoConverter siguen la misma lógica de antes (pero con modificaciones de cara a la ejecución de los métodos de conversión y los atributos implicados).

Los métodos de la clase Operaciones (ahora OperacionesMenu) han sido modificados casi por completo en algunos casos para trabajar con streams (simplificándolos y a la vez optimizándolos), corregir errores que se han ido descubriendo con las pruebas, añadir algunas comprobaciones nuevas y adaptarlos a la nueva estructura global.


# Cambios nuevos relacionados con la Práctica2-JSON

## 1- Implementación de una lógica en el menú que permita revisar todos los archivos del directorio actual en busca de la existencia previa de ficheros.json.

Ahora, en cada arranque la aplicación buscará en el directorio actual cualquier archivo que contenga datos.json, y en caso de no existir ninguno (habitual si es el primer arranque), se le preguntará al usuario si desea llevar a cabo la migración de los datos de la práctica 1 en CSV a JSON.

En caso afirmativo, se ejecutará un método estático de la nueva clase MigraCSVToJson que llevará a cabo la migración (esto lo explicaré con más detalle más adelante).

Tanto si la migración tiene éxito como si no (o simplemente el usuario ha decidido no llevarla a cabo), se comenzará una segunda exploración igual que la anterior para recopilar las rutas de los directorios que puedan tener archivos.json, guardándolos en una lista al final.

Se establecen un directorio y nombres de archivos por defecto en caso de no existir ficheros .json que utilizar.

Luego se verifica si la lista está vacía o no. En caso de estarlo, simplemente se creará un almacenamiento para JSON usando las rutas por defecto. Si se han encontrado archivos.json, se toma el directorio de una de las rutas (debería ser el mismo para ambas si los archivos.json cumplen con el requisito de estar guardados en el mismo directorio), y posteriormente se comprueba si ambas rutas contienen los ficheros.json que tengan nombres como "pago" o "cliente" para evitar que tome ficheros que contengan otros datos. A la vez, también se evalúa si ambos ficheros están dentro del mismo directorio. En caso de estarlo, se cambiará el nombre por defecto del directorio, y si no lo están, se mantiene. Los nombres de los ficheros no hace falta, en principio, llevar el mismo control sobre ellos que sobre el directorio, ya que se evalúa de antemano que los nombres sean del tipo "pagos" o "clientes".

En cualquier caso, con el resultado de la operación anterior sse creará un almacenamiento al final con las rutas resultantes de la exploración. Luego el programa seguirá como antes de la modificación para la migración, leyendo y guardando los datos.json en listas para manejarlos.

Después de mostrar el menú y hacer cualquier operación, los cambios se guardarán inmediatamente después de realizar dicha operación (en lugar de guardarse solo antes de salir).

## 2- Nueva interfaz convertidora para formato JSON

Antes había tenido pensado utilizar la librería Gson, pero, debido a varias complicaciones durante su implementación en la versión original de la app y también a la posibilidad de que se pueda complicar su activación en otro dispositivo, he decidido que lo mejor es prescindir de ella por el momento.

He creado una interfaz JSON converter para que la implementen las clases convertidoras de Cliente y PagoRepostaje. Los métodos devuelven un Function<T, U> (del tipo del objeto con el que se trabajo y String) para poder concatenarlos en streams durante la conversión. 

En el caso de lo pagos he descubierto una errata debido al formateador de fechas durante el guardado. Antes al guardar formateado a JSON un pago, el formateador convertía el separador decimal en una "," en lugar de un ".", corrompiendo la estructura JSON y propiciando errores a la hora de recuperar los datos, por lo que para corregirlo tuve que cambiar al estándar anglosajón utilizando java.util.Locale.US para que los decimales se guarden siempre utilizando puntos sin romper el programa.

## 3- Nueva clase AlmacenamintoJSON

Su función es la misma que AlmacenamientoCSV, solo que ahora trabajamos con JSON. He de añadir que tanto la clase AlmacenamientoCSV como AlmacenamientoJSON fueron modificadas para admitir rutas de forma dinámica, por lo que ahora sus constructores recibirán como parámetros el nombre del directorio y los de los ficheros. Además para que no generen ficheros nuevos en blanco, se han modificado para que en la versión CSV se genere una cabecera y en la versión JSON unos {[]}, para que quede más profesional.

## 4- Nueva clase MigraCSVToJson

Este es uno de los cambios más grandes. Esta clase cuenta con un único método estático llamado: 

ejecutarMigracion(String rutaOrigen, String rutaDestino);

que recibe como parámetros los nombres de los directorios de origen y destino para la migración. Este método a grandes rasgos lo que hace es comprobar si:

- Los ficheros existen.
- El directorio de origen contiene los ficheros.csv requeridos.
- El directorio de destino no contiene los ficheros.json que se generarán(es una capa extra de seguridad, pero realmente la comprobación de si el directorio está vacío se hace ya directamente en el menú). 

Si fallase algunas de estas contemplaciones, entonces la migración quedará cancelada. En caso de no fallar, se crea un objeto de almacenamiento en CSV, se recopilan en listas todos los clientes y pagos, se crea un objeto de almacenamiento en JSON y se guardan los datos de esas listas en ficheros.json. Al final se informa al usuario del total de clientes y pagos migrados, mostrándose un mensaje al final si la migración ha sido un éxito. Esta parte está metida dentro de un try-catch, por lo que si falla la migración se interrumpe. En ningún momento se modifican lo ficheros.csv y los ficheros.json solo se generan si se ha podido recopilar la información de los CSV.

Al final esta estructura garantiza que la migración únicamente se efectúe si todos los requisitos han sido aprobados. 


Autora: Carolina Barrameda Domínguez






