import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidad de dominio con ALTA COHESIÓN y BAJO ACOPLAMIENTO.
 * Solo contiene los datos y reglas propias del documento de egreso,
 * sin conocer absolutamente nada sobre emails, PDFs o SIU-Guaraní.
 */
public class ActaEgreso {
    private String numeroActa;
    private LocalDate fechaGraduacion;
    private Estudiante estudiante;

    public ActaEgreso(Estudiante estudiante, LocalDate fechaGraduacion) {
        this.numeroActa = "ACTA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.estudiante = estudiante;
        this.fechaGraduacion = fechaGraduacion;
    }

    public String getNumeroActa() { return numeroActa; }
    public LocalDate getFechaGraduacion() { return fechaGraduacion; }
    public Estudiante getEstudiante() { return estudiante; }
}

/**
 * Interfaces de infraestructura para mantener bajo acoplamiento.
 */
public interface INotificadorEgreso {
    void notificarGraduacion(ActaEgreso acta);
}

public interface ISincronizadorAcademico {
    void registrarEgresoExterno(ActaEgreso acta);
}

public interface IGeneradorDiploma {
    void emitirDiploma(ActaEgreso acta);
}

/**
 * Servicio de Aplicación / Orquestador (Fabricación Pura).
 * Coordina la emisión del ActaEgreso y delega los efectos secundarios
 * a los servicios externos desacoplados.
 */
public class ServicioGestionEgreso {
    private final INotificadorEgreso notificador;
    private final ISincronizadorAcademico sincronizador;
    private final IGeneradorDiploma generadorDiploma;

    public ServicioGestionEgreso(INotificadorEgreso notificador,
                                 ISincronizadorAcademico sincronizador,
                                 IGeneradorDiploma generadorDiploma) {
        this.notificador = notificador;
        this.sincronizador = sincronizador;
        this.generadorDiploma = generadorDiploma;
    }

    /**
     * Ejecuta el flujo completo de egreso manteniendo cada responsabilidad separada.
     * @param estudiante El alumno que defendió y aprobó su tesis.
     * @param fecha Fecha oficial de graduación.
     * @return El ActaEgreso generada.
     */
    public ActaEgreso procesarEgreso(Estudiante estudiante, LocalDate fecha) {
        // 1. Crea la entidad limpia de dominio
        ActaEgreso acta = new ActaEgreso(estudiante, fecha);

        // 2. Delega las operaciones de infraestructura a cada especialista
        sincronizador.registrarEgresoExterno(acta);
        generadorDiploma.emitirDiploma(acta);
        notificador.notificarGraduacion(acta);

        return acta;
    }
}