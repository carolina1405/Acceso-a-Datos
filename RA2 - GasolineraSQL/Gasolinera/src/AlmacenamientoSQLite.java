import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AlmacenamientoSQLite implements Almacenamiento{

    private static final Path archivo = Path.of(System.getenv("GASOLINERA_RUTA"));
    private static final String URL = "jdbc:sqlite:" + archivo.toAbsolutePath();

    // En el constructor se prueba la conexión y se inician las tablas
    public AlmacenamientoSQLite() {
        inicializarBaseDeDatos();
    }


    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void inicializarBaseDeDatos() {
        String sqlClientes = """
            CREATE TABLE IF NOT EXISTS clientes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                telefono TEXT NOT NULL,
                matricula TEXT NOT NULL
            );
            """;

        String sqlPagos = """
            CREATE TABLE IF NOT EXISTS pagos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_cliente INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                importe REAL NOT NULL,
                litros REAL NOT NULL,
                combustible TEXT NOT NULL,
                FOREIGN KEY (id_cliente) REFERENCES clientes(id)
            );
            """;

        try (Connection con = conectar();
             Statement st = con.createStatement()) {

            System.out.println("Conexión establecida con SQLite correctamente.");
            st.execute(sqlClientes);
            st.execute(sqlPagos);
            System.out.println("Tablas de la base de datos creadas/verificadas.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    //Para probar la funcionalidad del programa
    public static void main(String[] args) {
        new AlmacenamientoSQLite();
    }



    //Aún no se han podido desarrollar
    @Override
    public List<Cliente> leerClientes() {
        return List.of();
    }

    @Override
    public void escribirClientes(List<Cliente> cliente) {

    }

    @Override
    public List<PagoRepostaje> leerPagos() {
        return List.of();
    }

    @Override
    public void escribirPagos(List<PagoRepostaje> pagos) {

    }
}
