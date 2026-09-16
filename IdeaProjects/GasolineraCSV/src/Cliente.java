import java.util.Objects;

public class Cliente implements Comparable<Cliente>, CSVConverter<Cliente> {
    //=========================================================
    //Atributos
    private final int ID;
    private String nombre, telefono, matricula;

    //=========================================================
    //Constructor
    public Cliente(int ID, String nombre, String telefono, String matricula) {
        this.ID = ID;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;
    }

    //=========================================================
    //Overrides de Comparable, equals y hashcode
    @Override
    public int compareTo(Cliente o) {
        int resultado =  this.nombre.compareTo(o.nombre);

        if (resultado != 0){
            return resultado;
        }else{
            return -(this.ID-o.ID);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return ID == cliente.ID && Objects.equals(matricula, cliente.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, matricula);
    }

    //=========================================================
    //G/S
    public int getID() {
        return ID;
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
    public String toCSV(Object obj) {
        return this.ID+","+this.nombre+","+this.telefono+","+this.matricula;
    }

    @Override
    public Cliente fromCSV(String line) {
        String[] splited = line.split(",");
        return new Cliente(Integer.valueOf(splited[0]), splited[1], splited[2], splited[3]);
    }
}
