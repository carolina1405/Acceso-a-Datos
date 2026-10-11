import java.util.function.Function;

public interface JSONConverter <T>{
    Function<T, String> toJSON();
    Function<String, T>fromJSON();
}
