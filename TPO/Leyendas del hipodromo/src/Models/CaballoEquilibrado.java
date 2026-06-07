package models;

public class CaballoEquilibrado extends Caballo {

    public CaballoEquilibrado(String nombre) {
        super(nombre);
        this.velocidadBase = 12.0;
        this.resistencia = 800.0;
        this.penalidadCansancio = 0.40; // Balance perfecto

    @Override
    public void avanzar() {
 
        double avance = getVelocidadBase() * (getEnergiaActual() / 100);
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {

    	if (this.energiaActual > 0) {
            this.energiaActual -= 5; 
        } else {
            this.energiaActual = 0;
        }
    }
}