import java.util.InputMismatchException;
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

        //Funcionalidad tras el menú.
        do{
            System.out.println(menuText);
            opcion = comprobarOpcion(sc);

            switch(opcion){
                case 1 -> {
                    System.out.println("===================================================================");
                    altaCliente();
                    System.out.println("===================================================================");
                }
                case 2 ->{
                    System.out.println("===================================================================");
                    listarClientes();
                    System.out.println("===================================================================");
                }
                case 3 ->{
                    System.out.println("===================================================================");
                    buscarClientes();
                    System.out.println("===================================================================");
                }
                case 4 ->{
                    System.out.println("===================================================================");
                    procesarPagoRepostaje();
                    System.out.println("===================================================================");
                }
                case 5 ->{
                    System.out.println("===================================================================");
                    consultarPagos();
                    System.out.println("===================================================================");
                }
                default -> {
                    System.out.println("===================================================================");
                    System.out.println("Has salido del programa.");
                    System.out.println("===================================================================");
                }
            }
        }while(opcion != 0);



    }


    //-------------------------------------------------------------------------------------------------------
    // El conjunto principal de métodos que implementará nuestro menú. Aún son prototipos, por lo que no hacen gran cosa.

    private void altaCliente(){
        System.out.println("Damos de alta a un cliente");
    }
    private void listarClientes(){
        System.out.println("Listamos a todos los clientes");
    }
    private void buscarClientes(){
        System.out.println("Buscamos a un cliente");
    }
    private void procesarPagoRepostaje(){
        System.out.println("Procesamos un pago");
    }
    private void consultarPagos(){
        System.out.println("Consultamos los pagos");
    }
    //-------------------------------------------------------------------------------------------------------








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

        return num;
    }

    //-------------------------------------------------------------------------------------------------------
}
