import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RepoPagos extends Repositorio<PagoRepostaje>{

    private Path archivoPagos;


    public RepoPagos(String directorio, String archivoPagos) {
        super(directorio);
        this.archivoPagos = Path.of(archivoPagos+".csv");
    }

    @Override
    protected void guardar(PagoRepostaje objeto) {
        System.out.println("Se guarda un pago");
    }

    @Override
    protected List<PagoRepostaje> listar() {
        List<PagoRepostaje> pagos = new LinkedList<>();

        try(BufferedReader in = Files.newBufferedReader(archivoPagos)){

            String line = in.readLine();
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
