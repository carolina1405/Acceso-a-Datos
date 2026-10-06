import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RepoPagos extends Repositorio<PagoRepostaje>{

    //=========================================================
    //Atributos
    private Path archivoPagos;
    private String cabecera;

    //=========================================================
    //Constructor
    public RepoPagos(String directorio, String archivoPagos) {
        super(directorio);

        //Se generará un archivo cuyo nombre será pasado como parámetro
        generarCSV(directorio, archivoPagos);
    }
    //=========================================================
    //Métodos
    //CSV
    @Override
    protected void guardarCSV(List<PagoRepostaje> pagos) {
        PagoRepostajeConverter converter = new PagoRepostajeConverter();
        try(BufferedWriter out = Files.newBufferedWriter(archivoPagos)){
            out.write(cabecera);
            out.newLine();
            for(PagoRepostaje p : pagos){
                String pagoEscritura = converter.toCSV(p, ",");
                out.write(pagoEscritura);
                out.newLine();
            }

            System.out.println("===================================================================");
            System.out.println("Se han guardado los cambios en los registros de pagos.");
        }catch(IOException e){
            System.out.println("========================================");
            System.out.println("""
                    Ha ocurrido un error.
                    No se han guardado los cambios realizados en los
                    registros de los pagos.""");
            System.out.println("========================================");
        }
    }

    @Override
    protected List<PagoRepostaje> listarCSV() {
        PagoRepostajeConverter converter = new PagoRepostajeConverter();
        List<PagoRepostaje> pagos = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoPagos)){

            in.readLine();
            String line = in.readLine();
            while(line != null){
                pagos.add(converter.fromCSV(line, ","));
                line = in.readLine();

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return pagos;
    }

    @Override
    protected void generarCSV(String directorio, String archivoPagos){
        this.archivoPagos = Path.of(directorio, archivoPagos+".csv");
        this.cabecera = "ID,ID CLIENTE,FECHA,IMPORTE,LITROS,COMBUSTIBLE";
        try{
            if(Files.notExists(this.archivoPagos)){
                Files.createFile(this.archivoPagos);
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
    protected void guardarJSON(List<PagoRepostaje> pagos){
        PagoRepostajeConverter converter = new PagoRepostajeConverter();  //Esta línea es la que guarda nuestra lista en JSON

        try(BufferedWriter out = Files.newBufferedWriter(archivoPagos)){
            converter.gsonGenerator().toJson(pagos, out);
            System.out.println("Pagos migrados: "+pagos.size());
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
    protected List<PagoRepostaje> listarJSON() {
        PagoRepostajeConverter converter = new PagoRepostajeConverter();
        List<PagoRepostaje> pagos = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoPagos)){

            converter.gsonGenerator().fromJson(in, converter.tokenGenerator());

            if(pagos == null) pagos = new LinkedList<>();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return pagos;
    }


    @Override
    protected  void generarJSON(String directorio, String archivo){
        this.archivoPagos = Path.of(directorio, archivoPagos+".json");
        try{
            if(Files.notExists(this.archivoPagos)){
                Files.createFile(this.archivoPagos);
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
    public Path getArchivoPagos() {
        return archivoPagos;
    }

    public void setArchivoPagos(Path archivoPagos) {
        this.archivoPagos = archivoPagos;
    }
    //=========================================================
}
