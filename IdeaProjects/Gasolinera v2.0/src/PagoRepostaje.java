import java.time.LocalDate;
import java.util.Objects;

public class PagoRepostaje implements Comparable<PagoRepostaje>{
    //=========================================================
    //Atributos
    private int id, idCliente;
    private LocalDate fecha;
    private double importe, litros;
    private Combustible combustible;

    //=========================================================
    //Constructor
    public PagoRepostaje(int id, int idCliente, LocalDate fecha, double importe, double litros, Combustible combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }
    //=========================================================
    //Overrides de Comparable, equals y hashcode

    //Se establece un criterio de ordenación natural para los objetos de esta clase.
    @Override
    public int compareTo(PagoRepostaje o) {
        int resultado = o.fecha.compareTo(this.fecha);  //Orden descendente

        if (resultado != 0){    //Si las fechas son iguales
            return resultado;
        }else{
            return -(this.id-o.id); //Orden descendente
        }
    }

    //Se hace @Override de equals y hashcode para asegurarnos de que el programa sepa
    // en qué atributos basarse para determinar si 2 objetos de esta clase son diferentes o no.
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

    //---------------------------------------------------------
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

    public Combustible getCombustible() {
        return combustible;
    }

    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }
    //---------------------------------------------------------
    //toString
    @Override
    public String toString() {
        return id +
                "\t" + idCliente +
                "\t" + fecha +
                "\t" + String.format("%.2f €", importe) +
                "\t" + String.format("%.2f L", litros) +
                "\t" + combustible;
    }
    //=========================================================
}
