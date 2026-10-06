import java.util.List;

public interface JSONConverter<T> {
    public abstract String toJSON(T object);
    public abstract T fromJSON(String line);
}
