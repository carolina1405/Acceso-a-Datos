import java.time.LocalDate;
import java.util.Objects;

public class PagoRepostaje implements Comparable<PagoRepostaje>{
    private int id, idCliente;
    private LocalDate fecha;
    private double importe, litros;
    private String combustible;

    public PagoRepostaje(int id, int idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }


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
        if (o == null || getClass() != o.getClass()) return false;
        PagoRepostaje that = (PagoRepostaje) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int getId() {
        return id;
    }

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
}
