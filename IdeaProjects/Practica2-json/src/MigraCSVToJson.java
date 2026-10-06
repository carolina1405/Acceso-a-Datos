import java.util.List;
import java.util.Scanner;

public class MigraCSVToJson {
    public static void migrarDatosCSV(String directorio, RepoCliente repoClienteJSON,  RepoPagos repoPagosJSON){
        RepoCliente repoClientesCSV = new RepoCliente(directorio, "Clientes");
        RepoPagos repoPagosCSV = new RepoPagos(directorio, "Pagos");

        List<Cliente> clientes = repoClientesCSV.listarCSV();
        List<PagoRepostaje> pagos = repoPagosCSV.listarCSV();

        repoClienteJSON.guardarJSON(clientes);
        repoPagosCSV.guardarJSON(pagos);

        System.out.println("Se han migrado los datos.");
    }

}
