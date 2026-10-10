import java.util.List;

public interface Almacenamiento {
    List<Cliente> leerClientes();
    void escribirClientes(List<Cliente> cliente);

    List<PagoRepostaje> leerPagos();
    void escribirPagos(List<PagoRepostaje> pagos);
}
