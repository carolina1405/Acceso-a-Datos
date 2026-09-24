import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;


public abstract class Repositorio<T extends Object> {
    private Path directorio;

    public Repositorio(String directorio) {
        this.directorio = Path.of(directorio);;
        if(Files.notExists(this.directorio)){
            try{
                Files.createDirectories(this.directorio);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /*Métodos que podrán implementar las clases hijas en función del objeto para el
            cual esté dedicado el repositorio.*/
    protected abstract void guardar(List<T> lista);
    protected abstract List<T> listar();


}
