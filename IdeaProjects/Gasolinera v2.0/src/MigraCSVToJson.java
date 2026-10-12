import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MigraCSVToJson {

    public static void ejecutarMigracion(String rutaOrigen, String rutaDestino){
        Path dirOrigen = Path.of(rutaOrigen);
        Path dirDestino = Path.of(rutaDestino);

        Path archivoClientesCSV = dirOrigen.resolve("clientes.csv");
        Path archivoPagosCSV = dirOrigen.resolve("pagos.csv");

        Path archivoClientesJSON = dirDestino.resolve("clientes.json");
        Path archivoPagosJSON = dirDestino.resolve("pagos.json");

        if(Files.exists(archivoClientesJSON) || Files.exists(archivoPagosJSON)){
            System.out.println("=============================================================================");
            System.out.println("Migración cancelada: el destino ya contiene datos.json.");
            System.out.println("Selecciona un destino libre. No se han modificado los ficheros existentes.");
            System.out.println("=============================================================================");
        }else if(Files.notExists(archivoClientesCSV) || Files.notExists(archivoPagosCSV)){
            System.out.println("=============================================================================");
            System.out.println("ERROR: Faltan ficheros de origen requeridos en el directorio: " + rutaOrigen);
            System.out.println("=============================================================================");
        }else{
            System.out.println("Validación completada.");
            try{
                Almacenamiento almacenamientoCSV = new AlmacenamientoCSV(dirOrigen.toString(), archivoClientesCSV.getFileName().toString(), archivoPagosCSV.getFileName().toString());
                List<Cliente> clientesCSV = almacenamientoCSV.leerClientes();
                List<PagoRepostaje> pagosCSV = almacenamientoCSV.leerPagos();

                Almacenamiento almacenamientoJSON = new AlmacenamientoJSON(dirDestino.toString(), archivoClientesJSON.getFileName().toString(), archivoPagosJSON.getFileName().toString());
                almacenamientoJSON.escribirClientes(clientesCSV);
                almacenamientoJSON.escribirPagos(pagosCSV);
                System.out.println("Clientes migrados: "+clientesCSV.size());
                System.out.println("Pagos migrados: "+pagosCSV.size());
                System.out.println("Migración completada en "+dirDestino);
            }catch (Exception e){
                System.out.println("=============================================================================");
                System.out.println("La migración ha fallado y no se ha completado.");
                System.out.println("Motivo: " + e.getMessage());
                System.out.println("=============================================================================");
            }
        }
    }
}
