import java.io.File;
import java.util.Set;

public abstract class Repositorio<T extends Object> {
    private File archivo;
    private String separador;

    public Repositorio(String separador, File archivo) {
        this.separador = separador;
        this.archivo = archivo;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = separador;
    }

    /*Métodos que podrán implementar las clases hijas en función del objeto para el
        cual esté dedicado el repositorio.*/
    protected abstract boolean guardar(T objeto);

    protected abstract void recuperar(String clave);

    protected abstract Set<T> listar();


}
