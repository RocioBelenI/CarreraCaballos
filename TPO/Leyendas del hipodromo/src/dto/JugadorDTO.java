package dto;

public class JugadorDTO {
    private String nombre;
    private CaballoDTO caballo;
    private int puntaje;
    private int victorias;

    public JugadorDTO(String nombre, CaballoDTO caballo) {
        this.nombre = nombre;
        this.caballo = caballo;
        this.puntaje = 0;
        this.victorias = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CaballoDTO getCaballo() {
        return caballo;
    }

    public void setCaballo(CaballoDTO caballo) {
        this.caballo = caballo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }
}