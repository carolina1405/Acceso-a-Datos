public interface CSVConverter<T extends Object> {
    //Interfaz diseñada para que cada objeto de una clase pueda convertirse a formato CSV
    //según sus características y reconvertirse en un objeto.


    public abstract String toCSV(T Objeto, String separador); //Convierte a formato CSV (un String con los atributos del objeto separados por comas ",").
    public abstract T fromCSV(String line, String separador); //Convierte un String recuperado del documento en un objeto de la clase correspondiente.
}
