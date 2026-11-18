import java.util.ArrayList;
import java.util.List;

/**
 * Representa el plan de estudios de la carrera.
 * Es el experto en saber cuentas materias exige la carrera para egresar.
 */
public class PlanEstudio {
    private String codigo;
    private int totalMateriasObligatorias;

    public PlanEstudio(String codigo, int totalMateriasObligatorias) {
        this.codigo = codigo;
        this.totalMateriasObligatorias = totalMateriasObligatorias;
    }

    public int getTotalMateriasObligatorias() {
        return totalMateriasObligatorias;
    }
}

/**
 * Representa el registro de cursada/examen de una materia por parte de un alumno.
 * Experta en determinar si esa materia especifica esta aprobada.
 */
public class Cursada {
    private String nombreMateria;
    private double notaFinal;

    public Cursada(String nombreMateria, double notaFinal) {
        this.nombreMateria = nombreMateria;
        this.notaFinal = notaFinal;
    }

    /**
     * Evalua si la nota alcanza el minimo aprobatorio (ej. >= 6).
     * @return true si esta aprobada.
     */
    public boolean estaAprobada() {
        return this.notaFinal >= 6.0;
    }
}

/**
 * Experto en Informacion sobre su propio historial academico.
 * Encapsula su lista de cursadas y responde directamente si esta habilitado para tesis.
 */
public class Estudiante {
    private String legajo;
    private String nombre;
    private List<Cursada> cursadas;
    private PlanEstudio planEstudio;

    public Estudiante(String legajo, String nombre, PlanEstudio planEstudio) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.planEstudio = planEstudio;
        this.cursadas = new ArrayList<>();
    }

    public void agregarCursada(Cursada cursada) {
        this.cursadas.add(cursada);
    }


    public boolean puedePresentarTesis() {
        int aprobadas = contarMateriasAprobadas();
        return aprobadas >= planEstudio.getTotalMateriasObligatorias();
    }

    private int contarMateriasAprobadas() {
        int contador = 0;
        for (Cursada c : cursadas) {
            if (c.estaAprobada()) {
                contador++;
            }
        }
        return contador;
    }
}