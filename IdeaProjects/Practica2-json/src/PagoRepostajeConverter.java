import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedList;


public class PagoRepostajeConverter implements CSVConverter<PagoRepostaje>, JSONConverter{
    //Clase encargada de cambiar el formato de un objeto de la clase PagoRepostaje o convertir otro objeto en uno de dicha clase.

    //CSV

    @Override
    public String toCSV(PagoRepostaje pago, String separador) {
        return pago.getId()+separador+pago.getIdCliente()+separador+pago.getFecha()+separador+pago.getImporte()+separador+pago.getLitros()+separador+pago.getCombustible();
    }

    @Override
    public PagoRepostaje fromCSV(String line, String separador) {
        String[] splited = line.split(separador);
        return new PagoRepostaje(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), LocalDate.parse(splited[2]),
                Double.parseDouble(splited[3]), Double.parseDouble(splited[4]), Combustible.valueOf(splited[5].toUpperCase()));
    }

    //JSON

    @Override
    public Gson gsonGenerator() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public Type tokenGenerator() {
        return new TypeToken<LinkedList<PagoRepostaje>>(){}.getType();
    }
}
