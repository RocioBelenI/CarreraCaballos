package dto;

public class CaballoDTO {
    private final String id;
    private final String nombre;
    private final String emoji;
    private final double velocidadBase; // Cambiado a double
    private final double resistencia;   // Cambiado a double
    private final double energiaActual; // AGREGADO
    private final double distanciaRecorrida; // AGREGADO 

    public CaballoDTO(String id, String nombre, String emoji, double velocidad, 
                      double resistencia, double energia, double distancia) {
        this.id = id;
        this.nombre = nombre;
        this.emoji = emoji;
        this.velocidadBase = velocidad;
        this.resistencia = resistencia;
        this.energiaActual = energia;
        this.distanciaRecorrida = distancia;
    }
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmoji() { return emoji; }
    public double getVelocidadBase() { return velocidadBase; }
    public double getResistencia() { return resistencia; }
    public double getEnergiaActual() { return energiaActual; }
    public double getDistanciaRecorrida() { return distanciaRecorrida; }
    
}