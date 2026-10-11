import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class OperacionesMenu {
    //Esta clase almacena la lógica de cada opción que hay en el menú.
    //Como solo vamos a tener métodos no es necesario crear una instancia de esta clase
    //para usarlos, así que serán estáticos.
    //========================================================================================================================================
    public static List<Cliente> altaCliente(List<Cliente> clientes, Scanner sc){
        String matricula = comprobarMatricula(sc, clientes);

        if(matricula != null){
            String nombre = comprobarNombre(sc),
                    telefono = comprobarTelefono(sc);
            clientes.add(new Cliente(clientes.size()+1, nombre, telefono, matricula));
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
                //Verificamos que el usuario no deje este campo en blanco.
                if(telf.isEmpty()){
                    throw new IllegalArgumentException("Teléfono no válido");
                }else{
                    //Comprobamos si el dígito es efectivamente un número o espacio en blanco
                    //y en caso de que hubiera un prefijo ("+" al inicio) el "+" será admitido como
                    //primer carácter.
                    for(int i = 0; i < telf.length(); i++){
                        char c = telf.charAt(i);
                        if(c == '+' && i != 0){
                            throw new IllegalArgumentException("Teléfono no válido");
                        }else{
                            if(!Character.isDigit(c) && !Character.isWhitespace(c) && !(c == '+' && i == 0)){
                                throw new IllegalArgumentException("Teléfono no válido");
                            }
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
        //Comprobamos que la matrícula introducida no esté ya registrada para otro cliente.
        String finalMatr = matr;
        boolean duplicada = clientes
                .stream()
                .anyMatch(cliente -> cliente.getMatricula().equalsIgnoreCase(finalMatr));
        if (duplicada){
            System.out.println("=========================================================");
            System.out.println("La matrícula ya ha sido registrada para otro cliente." +
                    "\nNo se ha guardado el nuevo cliente.");
            System.out.println("=========================================================");
            return null;
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
            System.out.println("------------------ CLIENTES REGISTRADOS ------------------");
            String formatoColumnas = "%-5s %-25s %-15s %-12s%n";
            System.out.printf(formatoColumnas, "ID","NOMBRE","TELÉFONO","MATRÍCULA");
            clientes.stream()
                    .sorted()
                    .forEach(cliente -> System.out.printf(formatoColumnas,
                            cliente.getID(), cliente.getNombre(), cliente.getTelefono(), cliente.getMatricula()));
            System.out.println("----------------------------------------------------------");
        }
    }
    //========================================================================================================================================
    public static void buscarClientes(List<Cliente> clientes, Scanner sc){
        System.out.println("BÚSQUEDA DE CLIENTES");
        if(clientes.isEmpty()){
            System.out.println("Aún no hay clientes registrados en el sistema :(");
        }else{
            String clave = comprobarClave(sc);
            System.out.println("Buscando...");
            List<Cliente> coincidencias = clientes.stream()
                    .filter(cliente ->
                            (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(clave.toLowerCase())) ||
                            (cliente.getTelefono() != null && cliente.getTelefono().contains(clave)) ||
                            (cliente.getMatricula() != null && cliente.getMatricula().toLowerCase().contains(clave.toLowerCase())))
                    .sorted()
                    .toList();
            System.out.println("-----RESULTADOS DE BÚSQUEDA--------------------------------");
            if(coincidencias.isEmpty()){
                System.out.println("No se han encontrado coincidencias.");
            }else{
                String formatoColumnas = "%-5s %-25s %-15s %-12s%n";
                System.out.printf(formatoColumnas, "ID","NOMBRE","TELÉFONO","MATRÍCULA");
                coincidencias.stream()
                        .sorted()
                        .forEach(cliente -> System.out.printf(formatoColumnas,
                                cliente.getID(), cliente.getNombre(), cliente.getTelefono(), cliente.getMatricula()));
            }
            System.out.println("-----------------------------------------------------------");
        }
    }

    //COMPROBADORES
    private static String comprobarClave(Scanner sc){
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
            System.out.println("Aún no hay clientes registrados. " +
                    "\nPara procesar un pago debes tener registrado al menos un cliente.");
        }else{
            listarClientes(clientes);
            int idCliente = comprobarFormatoIDCliente(sc);
            Cliente c = clientes.stream()
                    .filter(cliente -> cliente.getID() == idCliente)
                    .findFirst().orElse(null);
            if(c == null){
                System.out.println("===================================================================");
                System.out.println("El cliente con id "+idCliente+" no existe. " +
                        "\nNo se ha registrado el pago.");
                System.out.println("===================================================================");
            }else {
                int idPago = pagos.size() + 1;
                LocalDate fecha = comprobarFormatoFecha(sc);
                double importe = comprobarFormatoImporte(sc);
                double litros = comprobarFormatoLitros(sc);
                Combustible combustible = comprobarCombustible(sc);

                pagos.add(new PagoRepostaje(idPago, idCliente, fecha, importe, litros, combustible));

                String formatoExito = "%-6s %-20s %-12s%n";
                System.out.println("-----------------------------------------");
                System.out.println("Se ha guardado correctamente el pago con:");
                System.out.printf(formatoExito, "ID", "CLIENTE", "IMPORTE");
                System.out.printf(formatoExito,
                        pagos.getLast().getId(),
                        c.getNombre(),
                        String.format("%.2f €", pagos.getLast().getImporte())
                );
                System.out.println("-----------------------------------------");

            }
        }
        return pagos;
    }

    //COMPROBADORES
    private static int comprobarFormatoIDCliente(Scanner sc){
        int id = 0;
        boolean valido = false;
        while(!valido){
            try{
                System.out.print("Escriba el ID del cliente a nombre del cual procesará el pago: ");
                id = Integer.parseInt(sc.nextLine());

                if(id <= 0){
                    throw new IllegalArgumentException("ID no válido");
                }

                valido = true;
            }catch(NumberFormatException e1){
                System.out.println("===================");
                System.out.println("ID no válido :(");
                System.out.println("===================");
            }catch(IllegalArgumentException e2){
                System.out.println("============================================");
                System.out.println("ERROR. Los IDs de los clientes son números" +
                        "\nenteros positivos mayores que 0 :(");
                System.out.println("============================================");
            }
        }
        return id;
    }
    private static LocalDate comprobarFormatoFecha(Scanner sc){
        String fecha;
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
                System.out.println("=============================================");
                System.out.println("Formato no válido :(");
                System.out.println("El formato de la fecha debe ser: dd/MM/yyyy");
                System.out.println("=============================================");
            }
        }
        return fechaFormateada;
    }
    private static double comprobarFormatoImporte(Scanner sc){
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
            }catch(NumberFormatException e1){
                System.out.println("========================================");
                System.out.println("Importe no válido :(");
                System.out.println("Asegúrese de usar solo números y que el" +
                        "\nseparador decimal sea o un punto o una coma.");
                System.out.println("========================================");
            }catch(IllegalArgumentException e2){
                System.out.println("========================================");
                System.out.println("No se admiten importes inferiores a 0 :(");
                System.out.println("========================================");
            }
        }
        return num;
    }
    private static double comprobarFormatoLitros(Scanner sc){
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
            }catch(NumberFormatException e1){
                System.out.println("========================================");
                System.out.println("Formato de litros incorrecto. :(");
                System.out.println("Asegúrese de usar solo números y que el" +
                        "\nseparador decimal sea o un punto o una coma.");
                System.out.println("========================================");
            }catch(IllegalArgumentException e2){
                System.out.println("========================================");
                System.out.println("No se admiten valores negativos :(");
                System.out.println("========================================");
            }
        }
        return num;
    }
    private static Combustible comprobarCombustible(Scanner sc){
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
            String formatoColumnas = "%-6s %-16s %-12s %-12s %-10s %-14s%n";
            System.out.println("=================== PAGOS REGISTRADOS ====================");
            System.out.printf(formatoColumnas, "ID","ID DEL CLIENTE","FECHA","IMPORTE","LITROS","COMBUSTIBLE");
            pagos.stream()
                 .sorted()
                 .forEach(pago -> System.out.printf(formatoColumnas,
                         pago.getId(), pago.getIdCliente(),
                         pago.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                         String.format("%.2f €", pago.getImporte()), String.format("%.2f L", pago.getLitros()),
                         pago.getCombustible()));
            System.out.println("==========================================================");
        }
    }
}
