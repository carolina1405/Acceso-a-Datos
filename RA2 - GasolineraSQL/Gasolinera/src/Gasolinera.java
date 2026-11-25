import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Estación del agregado Gasolinera-Ficha-Surtidores.
 *
 * Una estación se crea con su ficha de facturación y al menos un surtidor. El alta se realiza
 * mediante darDeAlta(...), evitando publicar una estación sin esos elementos obligatorios.
 */
public class Gasolinera implements Comparable<Gasolinera> {
    //=========================================================
    //Atributos
    private int codigoInterno;
    private String nombreComercial, direccion, telefono;
    private LocalDate fechaApertura;
    private Ficha ficha; // Asignada una sola vez, dentro de darDeAlta(...); nunca expuesta para modificar.
    private final HashMap<Integer, Surtidor> surtidores = new HashMap<>();

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
                                       String textoPie, String codigoEquipoInicial,
                                       int numeroVisibleInicial, Combustible combustibleInicial,
                                       EstadoServicio estadoInicial) {
        Gasolinera estacion = new Gasolinera(codigoInterno, nombreComercial, direccion,
                telefono, fechaApertura);
        estacion.agregarSurtidor(codigoEquipoInicial, numeroVisibleInicial,
                combustibleInicial, estadoInicial);
        Ficha ficha = Ficha.crearPara(estacion, codigoFicha, serie, siguienteNumero, textoPie);
        estacion.ficha = ficha;
        return estacion;
    }

    public void agregarSurtidor(String codigoEquipo, int numeroVisible, Combustible combustible,
                                EstadoServicio estadoServicio) {
        if (surtidores.containsKey(numeroVisible)) {
            throw new IllegalArgumentException("Ya existe un surtidor con ese número visible en la estación");
        }
        Surtidor surtidor = Surtidor.crearPara(this, codigoEquipo, numeroVisible,
                combustible, estadoServicio);
        surtidores.put(numeroVisible, surtidor);
    }

    public Surtidor retirarSurtidor(int numeroVisible) {
        Surtidor surtidor = surtidores.get(numeroVisible);
        if (surtidor == null) {
            throw new IllegalArgumentException("No existe un surtidor con ese número visible en la estación");
        }
        if (surtidores.size() == 1) {
            throw new IllegalStateException("No se puede retirar el último surtidor de la estación");
        }
        return surtidores.remove(numeroVisible);
    }

    public Map<Integer, Surtidor> getSurtidores() {
        return Collections.unmodifiableMap(new HashMap<>(surtidores));
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
