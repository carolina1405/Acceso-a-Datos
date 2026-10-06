import java.util.Objects;

public class Cliente implements Comparable<Cliente> {
    //=========================================================
    //Atributos
    private int id;
    private String nombre, telefono, matricula;

    //=========================================================
    //Constructor
    public Cliente(int id, String nombre, String telefono, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;
    }

    //=========================================================
    //Overrides de Comparable, equals y hashcode

    //Se establece un criterio de ordenación natural para los objetos de esta clase.
    @Override
    public int compareTo(Cliente o) {
        int resultado =  this.nombre.compareToIgnoreCase(o.nombre); //Orden ascendente

        if (resultado != 0){
            return resultado;
        }else{ //Si los nombres son iguales
            return this.id-o.id; //Orden ascendente
        }
    }

    //Se hace @Override de equals y hashcode para asegurarnos de que el programa sepa en qué atributos basarse para determinar si 2 objetos de esta clase son  diferentes o no.
    @Override
    public boolean equals(Object o) {
        if(this.id == ((Cliente)o).id){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //---------------------------------------------------------
    //G/S
    public int getID() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    //---------------------------------------------------------
    //toString
    @Override
    public String toString() {
        return "ID: " + id +
                " | Nombre: '" + nombre + '\'' +
                " | Teléfono: '" + telefono + '\'' +
                " | Matrícula: '" + matricula + '\'' ;
    }
    //=========================================================


}
