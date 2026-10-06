public class ClienteConverter implements CSVConverter<Cliente>{
    //Clase encargada de cambiar el formato de un objeto de la clase Cliente o convertir otro objeto en uno de dicha clase.

    @Override
    public String toCSV(Cliente cliente, String separador) {
        return cliente.getID()+separador+cliente.getNombre()+separador+cliente.getTelefono()+separador+cliente.getMatricula();
    }

    @Override
    public Cliente fromCSV(String line, String separador) {
        String[] splited = line.split(separador);
        return new Cliente(Integer.parseInt(splited[0]), splited[1], splited[2], splited[3]);
    }

}
