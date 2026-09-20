import java.io.File;
import java.nio.file.Path;
import java.util.Set;


public abstract class Repositorio<T extends Object> {
    private Path archivo;

    public Repositorio(String directorio, String fichero) {
        this.archivo = Path.of(directorio, fichero+".csv");;
    }

    /*Métodos que podrán implementar las clases hijas en función del objeto para el
            cual esté dedicado el repositorio.*/
    protected abstract void guardar(T objeto);

    protected abstract void recuperar(String clave);

    protected abstract Set<T> listar();


}
