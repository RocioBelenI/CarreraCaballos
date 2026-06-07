package models;

public class CaballoResistente extends Caballo {

    public CaballoResistente(String nombre) {
        super(nombre);
        this.velocidadBase = 9.0;
        this.resistencia = 1500.0;
        this.penalidadCansancio = 0.60; // Mantiene buen ritmo a pesar del cansancio
    
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