import java.util.Objects;

public class Cliente implements Comparable<Cliente>, CSVConverter<Cliente> {
    //=========================================================
    //Atributos
    private int id;
    private String nombre, telefono, matricula;

    //=========================================================
    //Constructor
    public Cliente(int ID, String nombre, String telefono, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;
    }

    public Cliente() {
    }

    //=========================================================
    //Overrides de Comparable, equals y hashcode
    @Override
    public int compareTo(Cliente o) {
        int resultado =  this.nombre.compareTo(o.nombre);

        if (resultado != 0){
            return resultado;
        }else{
            return -(this.id-o.id);
        }
    }

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

    //=========================================================
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


    //=========================================================
    //CSV
    @Override
    public String toCSV() {
        return this.id+","+this.nombre+","+this.telefono+","+this.matricula;
    }

    @Override
    public Cliente fromCSV(String line) {
        String[] splited = line.split(",");
        return new Cliente(Integer.valueOf(splited[0]), splited[1], splited[2], splited[3]);
    }

    //=========================================================

    @Override
    public String toString() {
        return "ID: " + id +
                ", Nombre: '" + nombre + '\'' +
                ", Teléfono: '" + telefono + '\'' +
                ", Matrícula: '" + matricula + '\'' ;
    }
}
