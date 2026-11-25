# Contexto de la aplicación — Gasolinera

## Qué es

Aplicación de consola en Java 21 para gestionar clientes y repostajes de una estación de servicio. Trabaja con persistencia CSV y JSON intercambiable y está en proceso de ampliación hacia MySQL mediante JDBC, conservando las funcionalidades y los datos anteriores.

## Dominio actual

- **`Cliente`**: id, nombre, teléfono y matrícula. Orden natural por nombre y después por id; `equals`/`hashCode` propios.
- **`PagoRepostaje`**: id, idCliente, fecha, importe, litros y combustible (`Combustible`). Orden por fecha descendente y luego id.
- **`Gasolinera` / `Ficha`**: composición fuerte 1:1. Se crean de forma atómica con `Gasolinera.darDeAlta(...)`; constructores privados, sin setter de ficha y dueño `final` en la ficha. No puede existir estación sin ficha ni ficha independiente.
- **`Surtidor` / `EstadoServicio`**: surtidor con estación asignada, código de equipo único, número visible, combustible y estado (`OPERATIVO`, `AVERIADO`, `EN_MANTENIMIENTO`).

## Operaciones y comportamiento

Menú de consola: alta de cliente (guarda en disco de inmediato), listado alineado con `printf`, búsqueda funcional por nombre/teléfono/matrícula sin distinción de mayúsculas, pago de repostaje vinculado a un cliente validado con Streams, consulta de pagos con unidades `€` y `L`, y salida.

`MigraCSVToJson` realiza la migración en el primer arranque con regla "todo o nada": valida los CSV en memoria y, si alguna línea está corrupta, no escribe nada. Los CSV originales no se modifican. En arranques posteriores se localiza la carpeta de trabajo dinámicamente y se carga el menú sin volver a preguntar.

El JSON se genera de forma artesanal: importes y litros con `Locale.US` (punto decimal, sin comillas) y fechas en `dd/MM/yyyy`.

## Arquitectura de persistencia

La interfaz `Almacenamiento` abstrae lectura y escritura; la implementan `AlmacenamientoCSV`, `AlmacenamientoJSON` y, en el estado actual del repositorio, `AlmacenamientoSQLite` (`jdbc:sqlite:`, ruta tomada de la variable de entorno `GASOLINERA_RUTA`, con tablas `clientes` y `pagos`). Los conversores `CSVConverter`, `JSONConverter`, `ClienteConverter` y `PagoConverter` son manuales y usan `Function<T, U>` para encadenarse en streams.

## Ampliación funcional en curso (Fase 1)

La aplicación debe incorporar MySQL/JDBC conviviendo con CSV y JSON, manteniendo separados modelo, lógica y almacenamiento. Las consultas y operaciones JDBC se dejan para una fase posterior. El dominio a cubrir se amplía con:

- **Estaciones y surtidores**: cada estación con una única ficha de facturación y al menos un surtidor; alta conjunta atómica, sin estaciones sin ficha ni retirada del último surtidor.
- **Personal**: empleados adscritos a una estación, con funciones por periodos (cajero, expendedor, responsable de tienda, jefe) que pueden simultanearse pero no solaparse para una misma función, y un supervisor como máximo, sin ciclos.
- **Clientes y tarjetas**: tarjetas de fidelización con número único, que pueden estar sin asignar; relación 1:1 opcional con el cliente.
- **Tienda**: productos envasados (unidades enteras) y a granel (kilogramos con precisión de pesada), con validación de cantidad y cálculo de importe según modalidad y precio vigente informado.
- **Proveedores y campañas**: acuerdos de suministro por producto y campañas con inscripción de clientes.
- **Ventas y pagos**: tickets con líneas que conservan el precio realmente cobrado, cliente opcional y cobro fraccionado en varios pagos.
- **Devoluciones**: parciales y sucesivas por línea pagada, calculadas al precio original, sin superar cantidad ni importe, y con indicación de aptitud para reventa.
- **Incidencias**: registradas por estación con responsable actual opcional.
- **Histórico de precios**: trigger en MySQL que registra el producto, precio anterior, nuevo y fecha/hora cuando cambia realmente el precio; no registra creación, mismo precio ni cambios de descripción, y se revierte con la transacción.

## Estructura de carpetas de interés

```text
RA2 - GasolineraSQL/
├── README.md                              Descripción, formato JSON, migración y uso de la app
├── Caso_practico_Gasolinera_MySQL_Fase_1.md  Requisitos funcionales de la ampliación
├── CONTEXT.md                             Este resumen
└── Gasolinera/
    ├── Gasolinera.iml                     Configuración del módulo de IntelliJ
    ├── src/
    │   ├── GasolineraMain.java            Punto de entrada
    │   ├── Menu.java                      Menú, detección de JSON y arranque de migración
    │   ├── OperacionesMenu.java           Lógica de las opciones y validaciones
    │   ├── MigraCSVToJson.java            Migración CSV → JSON atómica
    │   ├── Almacenamiento.java            Contrato de persistencia
    │   ├── AlmacenamientoCSV.java         Implementación CSV
    │   ├── AlmacenamientoJSON.java        Implementación JSON
    │   ├── AlmacenamientoSQLite.java      Implementación SQLite (estado actual)
    │   ├── CSVConverter.java              Contrato de conversión CSV
    │   ├── JSONConverter.java             Contrato de conversión JSON
    │   ├── ClienteConverter.java          Conversión de clientes
    │   ├── PagoConverter.java             Conversión de pagos
    │   ├── Cliente.java                   Modelo de cliente
    │   ├── PagoRepostaje.java             Modelo de pago
    │   ├── Combustible.java               Tipos de combustible
    │   ├── Gasolinera.java                Estación de servicio
    │   ├── Ficha.java                     Configuración de facturación
    │   ├── Surtidor.java                  Surtidor de la estación
    │   └── EstadoServicio.java            Estados del surtidor
    └── out/production/Gasolinera/         Clases compiladas del proyecto
```


