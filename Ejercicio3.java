/**
 * Entidad de dominio que gestiona su propio estado de aprobación.
 */
public class ProyectoTesis {
    private String id;
    private String titulo;
    private String estado;
    private double notaDefensa;

    public ProyectoTesis(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.estado = "EN_EVALUACION";
    }

    /**
     * Cambia el estado de la tesis a aprobada y registra la calificación.
     * @param nota Calificación final obtenida en la defensa.
     */
    public void aprobar(double nota) {
        if (nota < 6.0) {
            throw new IllegalArgumentException("La nota mínima de aprobación es 6.");
        }
        this.notaDefensa = nota;
        this.estado = "APROBADA";
    }

    public String getId() { return id; }
    public String getEstado() { return estado; }
}

/**
 * Abstracciones para desacoplar la persistencia y la API externa.
 */
public interface IRepositorioTesis {
    ProyectoTesis buscarPorId(String id);
    void guardar(ProyectoTesis tesis);
}

public interface IServicioTituloNacional {
    void iniciarTramiteTitulo(ProyectoTesis tesis);
}

/**
 * Patrón GRASP CONTROLADOR: Intermediario entre la interfaz gráfica y la lógica del sistema.
 * Maneja el evento del sistema 'aprobarTesis'.
 */
public class ControladorDefensaTesis {
    private final IRepositorioTesis repositorioTesis;
    private final IServicioTituloNacional servicioTitulo;

    public ControladorDefensaTesis(IRepositorioTesis repositorioTesis, IServicioTituloNacional servicioTitulo) {
        this.repositorioTesis = repositorioTesis;
        this.servicioTitulo = servicioTitulo;
    }

    /**
     * Caso de uso coordinado por el controlador al recibir la acción desde la UI.
     * @param idTesis Identificador del proyecto de tesis.
     * @param notaFinal Nota otorgada por el tribunal.
     */
    public void aprobarTesis(String idTesis, double notaFinal) {
        ProyectoTesis tesis = repositorioTesis.buscarPorId(idTesis);
        tesis.aprobar(notaFinal);
        repositorioTesis.guardar(tesis);
        servicioTitulo.iniciarTramiteTitulo(tesis);
    }
}

/**
 * Capa de Vista (UI): Solo captura la interacción del usuario y delega al controlador.
 */
public class VentanaDefensaUI {
    private final ControladorDefensaTesis controlador;

    public VentanaDefensaUI(ControladorDefensaTesis controlador) {
        this.controlador = controlador;
    }

    /**
     * Manejador del clic en el botón "Aprobar Tesis".
     */
    public void onClickAprobarTesis(String idTesis, double nota) {
        controlador.aprobarTesis(idTesis, nota);
        System.out.println("UI: Tesis aprobada y trámite iniciado correctamente.");
    }
}