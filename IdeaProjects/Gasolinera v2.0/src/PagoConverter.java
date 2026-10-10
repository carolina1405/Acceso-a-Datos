import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class PagoConverter implements CSVConverter<PagoRepostaje>{
    String separador;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public PagoConverter(String separador) {
        this.separador = separador;
    }

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
}
