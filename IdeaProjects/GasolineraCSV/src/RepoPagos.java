import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RepoPagos extends Repositorio<PagoRepostaje>{

    private Path archivoPagos;
    private String cabecera;

    public RepoPagos(String directorio, String archivoPagos) {
        super(directorio);
        this.archivoPagos = Path.of(directorio, archivoPagos+".csv");
        this.cabecera = "ID,ID CLIENTE,FECHA,IMPORTE,LITROS,COMBUSTIBLE";
        if(Files.notExists(this.archivoPagos)){
            try{
                Files.createFile(this.archivoPagos);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }

//            try(BufferedWriter out = Files.newBufferedWriter(this.archivoPagos)){
//                out.write(cabecera);
//            }catch (IOException e){
//                System.out.println(e.getMessage());
//            }
        }
    }

    @Override
    protected void guardar(List<PagoRepostaje> pagos) {
        try(BufferedWriter out2 = Files.newBufferedWriter(archivoPagos)){
            out2.write(cabecera);
            out2.newLine();
            for(PagoRepostaje p : pagos){
                String pagoEscritura = p.toCSV();
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
        List<PagoRepostaje> pagos = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoPagos)){

            String line = in.readLine();
            line = in.readLine();
            while(line != null){
                PagoRepostaje pago = new PagoRepostaje();
                pagos.add(pago.fromCSV(line));
                line = in.readLine();

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return pagos;
    }

    public Path getArchivoPagos() {
        return archivoPagos;
    }

    public void setArchivoPagos(Path archivoPagos) {
        this.archivoPagos = archivoPagos;
    }
}
