import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public void mostrarMenu(){

        //Hay que verificar si el archivo a leer ya esta creado y si no crearlo.
        Scanner sc = new Scanner(System.in);
        String menuText = "=== GESTIÓN DE GASOLINERA ===\n" +
                "1. Dar de alta un cliente\n" +
                "2. Listar clientes\n" +
                "3. Buscar clientes\n" +
                "4. Procesar un pago de repostaje\n" +
                "5. Consultar pagos\n" +
                "0. Salir";

        byte opcion;




        //------------------------------------------------------------------------------------------------------------------------------------

        RepoCliente repoCliente = new RepoCliente(".\\ArchivosCSV", "Clientes");
        RepoPagos repoPagos = new RepoPagos(".\\ArchivosCSV", "Pagos");

        List<Cliente> clientes = repoCliente.listar();
        List<PagoRepostaje> pagos = repoPagos.listar();
        //------------------------------------------------------------------------------------------------------------------------------------

        //Funcionalidad tras el menú.
        do{
            System.out.println(menuText);
            opcion = comprobarOpcion(sc);

            switch(opcion){
                case 1 -> {
                    System.out.println("===================================================================");
                    clientes = Operaciones.altaCliente(clientes);
                    System.out.println("===================================================================");
                }
                case 2 ->{
                    System.out.println("===================================================================");
                    Operaciones.listarClientes(clientes);
                    System.out.println("===================================================================");
                }
                case 3 ->{
                    System.out.println("===================================================================");
                    Operaciones.buscarClientes(clientes);
                    System.out.println("===================================================================");
                }
                case 4 ->{
                    System.out.println("===================================================================");
                    Operaciones.procesarPagoRepostaje(pagos, clientes);
                    System.out.println("===================================================================");
                }
                case 5 ->{
                    System.out.println("===================================================================");
                    Operaciones.consultarPagos(pagos);
                    System.out.println("===================================================================");
                }
                default -> {

                    repoCliente.guardar(clientes);
                    repoPagos.guardar(pagos);
                    System.out.println("===================================================================");
                    System.out.println("Has salido del programa.");
                    System.out.println("===================================================================");
                }
            }
        }while(opcion != 0);
    }

    //-------------------------------------------------------------------------------------------------------
    // Un método para comprobar que se escriban valores válidos para seleccionar una opción del menú.

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
                System.out.println("Debes escribir un número entero comprendido entre 0 y 5 para elegir una opción :(");
                System.out.println("===============================================================================");
            }catch(IllegalArgumentException e2){
                System.out.println("===============================================================================");
                System.out.println("Debes escribir un número comprendido entre 0 y 5 :(");
                System.out.println("===============================================================================");
            }
        }
        sc.nextLine();
        return num;
    }

    //-------------------------------------------------------------------------------------------------------
}
