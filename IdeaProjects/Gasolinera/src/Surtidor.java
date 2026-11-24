import java.util.Objects;

public class Surtidor implements Comparable<Surtidor> {
    private final Gasolinera gasolinera;
    private final String codigoEquipo;
    private final int numeroVisible;
    private final Combustible combustible;
    private EstadoServicio estadoServicio;

    private Surtidor(Gasolinera gasolinera, String codigoEquipo, int numeroVisible,
                     Combustible combustible, EstadoServicio estadoServicio) {
        try {
            this.gasolinera = Objects.requireNonNull(gasolinera, "El surtidor necesita una gasolinera");
            if (codigoEquipo == null || codigoEquipo.trim().isEmpty()) {
                throw new IllegalArgumentException("El código de equipo es obligatorio");
            }
            if (numeroVisible < 1) {
                throw new IllegalArgumentException("El número visible debe ser positivo");
            }
            this.codigoEquipo = codigoEquipo;
            this.numeroVisible = numeroVisible;
            this.combustible = Objects.requireNonNull(combustible, "El combustible es obligatorio");
            this.estadoServicio = Objects.requireNonNull(estadoServicio, "El estado de servicio es obligatorio");
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("No se pudo crear el surtidor (" + e.getClass().getSimpleName()
                    + "): " + e.getMessage());
            throw e;
        }
    }

    static Surtidor crearPara(Gasolinera gasolinera, String codigoEquipo, int numeroVisible,
                              Combustible combustible, EstadoServicio estadoServicio) {
        return new Surtidor(gasolinera, codigoEquipo, numeroVisible, combustible, estadoServicio);
    }

    // Orden ascendente por código de equipo y después por número visible.
    @Override
    public int compareTo(Surtidor otro) {
        int comparacionCodigo = codigoEquipo.compareTo(otro.codigoEquipo);
        return comparacionCodigo != 0
                ? comparacionCodigo
                : Integer.compare(numeroVisible, otro.numeroVisible);
    }

    // El código de equipo es único en toda la empresa e identifica al surtidor.
    @Override
    public boolean equals(Object objeto) {
        return objeto instanceof Surtidor
                && codigoEquipo.equals(((Surtidor) objeto).codigoEquipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoEquipo);
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public Gasolinera getGasolinera() {
        return gasolinera;
    }

    public int getNumeroVisible() {
        return numeroVisible;
    }

    public Combustible getCombustible() {
        return combustible;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = Objects.requireNonNull(estadoServicio, "El estado de servicio es obligatorio");
    }

    @Override
    public String toString() {
        return codigoEquipo + "\t" + numeroVisible + "\t" + combustible + "\t" + estadoServicio;
    }
}
