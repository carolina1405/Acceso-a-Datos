import java.util.function.Function;

public class ClienteConverter implements CSVConverter<Cliente>{

    String separador;

    public ClienteConverter(String separador) {
        this.separador = separador;
    }


    @Override
    public Function<Cliente, String> toCSV() {
        return cliente -> cliente.getID()
                +separador+cliente.getNombre()
                +separador+cliente.getTelefono()
                +separador+cliente.getMatricula();
    }

    @Override
    public Function<String, Cliente> fromCSV() {
        return line -> {
            String[] separado = line.split(separador);
            return new Cliente(Integer.parseInt(separado[0]),
                    separado[1], separado[2], separado[3]);
        };
    }
}
