import java.util.List;

public interface Almacenamiento {
    //Interfaz que establece los métodos que deberán implementar las clases destinadas al almacenamiento, cada una a su manera
    //dependiendo del formato de almacenamiento deseado, de manera que solo haya que cambiar una sola línea en el menú.
    List<Cliente> leerClientes();
    void escribirClientes(List<Cliente> cliente);

    List<PagoRepostaje> leerPagos();
    void escribirPagos(List<PagoRepostaje> pagos);
}
