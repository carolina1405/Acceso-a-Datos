import java.util.function.Function;

public interface CSVConverter<T> {
    //Aprendí a usar Function para encadenar las conversiones en streams con mis propias expresiones lambda :)
    //Habrá una clase convertidora por cada tipo de objeto que se desee almacenar, la cual implementará esta y
    //cualquier otra interfaz de conversión para leer o guardar información.
    Function<T, String> toCSV();
    Function<String, T> fromCSV();
}
