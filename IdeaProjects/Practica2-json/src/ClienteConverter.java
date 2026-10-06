import java.util.List;

public class ClienteConverter implements CSVConverter<Cliente>, JSONConverter<Cliente>{
    //Clase encargada de cambiar el formato de un objeto de la clase Cliente o convertir otro objeto en uno de dicha clase.

    @Override
    public String toCSV(Cliente cliente, String separador) {
        return cliente.getID()+separador+cliente.getNombre()+separador+cliente.getTelefono()+separador+cliente.getMatricula();
    }

    @Override
    public Cliente fromCSV(String line, String separador) {
        String[] splited = line.split(separador);
        return new Cliente(Integer.parseInt(splited[0]), splited[1], splited[2], splited[3]);
    }

    @Override
    public String toJSON(Cliente object) {
        String line = "{\"id\": "+object.getID()+"," +
                "\"nombre\": \""+object.getNombre()+"\"," +
                "\"telefono\": \""+object.getTelefono()+"\"," +
                "\"matricula\": \""+object.getMatricula()+"\"}";

        return line;
    }

    @Override
    public Cliente fromJSON(String line) {
        line = line.replace("{", "");
        line = line.replace("}", "");
        line = line.replace("\"clientes\": ", "");
        line = line.replace("[", "");
        line = line.replace("]", "");
        line = line.replace("]", "");
        line = line.replace("\"", "");
        line = line.replace(":", "");
        line = line.replace("id", "");
        line = line.replace("nombre", "");
        line = line.replace("telefono", "");
        line = line.replace("matricula", "");
        line = line.replace("\\s+", "");
        String[] splited = line.split(",");
        return new Cliente(Integer.valueOf(splited[0]), splited[1], splited[2], splited[3]);

    }
}
