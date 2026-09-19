import java.io.File;
import java.util.Set;

public class RepoCliente extends Repositorio<Cliente>{

    public RepoCliente(String separador, File archivo) {
        super(separador, archivo);
    }

    @Override
    protected boolean guardar(Cliente objeto) {
        return false;
    }

    @Override
    protected void recuperar(String clave) {

    }

    @Override
    protected Set<Cliente> listar() {
        return Set.of();
    }
}
