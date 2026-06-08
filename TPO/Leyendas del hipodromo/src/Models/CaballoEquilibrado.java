package models;

/**
 * Modelo de negocio puro para el caballo equilibrado.
 * Balance perfecto entre velocidad y resistencia.
 */
public class CaballoEquilibrado extends Caballo {

    public CaballoEquilibrado(String nombre) {
        super(nombre);
        this.velocidadBase = 12.0;
        this.resistencia = 800.0;
        this.penalidadCansancio = 0.40; // Mantiene un buen balance al cansarse
    }

    @Override
    public void avanzar() {
        // Fórmula de avance según energía actual
        double avance = getVelocidadBase() * (getEnergiaActual() / 100);
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.10));

        if (this.energiaActual > 0) {
            this.energiaActual -= 5; 
        } else {
            this.energiaActual = 0;
        }
    }
}