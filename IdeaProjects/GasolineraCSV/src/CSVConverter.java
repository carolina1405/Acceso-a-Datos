public interface CSVConverter<T> {
    //Interfaz diseñada para que cada clase pueda convertirse a formato CSV según sus características y reconvertirse en un objeto.
    public String toCSV(Object obj);
    public Object fromCSV(String line);
}
