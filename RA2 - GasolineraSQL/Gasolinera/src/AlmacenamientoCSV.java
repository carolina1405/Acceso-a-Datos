import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class AlmacenamientoCSV implements Almacenamiento{
    /*Clase que implementa la interfaz de Almacenamiento
    para CSV para clientes y pagos*/

    //ATRIBUTOS
    Path directorio;
    Path archivoClientes;
    Path archivoPagos;
    String encabezado;

    //COSNTRUCTOR
    public AlmacenamientoCSV(String directorio, String ficheroClientes, String ficheroPagos){
        this.directorio = Path.of(directorio);
        this.archivoClientes = this.directorio.resolve(ficheroClientes.replace(".csv", "")+".csv");
        this.archivoPagos = this.directorio.resolve(ficheroPagos.replace(".csv", "")+".csv");

        try{
            Files.createDirectories(this.directorio);

            if(Files.notExists(archivoClientes)){
                Files.createFile(archivoClientes);
                encabezado = "ID,Nombre,Teléfono,Matrícula\n";
                Files.writeString(archivoClientes, encabezado);
            }

            if(Files.notExists(archivoPagos)){
                Files.createFile(archivoPagos);
                encabezado = "ID,ID del Cliente,Fecha,Importe,Litros,Combustible\n";
                Files.writeString(archivoPagos, encabezado);
            }



        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    //MÉTODOS
    @Override
    public List<Cliente> leerClientes() {
        List<Cliente> clientes = new LinkedList<>();
        ClienteConverter converter = new ClienteConverter(",");
        try(Stream<String> stream = Files.lines(archivoClientes)){
            clientes = stream
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(converter.fromCSV())
                    .toList();
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
        return clientes;
    }

    @Override
    public void escribirClientes(List<Cliente> clientes) {
        ClienteConverter converter = new ClienteConverter(",");
        encabezado = "ID,Nombre,Teléfono,Matrícula";
        List<String> escritura = new LinkedList<>();

        try{
            escritura.add(encabezado);

            List<String> lineasClientes = clientes
                    .stream()
                    .map(converter.toCSV())
                    .toList();

            escritura.addAll(lineasClientes);
            Files.write(archivoClientes, escritura);
            System.out.println("Se han guardado los cambios realizados en los clientes.");
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    @Override
    public List<PagoRepostaje> leerPagos() {
        List<PagoRepostaje> pagos = new LinkedList<>();
        PagoConverter converter = new PagoConverter(",");
        try(Stream<String> stream = Files.lines(archivoPagos)){
            pagos = stream
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(converter.fromCSV())
                    .toList();
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
        return pagos;
    }

    @Override
    public void escribirPagos(List<PagoRepostaje> pagos) {
        List<String> escritura = new LinkedList<>();
        PagoConverter converter = new PagoConverter(",");
        encabezado = "ID,ID del Cliente,Fecha,Importe,Litros,Combustible";
        try{
            escritura.add(encabezado);

            List<String> lineasPagos = pagos
                    .stream()
                    .map(converter.toCSV())
                    .toList();

            escritura.addAll(lineasPagos);
            Files.write(archivoPagos, escritura);
            System.out.println("Se han guardado los cambios realizados en los pagos.");
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }
}
