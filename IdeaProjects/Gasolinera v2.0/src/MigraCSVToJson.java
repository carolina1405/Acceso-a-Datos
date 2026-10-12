import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MigraCSVToJson {

    //Método encargado de realizar la migración de los archivos
    public static void ejecutarMigracion(String rutaOrigen, String rutaDestino){
        //Convertimos en rutas los strings pasados como parámetros.
        Path dirOrigen = Path.of(rutaOrigen);
        Path dirDestino = Path.of(rutaDestino);

        //Creamos las rutas de los ficheros
        Path archivoClientesCSV = dirOrigen.resolve("clientes.csv");
        Path archivoPagosCSV = dirOrigen.resolve("pagos.csv");

        Path archivoClientesJSON = dirDestino.resolve("clientes.json");
        Path archivoPagosJSON = dirDestino.resolve("pagos.json");

        //Comprobamos por seguridad que los ficheros .json en cuestión no existan previamente.
        if(Files.exists(archivoClientesJSON) || Files.exists(archivoPagosJSON)){
            System.out.println("=============================================================================");
            System.out.println("Migración cancelada: el destino ya contiene datos.json.");
            System.out.println("Selecciona un destino libre. No se han modificado los ficheros existentes.");
            System.out.println("=============================================================================");
        }else if(Files.notExists(archivoClientesCSV) || Files.notExists(archivoPagosCSV)){ //Comprobamos que los ficheros .csv requeridos existan.
            System.out.println("=============================================================================");
            System.out.println("ERROR: Faltan ficheros de origen requeridos en el directorio: " + rutaOrigen);
            System.out.println("=============================================================================");
        }else{
            System.out.println("Validación completada.");
            try{
                //Creamos un almacenamiento para CSV y leemos los archivos.
                Almacenamiento almacenamientoCSV = new AlmacenamientoCSV(dirOrigen.toString(), archivoClientesCSV.getFileName().toString(), archivoPagosCSV.getFileName().toString());
                List<Cliente> clientesCSV = almacenamientoCSV.leerClientes();
                List<PagoRepostaje> pagosCSV = almacenamientoCSV.leerPagos();

                //Creamo un alamacenamiento para JSON y escribimos los archivos.
                Almacenamiento almacenamientoJSON = new AlmacenamientoJSON(dirDestino.toString(), archivoClientesJSON.getFileName().toString(), archivoPagosJSON.getFileName().toString());
                almacenamientoJSON.escribirClientes(clientesCSV);
                almacenamientoJSON.escribirPagos(pagosCSV);

                //Si todo va bien mostramos el número de clientes y pagos migrados
                System.out.println("Clientes migrados: "+clientesCSV.size());
                System.out.println("Pagos migrados: "+pagosCSV.size());
                System.out.println("Migración completada en "+dirDestino);
            }catch (Exception e){ //Manejamos cualquier excepción que pueda surgir en el proceso.
                System.out.println("=============================================================================");
                System.out.println("La migración ha fallado y no se ha completado.");
                System.out.println("Motivo: " + e.getMessage());
                System.out.println("=============================================================================");
            }
        }
    }
}
