import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RepoCliente extends Repositorio<Cliente>{

    private Path archivoClientes;

    public RepoCliente(String directorio, String archivoClientes) {
        super(directorio);
        this.archivoClientes = Path.of(archivoClientes+".csv");
    }

    @Override
    protected void guardar(Cliente objeto) {
        System.out.println("Se guarda un cliente");
    }

    @Override
    protected List<Cliente> listar() {

        List<Cliente> clientes = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoClientes)){

            String line = in.readLine();
            while(line != null){
                Cliente cliente = new Cliente();
                clientes.add(cliente.fromCSV(line));
                line = in.readLine();
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return clientes;
    }

    public Path getArchivoClientes() {
        return archivoClientes;
    }

    public void setArchivoClientes(Path archivoClientes) {
        this.archivoClientes = archivoClientes;
    }
}
