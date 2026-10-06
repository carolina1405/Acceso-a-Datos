import com.google.gson.Gson;

import java.lang.reflect.Type;

public interface JSONConverter{
    //Aprovechando la clase Gson podremos utilizar esta interfaz para crear un objeto Gson y otro Type que
    //se utilizarán para guardar y recuperar datos en formato JSON de forma sencilla.

    //Para generar el token se tendrá en cuenta el tipo de lista para el cual estará destinado el token, de manera que podremos
    //recuperar listas enteras en una sola línea de código para un objeto determinado.
    Gson gsonGenerator();
    Type tokenGenerator();
}
