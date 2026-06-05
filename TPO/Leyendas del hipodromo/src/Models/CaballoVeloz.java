package models;

public class CaballoVeloz extends Caballo {

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