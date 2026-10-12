import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlmacenamientoJSON implements Almacenamiento{

    //ATRIBUTOS
    Path directorio;
    Path archivoClientes;
    Path archivoPagos;
    String prefijo = "{\n\t[\n", sufijo = "\n\t]\n}";

    //CONSTRUCTOR
    public AlmacenamientoJSON(String directorio, String ficheroClientes, String ficheroPagos) {
        this.directorio = Path.of(directorio);
        this.archivoClientes = this.directorio.resolve(ficheroClientes.replace(".json", "")+".json");
        this.archivoPagos = this.directorio.resolve(ficheroPagos.replace(".json", "")+".json");

        try{
            Files.createDirectories(this.directorio);

            if(Files.notExists(archivoClientes)){
                Files.createFile(archivoClientes);
                Files.writeString(archivoClientes, prefijo+sufijo);
            }

            if(Files.notExists(archivoPagos)){
                Files.createFile(archivoPagos);
                Files.writeString(archivoPagos, prefijo+sufijo);
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
                    .skip(2)
                    .filter(line -> !line.isBlank() && !line.trim().equals("]") && !line.trim().equals("}"))
                    .map(converter.fromJSON())
                    .toList();
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
        return clientes;
    }

    @Override
    public void escribirClientes(List<Cliente> clientes) {
        ClienteConverter converter = new ClienteConverter(",");
        try{
            String lines = clientes.stream()
                    .map(converter.toJSON())
                    .collect(Collectors.joining(",\n"));
            String escritura = prefijo+lines+sufijo;
            Files.writeString(archivoClientes, escritura);
        } catch (IOException e) {
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    @Override
    public List<PagoRepostaje> leerPagos() {
        List<PagoRepostaje> pagos = new LinkedList<>();
        PagoConverter converter = new PagoConverter(",");
        try(Stream<String> stream = Files.lines(archivoPagos)){
            pagos = stream
                    .skip(2)
                    .filter(line -> !line.isBlank() && !line.trim().equals("]") && !line.trim().equals("}"))
                    .map(converter.fromJSON())
                    .toList();
        }catch (IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
        return pagos;
    }

    @Override
    public void escribirPagos(List<PagoRepostaje> pagos) {
        PagoConverter converter = new PagoConverter(",");
        try{
            String lines = pagos.stream()
                    .map(converter.toJSON())
                    .collect(Collectors.joining(",\n"));
            String escritura = prefijo+lines+sufijo;
            Files.writeString(archivoPagos, escritura);
        } catch (IOException e) {
            System.out.println("ERROR: "+e.getMessage());
        }
    }
}
