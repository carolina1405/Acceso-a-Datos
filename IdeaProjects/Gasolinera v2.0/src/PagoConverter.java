import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class PagoConverter implements CSVConverter<PagoRepostaje>, JSONConverter<PagoRepostaje>{
    //Equivalente de ClienteConverter para pagos.
    String separador;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Para asegurarnos de que las fechas se
                                                                                //mantengan en el formato deseado tanto en la lectura
                                                                                //como en el guardado.


    public PagoConverter(String separador) {
        this.separador = separador;
    }

    //CSV
    @Override
    public Function<PagoRepostaje, String> toCSV() {
        return pago ->pago.getId()+separador
            +pago.getIdCliente()+separador
            +pago.getFecha().format(dateFormatter)+separador
            +pago.getImporte()+separador
            +pago.getLitros()+separador
            +pago.getCombustible();

    }

    @Override
    public Function<String, PagoRepostaje> fromCSV() {
        return line ->{
            String[] separado = line.split(separador);

            return new PagoRepostaje(Integer.parseInt(separado[0]),
                    Integer.parseInt(separado[1]),
                    LocalDate.parse(separado[2], dateFormatter),
                    Double.parseDouble(separado[3]),
                    Double.parseDouble(separado[4]),
                    Combustible.valueOf(separado[5]));
        };
    }

    //JSON
    @Override
    public Function<PagoRepostaje, String> toJSON() {
        return pago -> "\t\t{\"id\": "+pago.getId()
                +separador+"\"idCliente\": "+pago.getIdCliente()
                +separador+"\"fecha\": "+"\""+pago.getFecha()+"\""
                +separador+"\"importe\": "+String.format("%.2f", pago.getImporte())
                +separador+"\"litros\": "+String.format("%.2f", pago.getLitros())
                +separador+"\"combustible\": "+"\""+pago.getCombustible()+"\"}";
    }

    @Override
    public Function<String, PagoRepostaje> fromJSON() {
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

            return new PagoRepostaje(Integer.parseInt(datos[0].split(":")[1].trim()),
                    Integer.parseInt(datos[1].split(":")[1].trim()),
                    LocalDate.parse(datos[2].split(":")[1].trim(), dateFormatter),
                    Double.parseDouble(datos[3].split(":")[1].trim()),
                    Double.parseDouble(datos[4].split(":")[1].trim()),
                    Combustible.valueOf(datos[5].split(":")[1].trim()));
        };
    }
}
