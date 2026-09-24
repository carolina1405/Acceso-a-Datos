import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;


public abstract class Repositorio<T extends Object> {
    private Path directorio;

    public Repositorio(String directorio) {
        this.directorio = Path.of(directorio);;
    }

    /*Métodos que podrán implementar las clases hijas en función del objeto para el
            cual esté dedicado el repositorio.*/
    protected abstract void guardar(T objeto);
    protected abstract List<T> listar();


}
