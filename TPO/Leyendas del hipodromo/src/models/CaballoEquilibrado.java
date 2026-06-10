package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("equilibrado")
public class CaballoEquilibrado extends Caballo {

    protected CaballoEquilibrado() {
        super();
        this.penalidadCansancio = 0.40;
    }

    public CaballoEquilibrado(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.40;
    }

    @Override
    public void avanzar() {
        double factorEnergia = 0.60 + (getEnergiaActual() / getResistencia()) * 0.40; // 0.60 → 1.00
        double avance = getVelocidadBase() * factorEnergia * getMotivacion();
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        this.energiaActual -= getResistencia() / 15.0;
        if (this.energiaActual < 1) {
            this.energiaActual = 1;
        }
    }
}