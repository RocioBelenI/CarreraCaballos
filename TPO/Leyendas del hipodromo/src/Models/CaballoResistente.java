package Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("resistente")
public class CaballoResistente extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoResistente() {
        super();
    }

    public CaballoResistente(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
    }

    @Override
    public void avanzar() {
        // Avanza a velocidad normal pero mantiene el ritmo constante
        setDistanciaRecorrida(getDistanciaRecorrida() + getVelocidadBase() * 0.9);
    }

    @Override
    public void reducirEnergia() {
        // Consume energía más lentamente
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.05));
    }
}
