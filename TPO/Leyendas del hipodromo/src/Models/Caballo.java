package models;

/**
 * Clase base del modelo de dominio para la simulación de la carrera.
 * Libre de anotaciones de base de datos (JPA).
 */
public abstract class Caballo {
    protected String nombre;
    protected double velocidadBase;
    protected double resistencia;
    protected double energiaActual;
    protected double distanciaRecorrida;
    
    // Atributos de la lógica del motor físico
    protected double motivacion;
    protected double penalidadCansancio;

    public Caballo(String nombre) {
        this.nombre = nombre;
    }

    public void prepararParaCarrera() {
        this.distanciaRecorrida = 0;
        this.energiaActual = this.resistencia;
        
        // Cálculo de la Motivación (Probabilidad de "Batacazo")
        double suerte = Math.random();
        if (suerte < 0.05) {
            this.motivacion = 1.4 + (Math.random() * 0.2); // Día de Gloria (1.4 - 1.6)
        } else if (suerte > 0.95) {
            this.motivacion = 0.7 + (Math.random() * 0.1); // Mal Día (0.7 - 0.8)
        } else {
            this.motivacion = 0.95 + (Math.random() * 0.1); // Normal (0.95 - 1.05)
        }
    }

    // Métodos de comportamiento de la carrera que implementarán los hijos
    public abstract void avanzar();
    public abstract void reducirEnergia();

    // --- Getters y Setters ---

    public String getNombre() { 
        return nombre; 
    }
    
    public double getVelocidadBase() { 
        return velocidadBase; 
    }
    
    public double getResistencia() { 
        return resistencia; 
    }
    
    public double getEnergiaActual() { 
        return energiaActual; 
    }
    
    public void setEnergiaActual(double energiaActual) { 
        this.energiaActual = energiaActual; 
    }
    
    public double getDistanciaRecorrida() { 
        return distanciaRecorrida; 
    }
    
    public void setDistanciaRecorrida(double distanciaRecorrida) { 
        this.distanciaRecorrida = distanciaRecorrida; 
    }
}