package dto;

public class CaballoDTO {
    private final String id;
    private final String nombre;
    private final String emoji;
    private final double velocidadBase;
    private final double resistencia;
    private final double energiaActual;
    private final double distanciaRecorrida;

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