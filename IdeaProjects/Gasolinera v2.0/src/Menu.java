import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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

        Almacenamiento almacenamientoCSV = new AlmacenamientoCSV();

        List<Cliente> lecturaClientes = almacenamientoCSV.leerClientes();
        List<Cliente> clientes = new LinkedList<>(lecturaClientes);

        List<PagoRepostaje> lecturaPagos = almacenamientoCSV.leerPagos();
        List<PagoRepostaje> pagos = new LinkedList<>(lecturaPagos);

        do {
            System.out.println(menuText);
            opcion = comprobarOpcion(sc);
            switch (opcion) {
                case 1 -> {
                    System.out.println("===================================================================");
                    clientes = OperacionesMenu.altaCliente(clientes, sc);
                    System.out.println("===================================================================");
                }
                case 2 -> {
                    System.out.println("===================================================================");
                    OperacionesMenu.listarClientes(clientes);
                    System.out.println("===================================================================");
                }
                case 3 -> {
                    System.out.println("===================================================================");
                    OperacionesMenu.buscarClientes(clientes, sc);
                    System.out.println("===================================================================");
                }
                case 4 -> {
                    System.out.println("===================================================================");
                    pagos = OperacionesMenu.procesarPagoRepostaje(pagos, clientes, sc);
                    System.out.println("===================================================================");
                }
                case 5 -> {
                    System.out.println("===================================================================");
                    OperacionesMenu.consultarPagos(pagos);
                    System.out.println("===================================================================");
                }
                default -> {
                    almacenamientoCSV.escribirClientes(clientes);
                    almacenamientoCSV.escribirPagos(pagos);

                    System.out.println("===================================================================");
                    System.out.println("Has salido del programa.");
                    System.out.println("===================================================================");
                }

            }

        }while (opcion != 0) ;
        sc.close();  //Cerramos el escáner.
    }

    private byte comprobarOpcion(Scanner sc){
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
}
