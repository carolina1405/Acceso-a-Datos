public interface CSVConverter<T extends Object> {
    //Interfaz diseñada para que cada clase pueda convertirse a formato CSV según sus características y reconvertirse en un objeto.

    /*Tener en cuenta que esta interfaz solo sirve para que un objeto
    se convierta en un String almacenable en un documentoCSV o para reconvertir
    un String recuperado de un documento en un objeto de la clase correspondiente,
    por lo que no tiene como función guardar ni sacar información de un documento.
    Esa función la cumplirán otras clases específicamente diseñadas para ello.*/

    String toCSV();
    T fromCSV(String line);
}
