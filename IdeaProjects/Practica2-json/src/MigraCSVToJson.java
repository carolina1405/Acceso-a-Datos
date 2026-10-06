import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class MigraCSVToJson {
    public static void migrarDatosCSV(Scanner sc){
        //Se pregunta si se desean migrar los archivos

        if (comprobarOpcionMigracion(sc).equalsIgnoreCase("S")) {
            System.out.print("Origen CSV: ");
            String rutaOrigen = sc.nextLine();

            while(!Files.exists(Path.of(rutaOrigen))){
                System.out.println("=====================================================");
                System.out.println("La ruta especificada no existe. Inténtelo de nuevo.");
                System.out.println("=====================================================");
                System.out.print("Origen CSV: ");
                rutaOrigen = sc.nextLine();
            }
            if(Files.exists(Path.of(rutaOrigen, "Clientes.csv")) && Files.exists(Path.of(rutaOrigen, "Pagos.csv"))){

                RepoCliente repoClientesCSV = new RepoCliente(rutaOrigen, "Clientes.csv");
                RepoPagos repoPagosCSV = new RepoPagos(rutaOrigen, "Pagos.csv");


                System.out.print("Destino JSON: ");
                String rutaDestino = sc.nextLine();

                while(!Files.exists(Path.of(rutaDestino))){
                    System.out.println("=====================================================");
                    System.out.println("La ruta especificada no existe. Inténtelo de nuevo.");
                    System.out.println("=====================================================");
                    System.out.print("Destino JSON: ");
                    rutaDestino = sc.nextLine();
                }

                if(!comprobarFicherosJSON(rutaDestino)){
                    System.out.println("Validación completada.");


                    List<Cliente> clientes = new LinkedList<>();
                    List<PagoRepostaje> pagos = new LinkedList<>();

                    clientes = repoClientesCSV.listarCSV();
                    pagos = repoPagosCSV.listarCSV();


                    if(!clientes.isEmpty() && !pagos.isEmpty()){
                        RepoCliente repoClientesJSON = new RepoCliente(rutaDestino, "Clientes.json");
                        RepoPagos repoPagosJSON = new RepoPagos(rutaDestino, "Pagos.json");

                        repoClientesJSON.guardarJSON(clientes);
                        repoPagosJSON.guardarJSON(pagos);
                    }else{
                        System.out.println("Migración cancelada: Hay uno o más ficheros vacíos en el origen.");
                        System.out.println("Selecciona un origen donde todos los ficheros requeridos contengan datos. No se han modificado los ficheros existentes.");
                    }
                }else{
                    System.out.println("Migración cancelada: El destino ya contiene datos.json.");
                    System.out.println("Selecciona un destino libre. No se han modificado los ficheros existentes.");
                }
            }else{
                System.out.println("Migración cancelada: No se han encontrado los ficheros .csv requeridos.");
                System.out.println("Selecciona un origen con lo ficheros \"Clientes.csv\" y \"Pagos.csv\" . No se han modificado los ficheros existentes.");
            }

        }

    }

    private static String comprobarOpcionMigracion(Scanner sc) {
        String opcion = "";
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("¿Hay archivos CSV que migrar a JSON (S/N)?: ");
                opcion = sc.nextLine();
                if (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
                    throw new IllegalArgumentException("Respuesta no válida");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("===============================================================================");
                System.out.println("Debes elegir S/N");
                System.out.println("===============================================================================");
            }
        }
        return opcion;
    }

    private static boolean comprobarFicherosJSON(String directorio) {
        Path rutaInicio = Path.of(directorio);
        int profundidad = 5;
        boolean encontrado = false;
        try (Stream<Path> encontrados = Files.find(rutaInicio, profundidad,
                (ruta, atributos) -> atributos.isRegularFile() && ruta.getFileName().toString().toLowerCase().endsWith(".json"))) {
            encontrado = encontrados.findFirst().isPresent();
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error durante la comprobación.");
        }
        return encontrado;
    }

}
