import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;



public abstract class Repositorio<T extends Object> {
    //=========================================================
    //Atributos
    private Path directorio;
    //=========================================================
    //Constructor
    public Repositorio(String directorio) {
        //Se genera un directorio cuyo nombre será especificado como parámetro.
        this.directorio = Path.of(directorio);;

        try{
            if(Files.notExists(this.directorio)){
                Files.createDirectories(this.directorio);
            }
        }catch(IOException e){
            System.out.println("========================================");
            System.out.println("""
                    Ha ocurrido un error.
                    No se ha creado el directorio""");
            System.out.println("========================================");
        }

    }
    //=========================================================
    /*Métodos que podrán implementar las clases hijas en función del objeto para el
      cual esté dedicado el repositorio.*/
    protected abstract void guardarCSV(List<T> lista);
    protected abstract List<T> listarCSV();


    /*Este método generará el archivo CSV preparado para utilizarlo.
      Repositorio tendrá un método para el tipo de archivo que desee generar con sus especificaciones en cada caso,
      por ejemplo un CSV tiene una extensión .csv y en este caso tendrá una cabecera.
      Si necesitamos crear otro tipo de archivo que no esté especificado (e incluso utilizar una base de datos) solo tendremos que crear el método correspondiente e implementarlo en el constructor de Repositorio.*/
      protected abstract void generarCSV(String directorio, String archivo);
    //=========================================================
}
