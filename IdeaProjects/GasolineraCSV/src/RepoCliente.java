import java.util.Set;

public class RepoCliente extends Repositorio<Cliente>{

    public RepoCliente(String directorio, String fichero) {
        super(directorio, fichero);
    }

    @Override
    protected void guardar(Cliente objeto) {
        System.out.println("Se guarda un cliente");
    }

    @Override
    protected void recuperar(String clave) {
        System.out.println("Se recupera un cliente");
    }

    @Override
    protected Set<Cliente> listar() {
        return Set.of();
    }
}
