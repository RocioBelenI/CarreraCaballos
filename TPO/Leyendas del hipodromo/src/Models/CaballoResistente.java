package Models;

public class CaballoResistente extends Caballo {
    
    @Override
    public void avanzar() {
    	
        double avance = getVelocidadBase() * (getResistencia() / 10); 
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    @Override
    public void reducirEnergia() {
    	
        this.energiaActual -= (10 / getResistencia()); 
    }
}