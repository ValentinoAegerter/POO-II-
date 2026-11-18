import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa el voto individual de un miembro del tribunal.
 */
public class VotoJurado {
    private Docente jurado;
    private double calificacion;
    private String observacion;
    private boolean emitido;

    /**
     * Constructor invocado por el TribunalEvaluador al conformar la mesa.
     * @param jurado Docente asignado al tribunal.
     */
    public VotoJurado(Docente jurado) {
        this.jurado = jurado;
        this.emitido = false;
    }

    public void emitir(double calificacion, String observacion) {
        this.calificacion = calificacion;
        this.observacion = observacion;
        this.emitido = true;
    }

    public Docente getJurado() {
        return jurado;
    }
}

/**
 * Aplicando el patrón CREADOR: TribunalEvaluador contiene y agrega los VotoJurado,
 * por lo tanto es el responsable de instanciarlos internamente.
 */
public class TribunalEvaluador {
    private LocalDate fechaConstitucion;
    private List<VotoJurado> votos;

    public TribunalEvaluador(LocalDate fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
        this.votos = new ArrayList<>();
    }

    /**
     * Designa un docente como miembro del tribunal e instancia su correspondiente VotoJurado.
     * La UI solo pasa el Docente, sin hacer 'new VotoJurado()'.
     * @param docente El docente miembro del jurado.
     */
    public void asignarJurado(Docente docente) {
        if (votos.size() >= 3) {
            throw new IllegalStateException("El tribunal ya cuenta con los 3 jurados titulares.");
        }
        // Instanciación encapsulada según patrón Creador
        VotoJurado nuevoVoto = new VotoJurado(docente);
        this.votos.add(nuevoVoto);
    }

    public List<VotoJurado> getVotos() {
        return List.copyOf(votos);
    }
}