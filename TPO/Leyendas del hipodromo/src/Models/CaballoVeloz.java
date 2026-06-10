package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad JPA para el caballo veloz.
 * Avanza rápido gracias a su explosividad, pero se cansa muy rápido.
 */
@Entity
@DiscriminatorValue("veloz")
public class CaballoVeloz extends Caballo {

    // Constante de juego: multiplicador de velocidad explosiva
    private final double aumentoVelocidad = 1.5;

    // Constructor vacío requerido por JPA
    protected CaballoVeloz() {
        super();
        this.penalidadCansancio = 0.25; // Pierde mucho ritmo al cansarse
    }

    // Constructor para el seeder del repositorio
    public CaballoVeloz(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.25;
    }

    @Override
    public void avanzar() {
        // Fórmula de avance explosivo (lógica original de los compañeros)
        double avance = getVelocidadBase() * (getEnergiaActual() / 100) * this.aumentoVelocidad;
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        // Consume energía más rápido por su velocidad (lógica original de los compañeros)
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.15));

        this.energiaActual -= 10;
        
        // Garantizar un mínimo de energía para que el caballo nunca se detenga por completo
        if (this.energiaActual < 15) {
            this.energiaActual = 15;
        }
    }
}