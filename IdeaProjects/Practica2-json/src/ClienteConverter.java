import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedList;


public class ClienteConverter implements CSVConverter<Cliente>, JSONConverter{
    //Clase encargada de cambiar el formato de un objeto de la clase Cliente o convertir otro objeto en uno de dicha clase.

    //CSV
    @Override
    public String toCSV(Cliente cliente, String separador) {
        return cliente.getID()+separador+cliente.getNombre()+separador+cliente.getTelefono()+separador+cliente.getMatricula();
    }

    @Override
    public Cliente fromCSV(String line, String separador) {
        String[] splited = line.split(separador);
        return new Cliente(Integer.parseInt(splited[0]), splited[1], splited[2], splited[3]);
    }

    //JSON
    @Override
    public Gson gsonGenerator() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public Type tokenGenerator() {
        return new TypeToken<LinkedList<Cliente>>(){}.getType();
    }
}
