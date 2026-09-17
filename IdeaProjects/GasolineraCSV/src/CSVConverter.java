public interface CSVConverter<T extends Object> {
    //Interfaz diseñada para que cada clase pueda convertirse a formato CSV según sus características y reconvertirse en un objeto.
    String toCSV(T obj);
    T fromCSV(String line);
}
