import java.time.LocalDate;
import java.util.Objects;

public class PagoRepostaje implements Comparable<PagoRepostaje>, CSVConverter<PagoRepostaje>{
    //=========================================================
    //Atributos
    private int id, idCliente;
    private LocalDate fecha;
    private double importe, litros;
    private String combustible;

    //=========================================================
    //Constructor
    public PagoRepostaje(int id, int idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }


    //=========================================================
    //Overrides de Comparable, equals y hashcode
    @Override
    public int compareTo(PagoRepostaje o) {
        int resultado = this.fecha.compareTo(o.fecha);

        if (resultado != 0){
            return resultado;
        }else{
            return this.id-o.id;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this.id == ((PagoRepostaje)o).id){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int getId() {
        return id;
    }

    //=========================================================
    //G/S
    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    //=========================================================
    //CSV
    @Override
    public String toCSV(PagoRepostaje obj) {
        return this.id+","+this.idCliente+","+this.fecha+","+this.importe+","+this.litros+","+this.combustible;
    }

    @Override
    public PagoRepostaje fromCSV(String line) {
        String[] splited = line.split(",");
        return new PagoRepostaje(Integer.valueOf(splited[0]), Integer.valueOf(splited[1]), LocalDate.parse(splited[2]),
                Double.valueOf(splited[3]), Double.valueOf(splited[4]), splited[5]);
    }
}
