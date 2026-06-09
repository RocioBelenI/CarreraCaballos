package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad JPA para el caballo equilibrado.
 * Balance perfecto entre velocidad y resistencia.
 * También representa el tipo "fuerte" (fallback por defecto).
 */
@Entity
@DiscriminatorValue("equilibrado")
public class CaballoEquilibrado extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoEquilibrado() {
        super();
        this.penalidadCansancio = 0.40; // Mantiene un buen balance al cansarse
    }

    // Constructor para el seeder del repositorio
    public CaballoEquilibrado(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.40;
    }

    @Override
    public void avanzar() {
        // Fórmula de avance según energía actual (lógica original de los compañeros)
        double avance = getVelocidadBase() * (getEnergiaActual() / 100);
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        // Fórmula original de los compañeros
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.10));

        this.energiaActual -= 5;
        
        // Garantizar un mínimo de energía para que el caballo nunca se detenga por completo
        if (this.energiaActual < 15) {
            this.energiaActual = 15;
        }
    }
}