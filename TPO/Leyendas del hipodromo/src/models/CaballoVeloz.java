package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("veloz")
public class CaballoVeloz extends Caballo {

    private static final double MULTIPLICADOR_VELOCIDAD = 1.5;

    protected CaballoVeloz() {
        super();
        this.penalidadCansancio = 0.25;
    }

    public CaballoVeloz(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.25;
    }

    @Override
    public void avanzar() {
        double factorEnergia = getEnergiaActual() / getResistencia();
        double avance = getVelocidadBase() * factorEnergia * MULTIPLICADOR_VELOCIDAD * getMotivacion();
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        this.energiaActual -= getResistencia() / 8.0;
        if (this.energiaActual < 1) {
            this.energiaActual = 1;
        }
    }
}