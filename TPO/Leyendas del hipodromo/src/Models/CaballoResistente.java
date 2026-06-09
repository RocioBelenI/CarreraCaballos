package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad JPA para el caballo resistente.
 * Mantiene un buen ritmo constante a pesar del cansancio.
 */
@Entity
@DiscriminatorValue("resistente")
public class CaballoResistente extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoResistente() {
        super();
        this.penalidadCansancio = 0.60; // Mantiene buen ritmo a pesar del cansancio
    }

    // Constructor para el seeder del repositorio
    public CaballoResistente(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.60;
    }

    @Override
    public void avanzar() {
        // Factor entre 0.8 y 1.0 según cuánta energía le queda
        // (no varía mucho → ritmo "resistente")
        double factorEnergia = 0.8 + (getEnergiaActual() / getResistencia()) * 0.2;
        double avance = getVelocidadBase() * factorEnergia;
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        // Consume energía más lentamente (lógica original de los compañeros)
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.05));
        this.energiaActual -= (10 / getResistencia());

        // Garantizar un mínimo de energía para que el caballo nunca se detenga por completo
        if (this.energiaActual < 15) {
            this.energiaActual = 15;
        }
    }
}