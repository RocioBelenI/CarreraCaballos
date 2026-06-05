package Models;

public abstract class Caballo{
    private String nombre;
    protected double velocidadBase;
    protected double resistencia;
    protected double energiaActual;
    protected double distanciaRecorrida;

    public abstract void avanzar();
    public abstract void reducirEnergia();
    
    public double getDistanciaRecorrida(){
        return distanciaRecorrida;
    }
    public String getNombre(){
        return nombre;
    }
    
    public double getVelocidadBase() {
    	
    	return velocidadBase;
    }
    
    public double getEnergiaActual() {
    	return energiaActual;
 
    }	
    
    public double getResistencia() {
    	return resistencia;
    	
    }
}


