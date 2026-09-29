import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RepoCliente extends Repositorio<Cliente>{

    //=========================================================
    //Atributos
    private Path archivoClientes;
    private String cabecera;
    //=========================================================
    //Constructor
    public RepoCliente(String directorio, String archivoClientes) {
        super(directorio);

        //Se generará un archivo cuyo nombre será pasado como parámetro
        generarCSV(directorio, archivoClientes);
    }
    //=========================================================
    //Métodos
    @Override
    protected void guardar(List<Cliente> clientes) {
        try(BufferedWriter out1 = Files.newBufferedWriter(archivoClientes)){
            ClienteConverter converter = new ClienteConverter();
            out1.write(cabecera);
            out1.newLine();
            for(Cliente c: clientes){
                String clienteEscritura = converter.toCSV(c, ",");
                out1.write(clienteEscritura);
                out1.newLine();
            }

            System.out.println("===================================================================");
            System.out.println("Se han guardado los cambios de los clientes.");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected List<Cliente> listar() {
        ClienteConverter converter = new ClienteConverter();
        List<Cliente> clientes = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoClientes)){

            in.readLine();
            String line = in.readLine();
            while(line != null){
                clientes.add(converter.fromCSV(line, ","));
                line = in.readLine();
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return clientes;
    }

    @Override
    protected void generarCSV(String directorio, String archivoClientes){
        this.archivoClientes = Path.of(directorio, archivoClientes+".csv");
        this.cabecera = "ID,NOMBRE,TELÉFONO,MATRÍCULA";
        if(Files.notExists(this.archivoClientes)){
            try{
                Files.createFile(this.archivoClientes);

            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    //---------------------------------------------------------
    //G/S
    public Path getArchivoClientes() {
        return archivoClientes;
    }

    public void setArchivoClientes(Path archivoClientes) {
        this.archivoClientes = archivoClientes;
    }

    //=========================================================
}
