import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        String menuText = "=== GESTIÓN DE GASOLINERA ===\n" +
                "1. Dar de alta un cliente\n" +
                "2. Listar clientes\n" +
                "3. Buscar clientes\n" +
                "4. Procesar un pago de repostaje\n" +
                "5. Consultar pagos\n" +
                "0. Salir";
        byte opcion;



        //=========================================================================================

        //Creamos un almacenamiento para leer/escribir en el formato deseado.
        Almacenamiento almacenamiento = null;



        List<Path> rutas = new LinkedList<>();

        //Comprobamos que no hayan ficheros .json (normal en un primer arranque) para
        //preguntarle al usuario si desea realizar la migración.
        try(Stream<Path> encontrados = Files.find(Path.of("."), 3,
                (ruta, atributos) -> atributos.isRegularFile() && ruta.toString().endsWith(".json"))){
            boolean vacio = encontrados.findAny().isEmpty();
            if(vacio){
                if(comprobarOpcionMigracion(sc)){
                    System.out.print("Origen CSV: ");
                    String origen = sc.nextLine();
                    System.out.print("Destino JSON: ");
                    String destino = sc.nextLine();
                    System.out.println();
                    MigraCSVToJson.ejecutarMigracion(origen, destino);
                }
            }
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }

        //Para acceder a los archivos (migrados o no) en cualquier arranque
        //hacemos una comprobación como la anterior nuevamente.
        try(Stream<Path> encontrados = Files.find(Path.of("."), 3,
                (ruta, atributos) -> atributos.isRegularFile() && ruta.toString().endsWith(".json"))){
            rutas = encontrados.toList(); //Creamos ua lista inmutable con los resultados de la búsqueda.
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }

        //Creamos los nombres del directorio y los archivos por defecto.
        String directorio = "ArchivosJSON";
        String ficheroClientes = "clientes.json";
        String ficheroPagos = "pagos.json";
        
        if(!rutas.isEmpty()){ //Si ya existían archivos .json comprobamos que cumplan con los requisitos de nombre.
            String directorioPrueba = rutas.get(0).getParent().toString();
            boolean mismoDirectorio1 = false, mismoDirectorio2 = false; //Comprueban que ambos ficheros estén en el mismo directorio
            for (Path p : rutas) {
                String nombreFichero = p.getFileName().toString(); //Cambiamos los valores por defecto si se cumplen estos requisitos.
                if (nombreFichero.contains("pago") && p.getParent().toString().equals(directorioPrueba)) {
                    ficheroPagos = nombreFichero;
                    mismoDirectorio1 = true;
                } else if (nombreFichero.contains("cliente") && p.getParent().toString().equals(directorioPrueba)) {
                    ficheroClientes = nombreFichero;
                    mismoDirectorio2 = true;
                }
            }
            if(mismoDirectorio1 && mismoDirectorio2){
                directorio = directorioPrueba; //Solo se cambia si ambos ficheros.json están en el mismo directorio.
            }

        }

        //Asignamos el directorio y nombres de ficheros resultantes de la exploración.
        //De esta manera podremos acceder siempre a los ficheros .json existieran o no previamente.
        almacenamiento = new AlmacenamientoJSON(directorio, ficheroClientes, ficheroPagos);



        //Como los streams usados para la lectura devuelven listas inmutables,
        //debo reasignar sus valores en nuevas listas que sí puedan ser modificadas.
        List<Cliente> lecturaClientes = almacenamiento.leerClientes();
        List<Cliente> clientes = new LinkedList<>(lecturaClientes);

        List<PagoRepostaje> lecturaPagos = almacenamiento.leerPagos();
        List<PagoRepostaje> pagos = new LinkedList<>(lecturaPagos);

        //=========================================================================================

        //Esta es la lógica detrás del menú.
        do {
            System.out.println(menuText);
            opcion = comprobarOpcionMenu(sc);
            switch (opcion) {
                case 1 -> {
                    System.out.println("===================================================================");
                    clientes = OperacionesMenu.altaCliente(clientes, sc);
                    System.out.println("===================================================================");
                    almacenamiento.escribirClientes(clientes); //Ahora guardamos los cambios tras cada operación
                    almacenamiento.escribirPagos(pagos);
                }
                case 2 -> {
                    System.out.println("===================================================================");
                    OperacionesMenu.listarClientes(clientes);
                    System.out.println("===================================================================");
                    almacenamiento.escribirClientes(clientes);
                    almacenamiento.escribirPagos(pagos);
                }
                case 3 -> {
                    System.out.println("===================================================================");
                    OperacionesMenu.buscarClientes(clientes, sc);
                    System.out.println("===================================================================");
                    almacenamiento.escribirClientes(clientes);
                    almacenamiento.escribirPagos(pagos);
                }
                case 4 -> {
                    System.out.println("===================================================================");
                    pagos = OperacionesMenu.procesarPagoRepostaje(pagos, clientes, sc);
                    System.out.println("===================================================================");
                    almacenamiento.escribirClientes(clientes);
                    almacenamiento.escribirPagos(pagos);
                }
                case 5 -> {
                    System.out.println("===================================================================");
                    OperacionesMenu.consultarPagos(pagos);
                    System.out.println("===================================================================");
                    almacenamiento.escribirClientes(clientes);
                    almacenamiento.escribirPagos(pagos);
                }
                default -> {
                    System.out.println("===================================================================");
                    System.out.println("Has salido del programa.");
                    System.out.println("===================================================================");
                }

            }

        }while (opcion != 0) ;
        sc.close();  //Cerramos el escáner al final del programa.
    }

    private byte comprobarOpcionMenu(Scanner sc){
        byte num = 0;
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Opción: ");
                num = Byte.parseByte(sc.nextLine());
                if(num > 5 || num < 0){
                    throw new IllegalArgumentException ("Fuera de rango.");
                }

                valido = true;
            }catch(NumberFormatException e1){
                System.out.println("===============================================================================");
                System.out.println("Debes escribir un número entero y este debe ser una de las opciones del menú :(");
                System.out.println("===============================================================================");
            }catch(IllegalArgumentException e2){
                System.out.println("===============================================================================");
                System.out.println("El número introducido debe corresponderse con una de las opciones del menú :(");
                System.out.println("===============================================================================");
            }
        }
        return num;
    }
    private boolean comprobarOpcionMigracion(Scanner sc){
        String decision = "";
        boolean valido = false;

        while(!valido){
            try{
                System.out.print("¿Hay ficheros .csv que migrar (S/N)?: ");
                decision = sc.nextLine();

                if(decision.equalsIgnoreCase("S")){
                    valido = true;
                    return true;
                }else if (decision.equalsIgnoreCase("N")){
                    valido = true;
                }else{
                    throw new IllegalArgumentException("Respuesta no válida");
                }
            }catch (IllegalArgumentException e){
                System.out.println("==========================================");
                System.out.println("ERROR: Debes escribir S/N para decidir.");
                System.out.println("==========================================");
            }
        }
        return false;
    }
}
