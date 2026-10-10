import java.util.function.Function;

public interface CSVConverter<T> {
    Function<T, String> toCSV();
    Function<String, T> fromCSV();
}
