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

        //Se generará un archivo cuyo nombre será pasado como parámetro.
        generarJSON(directorio, archivoClientes);
    }
    //=========================================================
    //Métodos
    //CSV
    @Override
    protected void guardarCSV(List<Cliente> clientes) {
        try(BufferedWriter out = Files.newBufferedWriter(archivoClientes)){
            ClienteConverter converter = new ClienteConverter();
            out.write(cabecera);
            out.newLine();
            for(Cliente c: clientes){
                String clienteEscritura = converter.toCSV(c, ",");
                out.write(clienteEscritura);
                out.newLine();
            }

            System.out.println("===================================================================");
            System.out.println("Se han guardado los cambios en los registros de los clientes.");
        }catch(IOException e){
            System.out.println("========================================");
            System.out.println("""
                    Ha ocurrido un error.
                    No se han guardado los cambios realizados en los
                    registros de los clientes.""");
            System.out.println("========================================");
        }
    }

    @Override
    protected List<Cliente> listarCSV() {
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
        try{
            if(Files.notExists(this.archivoClientes)){
                Files.createFile(this.archivoClientes);
            }
        }catch(IOException e){
            System.out.println("========================================");
            System.out.println("Ha ocurrido un error. " +
                    "\nNo se ha creado el fichero");
            System.out.println("========================================");
        }

    }

    //JSON
    @Override
    protected void guardarJSON(List<Cliente> clientes){
        ClienteConverter converter = new ClienteConverter();

        try(BufferedWriter out = Files.newBufferedWriter(archivoClientes)){
            converter.gsonGenerator().toJson(clientes, out);  //Esta línea es la que guarda nuestra lista en JSON


            System.out.println("Clientes migrados: "+clientes.size());
        }catch(IOException e){
            System.out.println("========================================");
            System.out.println("""
                    Ha ocurrido un error.
                    No se han guardado los cambios realizados en los
                    registros de los clientes.""");
            System.out.println("========================================");
        }
    }

    @Override
    protected List<Cliente> listarJSON(){
        ClienteConverter converter = new ClienteConverter();
        List<Cliente> clientes = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoClientes)){

            converter.gsonGenerator().fromJson(in, converter.tokenGenerator());

            if(clientes == null) clientes = new LinkedList<>();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return clientes;
    }

    @Override
    protected  void generarJSON(String directorio, String archivo){
        this.archivoClientes = Path.of(directorio, archivoClientes+".json");
        try{
            if(Files.notExists(this.archivoClientes)){
                Files.createFile(this.archivoClientes);
            }
        }catch(IOException e){
            System.out.println("========================================");
            System.out.println("Ha ocurrido un error. " +
                    "\nNo se ha creado el fichero");
            System.out.println("========================================");
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
