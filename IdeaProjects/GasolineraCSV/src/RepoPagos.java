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
    @Override
    protected void guardar(List<PagoRepostaje> pagos) {
        PagoRepostajeConverter converter = new PagoRepostajeConverter();
        try(BufferedWriter out2 = Files.newBufferedWriter(archivoPagos)){
            out2.write(cabecera);
            out2.newLine();
            for(PagoRepostaje p : pagos){
                String pagoEscritura = converter.toCSV(p, ",");
                out2.write(pagoEscritura);
                out2.newLine();
            }

            System.out.println("===================================================================");
            System.out.println("Se han guardado los cambios de los registros de pagos.");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected List<PagoRepostaje> listar() {
        PagoRepostajeConverter converter = new PagoRepostajeConverter();
        List<PagoRepostaje> pagos = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoPagos)){

            String line = in.readLine();
            line = in.readLine();
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
        if(Files.notExists(this.archivoPagos)){
            try{
                Files.createFile(this.archivoPagos);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
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
