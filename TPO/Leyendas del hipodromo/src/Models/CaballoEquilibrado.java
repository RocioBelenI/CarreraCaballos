package Models;

public class CaballoEquilibrado extends Caballo {

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