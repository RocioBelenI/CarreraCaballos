package models;

public class CaballoVeloz extends Caballo {

    public CaballoVeloz(String nombre) {
        super(nombre);
        this.velocidadBase = 16.0;
        this.resistencia = 400.0;
        this.penalidadCansancio = 0.25; // Pierde mucho ritmo al cansarse

	private double aumentoVelocidad = 1.5; 

    @Override
    public void avanzar() {

    	double avance = getVelocidadBase() * (getEnergiaActual() / 100) * this.aumentoVelocidad;
        this.distanciaRecorrida += avance; 
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {

    	if (this.energiaActual > 0) {
            this.energiaActual -= 10; 
        } else {
            this.energiaActual = 0;
        }
    }
}