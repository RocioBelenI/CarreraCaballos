package models;

public abstract class Caballo{
    private String nombre;
    private double velocidadBase;
    private double resistencia;
    private double energiaActual;
    private double distanciaRecorrida;

    public abstract void avanzar();
    public abstract void reducirEnergia();
    public double getDistanciaRecorrida(){
        return distanciaRecorrida;
    }
    public String getNombre(){
        return nombre;
    }
}


