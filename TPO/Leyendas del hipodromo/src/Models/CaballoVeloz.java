package models;

/**
 * Modelo de negocio puro para el caballo veloz.
 * Avanza rápido gracias a su explosividad, pero se cansa muy rápido.
 */
public class CaballoVeloz extends Caballo {

    private final double aumentoVelocidad = 1.5;

    public CaballoVeloz(String nombre) {
        super(nombre);
        this.velocidadBase = 16.0;
        this.resistencia = 400.0;
        this.penalidadCansancio = 0.25; // Pierde mucho ritmo al cansarse
    }

    @Override
    public void avanzar() {
        // Fórmula de avance explosivo
        double avance = getVelocidadBase() * (getEnergiaActual() / 100) * this.aumentoVelocidad;
        this.distanciaRecorrida += avance; 
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
        // Consume energía más rápido por su velocidad
        setEnergiaActual(getEnergiaActual() - (getVelocidadBase() * 0.15));

        if (this.energiaActual > 0) {
            this.energiaActual -= 10; 
        } else {
            this.energiaActual = 0;
        }
    }
}