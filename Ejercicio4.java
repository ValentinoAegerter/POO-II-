/**
 * Abstracción que define el contrato polimórfico para los roles en una tesis.
 */
public abstract class RolTesis {
    /**
     * Cada subclase concreta sabe exactamente cuántos puntos otorga su rol.
     * @return Cantidad de puntos académicos.
     */
    public abstract int calcularPuntaje();
}

/**
 * Implementación concreta para el rol de Tutor (50 puntos).
 */
public class RolTutor extends RolTesis {
    @Override
    public int calcularPuntaje() {
        return 50;
    }
}

/**
 * Implementación concreta para el rol de Jurado Titular (30 puntos).
 */
public class RolJuradoTitular extends RolTesis {
    @Override
    public int calcularPuntaje() {
        return 30;
    }
}

/**
 * Implementación concreta para el rol de Jurado Suplente (10 puntos).
 */
public class RolJuradoSuplente extends RolTesis {
    @Override
    public int calcularPuntaje() {
        return 10;
    }
}

/**
 * Representa al docente que acumula puntaje por sus participaciones.
 */
public class Docente {
    private String legajo;
    private String nombre;
    private int puntajeAcumulado;

    public Docente(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.puntajeAcumulado = 0;
    }

    public void sumarPuntos(int puntos) {
        this.puntajeAcumulado += puntos;
    }

    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }
}

/**
 * Vincula a un Docente con su RolTesis y asigna puntos polimórficamente,
 * sin ningún tipo de switch o if/else.
 */
public class ParticipacionTesis {
    private Docente docente;
    private RolTesis rol;

    public ParticipacionTesis(Docente docente, RolTesis rol) {
        this.docente = docente;
        this.rol = rol;
    }

    /**
     * Acredita los puntos al docente delegando el cálculo al objeto polimórfico RolTesis.
     */
    public void acreditarPuntaje() {
        int puntos = rol.calcularPuntaje();
        docente.sumarPuntos(puntos);
    }
}