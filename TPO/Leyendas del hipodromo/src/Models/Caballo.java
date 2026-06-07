package models;

public abstract class Caballo {
    protected String nombre;
public abstract class Caballo{
    private String nombre;
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

    public void avanzar() {
        // Corre a velocidadBase hasta que se cansa (energia <= 0), luego aplica penalidad
        double factorCansancio = (energiaActual > 0) ? 1.0 : penalidadCansancio;
        
        // Fórmula de Avance por turno
        double avanceTurno = velocidadBase * factorCansancio * motivacion;
        distanciaRecorrida += avanceTurno;
    }

    public void reducirEnergia() {
        if (energiaActual > 0) {
            energiaActual -= velocidadBase; 
        }
    }

    public abstract void avanzar();
    public abstract void reducirEnergia();
    
    public double getDistanciaRecorrida(){
        return distanciaRecorrida;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getEnergiaActual() {
        return energiaActual;
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
