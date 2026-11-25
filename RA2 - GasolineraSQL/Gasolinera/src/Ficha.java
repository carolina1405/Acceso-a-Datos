import java.util.Objects;

/**
 * Ficha de configuración de facturación de una estación.
 *
 * Es parte de una composición fuerte: su constructor es privado y la única vía de creación
 * es {@link #crearPara(Gasolinera, String, String, int, String)}, invocada desde el alta
 * de Gasolinera. Por eso no puede existir una ficha independiente ni compartirse:
 * guarda un dueño final y rechaza crearse para una estación que ya tenga ficha.
 */
public class Ficha implements Comparable<Ficha> {
    //=========================================================
    //Atributos
    private final String codigo;
    private final Gasolinera estacion; // Dueño único; no hay setter que permita cambiarlo.
    private String serie;
    private int siguienteNumero;
    private String textoPie;

    //=========================================================
    //Constructor y creación vinculada

    private Ficha(Gasolinera estacion, String codigo, String serie,
                  int siguienteNumero, String textoPie) {
        this.estacion = Objects.requireNonNull(estacion, "La ficha necesita una estación");
        this.codigo = Objects.requireNonNull(codigo, "El código de configuración es obligatorio");
        this.serie = serie;
        this.siguienteNumero = siguienteNumero;
        this.textoPie = textoPie;
    }

    /**
     * Crea la ficha ya enlazada a su estación. Solo se usa en el alta de Gasolinera:
     * una estación publicada siempre tiene ficha, así que cualquier intento posterior
     * de crear otra para la misma estación se rechaza.
     */
    public static Ficha crearPara(Gasolinera estacion, String codigo, String serie,
                                  int siguienteNumero, String textoPie) {
        try {
            Objects.requireNonNull(estacion, "La ficha necesita una estación");
            if (estacion.getFicha() != null) {
                throw new IllegalStateException("La estación ya tiene una ficha de facturación");
            }
            return new Ficha(estacion, codigo, serie, siguienteNumero, textoPie);
        } catch (NullPointerException | IllegalStateException e) {
            System.err.println("No se pudo crear la ficha: " + e.getMessage());
            // Se relanza para que quien llamó al método también pueda gestionar el error.
            throw e;
        }
    }

    //=========================================================
    //Overrides de Comparable, equals y hashcode

    //Ordenación natural por código de configuración (ascendente), igual que el criterio de igualdad.
    @Override
    public int compareTo(Ficha o) {
        return this.codigo.compareTo(o.codigo);
    }

    //Dos fichas son la misma si coincide su código de configuración, que identifica la ficha ante soporte.
    @Override
    public boolean equals(Object o) {
        return o instanceof Ficha
                && codigo.equals(((Ficha) o).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    //---------------------------------------------------------
    //G/S
    public String getCodigo() {
        return codigo;
    }

    public Gasolinera getEstacion() {
        return estacion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getSiguienteNumero() {
        return siguienteNumero;
    }

    public void setSiguienteNumero(int siguienteNumero) {
        this.siguienteNumero = siguienteNumero;
    }

    public String getTextoPie() {
        return textoPie;
    }

    public void setTextoPie(String textoPie) {
        this.textoPie = textoPie;
    }

    //---------------------------------------------------------
    //toString
    @Override
    public String toString() {
        return codigo +
                "\t" + serie +
                "\t" + siguienteNumero +
                "\t" + textoPie;
    }
    //=========================================================
}
