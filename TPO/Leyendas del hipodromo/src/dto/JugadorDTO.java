package dto;

public class JugadorDTO {
    private final String id;
    private final String nombre;
    private final int puntaje;
    private final String caballoId;

    // Este es el constructor que Java te está pidiendo
    public JugadorDTO(String id, String nombre, int puntaje, String caballoId) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.caballoId = caballoId;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPuntaje() { return puntaje; }
    public String getCaballoId() { return caballoId; }
}