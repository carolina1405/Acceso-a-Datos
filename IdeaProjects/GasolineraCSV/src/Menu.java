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

        //Recuperar clientes y pagos al inicio, en caso de crear ya un objeto o lista de objetos guardarlos al salir
        //Funcionalidad tras el menú.
        do{
            System.out.println(menuText);
            opcion = comprobarOpcion(sc);

            switch(opcion){
                case 1 -> {
                    System.out.println("===================================================================");
                    Operaciones.altaCliente();
                    System.out.println("===================================================================");
                }
                case 2 ->{
                    System.out.println("===================================================================");
                    Operaciones.listarClientes();
                    System.out.println("===================================================================");
                }
                case 3 ->{
                    System.out.println("===================================================================");
                    String clave = "";
                    Operaciones.buscarClientes(clave);
                    System.out.println("===================================================================");
                }
                case 4 ->{
                    System.out.println("===================================================================");
                    Operaciones.procesarPagoRepostaje();
                    System.out.println("===================================================================");
                }
                case 5 ->{
                    System.out.println("===================================================================");
                    Operaciones.consultarPagos();
                    System.out.println("===================================================================");
                }
                default -> {
                    System.out.println("===================================================================");
                    System.out.println("Has salido del programa.");
                    //Aqui se guardaran en un documento todos los clientes y pagos que se hayan creado nuevos.
                    System.out.println("===================================================================");
                }
            }
        }while(opcion != 0);
    }


    //-------------------------------------------------------------------------------------------------------

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
