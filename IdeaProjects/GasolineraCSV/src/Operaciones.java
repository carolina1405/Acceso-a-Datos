import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
//Esta clase almacena la lógica de cada opción que hay en el menú.
public class Operaciones {
    //========================================================================================================================================
    public static List<Cliente> altaCliente(List<Cliente> clientes, Scanner sc){
        String nombre = comprobarNombre(sc).trim(),
                telefono = comprobarTelefono(sc).trim(),
                matricula = comprobarMatricula(sc, clientes).trim();

        boolean duplicada = false;
        for(Cliente c : clientes){
            if(c.getMatricula().equalsIgnoreCase(matricula)){
                System.out.println("La matrícula ya existe para otro cliente");
                duplicada = true;
            }
        }
        if (!duplicada){
            clientes.add(new Cliente(clientes.size()+1, nombre, telefono, matricula));
        }else{
            System.out.println("No se ha guardado el nuevo cliente.");
        }
        return clientes;
    }

    //COMPROBADORES
    private static String comprobarTelefono(Scanner sc){
        String telf = "";
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Escriba el teléfono del cliente: ");
                telf = sc.nextLine().trim();
                if(telf.isEmpty()){
                    throw new IllegalArgumentException("Teléfono no válido");
                }else{
                    char[] telefonoCaracteres = telf.toCharArray();
                    for(char c : telefonoCaracteres){
                        if(!Character.isDigit(c) && !Character.isWhitespace(c)){
                            throw new IllegalArgumentException("Teléfono no válido");
                        }
                    }
                }

                valido = true;
            }catch (IllegalArgumentException e1){
                System.out.println("========================================");
                System.out.println("El teléfono introducido no es válido. :(");
                System.out.println("========================================");
            }
        }
        return telf;
    }
    private static String comprobarMatricula(Scanner sc, List<Cliente> clientes){
        String matr = "";
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Escriba la matrícula del cliente: ");
                matr = sc.nextLine().trim();
                if(matr.isEmpty()){
                    throw new IllegalArgumentException("Matrícula vacía");
                }
                valido = true;
            }catch (IllegalArgumentException e1){
                System.out.println("========================================");
                System.out.println("La matrícula no puede estar vacía :(");
                System.out.println("========================================");
            }
        }
        return matr.toUpperCase();
    }
    private static String comprobarNombre(Scanner sc){
        String name = "";
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Escriba el nombre del cliente: ");
                name = sc.nextLine().trim();
                if(name.isEmpty()){
                    throw new IllegalArgumentException("Nombre vacío");
                }
                valido = true;
            }catch (IllegalArgumentException e1){
                System.out.println("========================================");
                System.out.println("El nombre no puede estar vacío :(");
                System.out.println("========================================");
            }
        }
        return name;
    }
    //========================================================================================================================================
    public static void listarClientes(List<Cliente> clientes){
        if(clientes.isEmpty()){
            System.out.println("Aún no hay clientes registrados...");
        }else{
            clientes.sort(null);
            System.out.println("---- CLIENTES REGISTRADOS ----");
            for(Cliente c: clientes){
                System.out.println(c.toString());
            }
        }
    }
    //========================================================================================================================================
    public static void buscarClientes(List<Cliente> clientes, Scanner sc){
        if(clientes.isEmpty()){
            System.out.println("Aún no hay clientes en el sistema :(");
        }else{
            String clave = comprobarClave(sc);
            System.out.println("Buscando...");
            boolean coincidenciaNombre = false,
                    coincidenciaTelefono = false,
                    coincidenciaMatricula = false;
            int contadorCoincidencias = 0;

            System.out.println("---- RESULTADOS DE BÚSQUEDA ----");
            for(Cliente c: clientes){
                coincidenciaNombre = c.getNombre().toUpperCase().contains(clave.toUpperCase());
                coincidenciaTelefono = c.getTelefono().toUpperCase().contains(clave.toUpperCase());
                coincidenciaMatricula = c.getMatricula().toUpperCase().contains(clave.toUpperCase());

                if(coincidenciaNombre || coincidenciaTelefono || coincidenciaMatricula){
                    System.out.println(c.toString());
                    contadorCoincidencias++;
                }
            }
            if(contadorCoincidencias == 0){
                System.out.println("No se han encontrado coincidencias.");
            }
            System.out.println("--------------------------------");
        }
    }

    //COMPROBADORES
    public static String comprobarClave(Scanner sc){
        String clave = "";
        boolean valido = false;

        while(!valido){
            try{
                System.out.print("Escribe una clave de búsqueda: ");
                clave = sc.nextLine().trim();
                if(clave.isBlank()){
                    throw new IllegalArgumentException("Clave vacía");
                }
                valido = true;
            }catch(IllegalArgumentException e){
                System.out.println("========================================");
                System.out.println("La clave de búsqueda no puede estar en blanco :(");
                System.out.println("========================================");
            }
        }
        return clave;
    }
    //========================================================================================================================================
    public static List<PagoRepostaje> procesarPagoRepostaje(List<PagoRepostaje> pagos, List<Cliente> clientes, Scanner sc){

        if(clientes.isEmpty()){
            System.out.println("Aún no hay clientes registrados. Para procesar un pago debes tener registrado al menos un cliente.");
        }else{
            int idCliente = comprobarFormatoIDCliente(sc);
            if(idCliente < 1 || idCliente > clientes.size()){
                System.out.println("El cliente con id "+idCliente+" no existe. No se ha registrado el pago.");
            }else{
                int idPago = pagos.size()+1;
                LocalDate fecha = comprobarFormatoFecha(sc);
                double importe = comprobarFormatoImporte(sc);
                double litros = comprobarFormatoLitros(sc);
                Combustible combustible = comprobarCombustible(sc);

                pagos.add(new PagoRepostaje(idPago, idCliente, fecha, importe, litros, combustible));
                System.out.println("-----------------------------------------");
                System.out.println("Se ha guardado correctamente el pago con:" +
                        "\nID: "+pagos.getLast().getId()+
                        "\nCliente: "+clientes.get(pagos.getLast().getIdCliente()-1).getNombre()+
                        "\nImporte: "+String.format("%.2f", pagos.getLast().getImporte()));
                System.out.println("-----------------------------------------");

            }
        }
        return pagos;
    }

    //COMPROBADORES
    public static int comprobarFormatoIDCliente(Scanner sc){
        int id = 0;
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Escriba el ID del cliente a nombre del cual procesará el pago: ");
                id = sc.nextInt();

                if(id <= 0){
                    throw new IllegalArgumentException("ID no válido");
                }

                valido = true;
            }catch(InputMismatchException e1){
                sc.nextLine();
                System.out.println("========================================");
                System.out.println("ID no válido :(");
                System.out.println("========================================");
            }catch(IllegalArgumentException e2){
                System.out.println("========================================");
                System.out.println("ERROR. Los IDs de los clientes son números positivos mayores que 0 :(");
                System.out.println(e2.getMessage());
                System.out.println("========================================");
            }
        }
        sc.nextLine();
        return id;
    }
    public static LocalDate comprobarFormatoFecha(Scanner sc){
        String fecha = "";
        LocalDate fechaFormateada = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean valido = false;

        while(!valido){
            try{
                System.out.print("Escriba la fecha del pago (dejar vacío para hoy): ");
                fecha = sc.nextLine().trim();

                if(!fecha.isEmpty()){
                    fechaFormateada = LocalDate.parse(fecha, formato);
                }
                valido = true;
            }catch(DateTimeParseException e2){
                System.out.println("========================================");
                System.out.println("FOrmato no válido :(");
                System.out.println("El formato de la fecha debe ser: dd/MM/yyyy");
                System.out.println("========================================");
            }
        }
        return fechaFormateada;
    }
    public static double comprobarFormatoImporte(Scanner sc){
        double num = 0;
        boolean valido = false;

        while(!valido){
            try{
                System.out.print("Escriba el importe: ");
                String line = sc.nextLine().trim();
                num = Double.parseDouble(line.replace(",", ".")); //Esto permitirá que se puedan usar tanto comas como puntos como separador decimal.

                if(num < 0){
                    throw new IllegalArgumentException("Número negativo");
                }

                valido = true;
            }catch(InputMismatchException e1){
                sc.nextLine();
                System.out.println("========================================");
                System.out.println("Importe no válido :(");
                System.out.println("========================================");
            }catch(IllegalArgumentException e2){
                System.out.println("========================================");
                System.out.println("No se admiten importes inferiores a 0 :(");
                System.out.println("========================================");
            }
        }
        return num;
    }
    public static double comprobarFormatoLitros(Scanner sc){
        double num = 0;
        boolean valido = false;

        while(!valido){
            try{
                System.out.print("Escriba los litros: ");
                String line = sc.nextLine().trim();
                num = Double.parseDouble(line.replace(",", ".")); //Esto permitirá que se puedan usar tanto comas como puntos como separador decimal.

                if(num < 0){
                    throw new IllegalArgumentException("Número negativo");
                }

                valido = true;
            }catch(InputMismatchException e1){
                sc.nextLine();
                System.out.println("========================================");
                System.out.println("Cantidad de litros no válida :(");
                System.out.println("========================================");
            }catch(IllegalArgumentException e2){
                System.out.println("========================================");
                System.out.println("No se admiten valores negativos :(");
                System.out.println("========================================");
            }
        }
        return num;
    }
    public static Combustible comprobarCombustible(Scanner sc){
        String c = "";
        boolean valido = false;
        Combustible combustible = null;
        while(!valido){
            try{
                System.out.print("Introduce un combustible: ");
                c = sc.nextLine().trim();
                combustible = Combustible.valueOf(c.toUpperCase());

                valido = true;
            }catch (IllegalArgumentException e1){
                System.out.println("========================================");
                System.out.println("Combustible no válido :(");
                System.out.println("========================================");
            }
        }
        return combustible;
    }
    //========================================================================================================================================
    public static void consultarPagos(List<PagoRepostaje> pagos){
        if(pagos.isEmpty()){
            System.out.println("Aún no se ha registrado ningún pago..." );
        }else{
            pagos.sort(null);
            System.out.println("---- PAGOS REGISTRADOS ----");
            for(PagoRepostaje p : pagos){
                System.out.println(p.toString());
            }
        }
    }
}
