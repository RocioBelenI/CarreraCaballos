package Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("equilibrado")
public class CaballoEquilibrado extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoEquilibrado() {
        super();
    }

    public CaballoEquilibrado(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
    }

    @Override
    public void avanzar() {
        // Velocidad y consumo equilibrados
        setDistanciaRecorrida(getDistanciaRecorrida() + getVelocidadBase() * 1.0);
    }

    @Override
    public void reducirEnergia() {
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.10));
    }
}
