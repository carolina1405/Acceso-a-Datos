import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RepoCliente extends Repositorio<Cliente>{

    private Path archivoClientes;
    private String cabecera;

    public RepoCliente(String directorio, String archivoClientes) {
        super(directorio);
        this.archivoClientes = Path.of(directorio, archivoClientes+".csv");
        this.cabecera = "ID,NOMBRE,TELÉFONO,MATRÍCULA";
        if(Files.notExists(this.archivoClientes)){
            try{
                Files.createFile(this.archivoClientes);

            }catch(IOException e){
                System.out.println(e.getMessage());
            }

//            try(BufferedWriter out = Files.newBufferedWriter(this.archivoClientes)){
//                out.write(cabecera);
//            }catch (IOException e){
//                System.out.println(e.getMessage());
//            }

        }
    }

    @Override
    protected void guardar(List<Cliente> clientes) {
        try(BufferedWriter out1 = Files.newBufferedWriter(archivoClientes)){
            out1.write(cabecera);
            out1.newLine();
            for(Cliente c: clientes){
                String clienteEscritura = c.toCSV();
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

        List<Cliente> clientes = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoClientes)){

            String line = in.readLine();
            line = in.readLine();
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
