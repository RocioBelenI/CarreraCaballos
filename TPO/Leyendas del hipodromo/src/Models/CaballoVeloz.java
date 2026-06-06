package Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("veloz")
public class CaballoVeloz extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoVeloz() {
        super();
    }

    public CaballoVeloz(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
    }

    @Override
    public void avanzar() {
        // Lógica de simulación: avanza rápido pero consume más energía
        setDistanciaRecorrida(getDistanciaRecorrida() + getVelocidadBase() * 1.3);
    }

    @Override
    public void reducirEnergia() {
        // Consume energía más rápido por su velocidad
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.15));
    }
}
