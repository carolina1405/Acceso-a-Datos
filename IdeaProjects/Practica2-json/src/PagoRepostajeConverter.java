import java.time.LocalDate;

public class PagoRepostajeConverter implements CSVConverter<PagoRepostaje>{
    //Clase encargada de cambiar el formato de un objeto de la clase PagoRepostaje o convertir otro objeto en uno de dicha clase.

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
}
