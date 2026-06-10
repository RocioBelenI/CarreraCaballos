package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("resistente")
public class CaballoResistente extends Caballo {

    protected CaballoResistente() {
        super();
        this.penalidadCansancio = 0.60;
    }

    public CaballoResistente(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.60;
    }

    @Override
    public void avanzar() {
        double factorEnergia = 0.75 + (getEnergiaActual() / getResistencia()) * 0.25;
        double avance = getVelocidadBase() * factorEnergia * getMotivacion();
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        this.energiaActual -= getResistencia() / 25.0;
        if (this.energiaActual < 1) {
            this.energiaActual = 1;
        }
    }
}