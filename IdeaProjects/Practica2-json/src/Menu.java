import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public void mostrarMenu(){
        Scanner sc = new Scanner(System.in);
        String menuText = "=== GESTIÓN DE GASOLINERA ===\n" +
                "1. Dar de alta un cliente\n" +
                "2. Listar clientes\n" +
                "3. Buscar clientes\n" +
                "4. Procesar un pago de repostaje\n" +
                "5. Consultar pagos\n" +
                "0. Salir";
        byte opcion;
        //====================================================================================================================================


        RepoCliente repoClientes = new RepoCliente(".\\ArchivosJSON", "Clientes");
        RepoPagos repoPagos = new RepoPagos(".\\ArchivosJSON", "Pagos");


        System.out.println();

        String migrar = comprobarMigracion(sc);

        if(migrar.equalsIgnoreCase("S")){
            System.out.print("Escriba el nombre del directorio donde guarda los archivos CSV: ");
            String directorio = sc.nextLine();
            migrarDatosCSV(directorio);
        }else{
            System.out.println("Has decidido no migrar los datos.");
        }


        //Guardamos los datos en listas
        List<Cliente> clientes = repoClientes.listarJSON();
        List<PagoRepostaje> pagos = repoPagos.listarJSON();
        //====================================================================================================================================

        //Funcionalidad tras el menú.
        do{
            System.out.println(menuText);
            opcion = comprobarOpcion(sc);
            switch(opcion){
                case 1 -> {
                    System.out.println("===================================================================");
                    clientes = Operaciones.altaCliente(clientes, sc);
                    System.out.println("===================================================================");
                }
                case 2 ->{
                    System.out.println("===================================================================");
                    Operaciones.listarClientes(clientes);
                    System.out.println("===================================================================");
                }
                case 3 ->{
                    System.out.println("===================================================================");
                    Operaciones.buscarClientes(clientes, sc);
                    System.out.println("===================================================================");
                }
                case 4 ->{
                    System.out.println("===================================================================");
                    Operaciones.procesarPagoRepostaje(pagos, clientes, sc);
                    System.out.println("===================================================================");
                }
                case 5 ->{
                    System.out.println("===================================================================");
                    Operaciones.consultarPagos(pagos);
                    System.out.println("===================================================================");
                }
                default -> {
                    System.out.println("===================================================================");
                    System.out.println("Has salido del programa.");
                    System.out.println("===================================================================");
                }
            }

        }while(opcion != 0);
        sc.close();  //Cerramos el escáner.
    }

    //-------------------------------------------------------------------------------------------------------
    // Comprueba que se escriben valores válidos para seleccionar una opción del menú.
    private byte comprobarOpcion(Scanner sc){
        byte num = 0;
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Opción: ");
                num = sc.nextByte();
                if(num > 5 || num < 0){
                    throw new IllegalArgumentException ("Fuera de rango.");
                }

                valido = true;
            }catch(InputMismatchException e1){
                sc.nextLine();
                System.out.println("===============================================================================");
                System.out.println("Debes escribir un número entero y este debe ser una de las opciones del menú :(");
                System.out.println("===============================================================================");
            }catch(IllegalArgumentException e2){
                System.out.println("===============================================================================");
                System.out.println("El número introducido debe corresponderse con una de las opciones del menú :(");
                System.out.println("===============================================================================");
            }
        }
        sc.nextLine(); //Limpiamos el buffer del escáner.
        return num;
    }
    //-------------------------------------------------------------------------------------------------------
    private void migrarDatosCSV(String directorio){

    }
    private String comprobarMigracion(Scanner sc){
        String opcion = "";
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("¿Desea migrar los datos de archivos en CSV creados previamente a JSON?: ");
                opcion = sc.nextLine();
                if(!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")){
                    throw new IllegalArgumentException("Respuesta no válida");
                }
                valido = true;
            }catch (IllegalArgumentException e){
                System.out.println("===============================================================================");
                System.out.println("Debes elegir S/N");
                System.out.println("===============================================================================");
            }
        }
        return opcion;
    }
}
