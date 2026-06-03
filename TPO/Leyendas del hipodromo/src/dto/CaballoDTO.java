package dto;

public class CaballoDTO {
    private final String id;
    private final String nombre;
    private final String emoji;
    private final int velocidad;
    private final int resistencia;

    public CaballoDTO(String id, String nombre, String emoji, int velocidad, int resistencia) {
        this.id = id;
        this.nombre = nombre;
        this.emoji = emoji;
        this.velocidad = velocidad;
        this.resistencia = resistencia;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getResistencia() {
        return resistencia;
    }
}