import java.util.Set;

public class RepoPagos extends Repositorio<PagoRepostaje>{

    public RepoPagos(String directorio, String fichero) {
        super(directorio, fichero);
    }

    @Override
    protected void guardar(PagoRepostaje objeto) {
        System.out.println("Se guarda un pago");
    }

    @Override
    protected Set<PagoRepostaje> listar() {
        return Set.of();
    }
}
