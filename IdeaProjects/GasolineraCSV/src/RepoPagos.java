import java.io.File;
import java.util.Set;

public class RepoPagos extends Repositorio<PagoRepostaje>{

    public RepoPagos(String separador, File archivo) {
        super(separador, archivo);
    }

    @Override
    protected boolean guardar(PagoRepostaje objeto) {
        return false;
    }

    @Override
    protected void recuperar(String clave) {

    }

    @Override
    protected Set<PagoRepostaje> listar() {
        return Set.of();
    }
}
