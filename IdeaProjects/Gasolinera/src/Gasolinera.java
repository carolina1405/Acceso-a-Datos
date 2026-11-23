import java.time.LocalDate;
import java.util.Objects;

/**
 * Estación del agregado Gasolinera-Ficha.
 *
 * Una estación existe siempre con exactamente una ficha de facturación. Para garantizarlo,
 * el constructor es privado y la única forma de obtener una instancia es darDeAlta(...).
 * Ese método crea la estación y su ficha en una sola operación: los estados intermedios
 * (estación aún sin ficha) no son visibles desde fuera, por lo que no puede quedar un alta a medias.
 */
public class Gasolinera implements Comparable<Gasolinera> {
    //=========================================================
    //Atributos
    private int codigoInterno;
    private String nombreComercial, direccion, telefono;
    private LocalDate fechaApertura;
    private Ficha ficha; // Asignada una sola vez, dentro de darDeAlta(...); nunca expuesta para modificar.

    //=========================================================
    //Constructor y alta del agregado

    //Privado: nadie puede crear una estación sin pasar por la fábrica que también crea su ficha.
    private Gasolinera(int codigoInterno, String nombreComercial, String direccion,
                       String telefono, LocalDate fechaApertura) {
        this.codigoInterno = codigoInterno;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaApertura = fechaApertura;
    }

    /**
     * Alta atómica de la estación junto con su ficha. O se construyen los dos objetos
     * o no se publica ninguno; no existe un setter para la ficha.
     */
    public static Gasolinera darDeAlta(int codigoInterno, String nombreComercial, String direccion,
                                       String telefono, LocalDate fechaApertura,
                                       String codigoFicha, String serie, int siguienteNumero,
                                       String textoPie) {
        Gasolinera estacion = new Gasolinera(codigoInterno, nombreComercial, direccion,
                telefono, fechaApertura);
        Ficha ficha = Ficha.crearPara(estacion, codigoFicha, serie, siguienteNumero, textoPie);
        estacion.ficha = ficha;
        return estacion;
    }

    //=========================================================
    //Overrides de Comparable, equals y hashcode

    //Ordenación natural por código interno (ascendente), igual que el criterio de igualdad.
    @Override
    public int compareTo(Gasolinera o) {
        return Integer.compare(this.codigoInterno, o.codigoInterno);
    }

    //Dos gasolineras son la misma si coincide su código interno, que es único en la empresa.
    @Override
    public boolean equals(Object o) {
        return o instanceof Gasolinera
                && codigoInterno == ((Gasolinera) o).codigoInterno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoInterno);
    }

    //---------------------------------------------------------
    //G/S
    public int getCodigoInterno() {
        return codigoInterno;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Ficha getFicha() {
        return ficha;
    }

    //---------------------------------------------------------
    //toString
    @Override
    public String toString() {
        return codigoInterno +
                "\t" + nombreComercial +
                "\t" + direccion +
                "\t" + telefono +
                "\t" + fechaApertura;
    }
    //=========================================================
}
