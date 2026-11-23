import java.time.LocalDate;
import java.util.function.Function;

public class ClienteConverter implements CSVConverter<Cliente>, JSONConverter<Cliente>{
    //Clase destinada a convertir objetos Cliente en cualquier formato que se desee implementar.
    //También sirve para reconvertir otros formatos en un objeto Cliente.
    String separador;

    public ClienteConverter(String separador) {
        this.separador = separador;
    }


    //CSV
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


    //JSON
    @Override
    public Function<Cliente, String> toJSON() {
        return cliente -> "\t\t{\"id\": "+cliente.getID()
                +separador+"\"nombre\": "+"\""+cliente.getNombre()+"\""
                +separador+"\"telefono\": "+"\""+cliente.getTelefono()+"\""
                +separador+"\"matricula\": "+"\""+cliente.getMatricula()+"\"}";
    }

    @Override
    public Function<String, Cliente> fromJSON() {
        return line -> {
            String lineLimpia = line.replace("\t", "")
                                    .replace("{", "")
                                    .replace("}", "")
                                    .replace("\"", "")
                                    .trim();
            if(lineLimpia.endsWith(",")){
                lineLimpia = lineLimpia.substring(0, lineLimpia.length()-1);
            }

            String[] datos = lineLimpia.split(",");

            return new Cliente(Integer.parseInt(datos[0].split(":")[1].trim()),
                    datos[1].split(":")[1].trim(),
                    datos[2].split(":")[1].trim(),
                    datos[3].split(":")[1].trim());
        };
    }


}
