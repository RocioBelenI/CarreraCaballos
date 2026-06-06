package Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "jugador")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column
    private String mail;

    @Column
    private int puntaje;

    // Relación hacia la entidad real del dominio: Caballo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caballo_id", nullable = false)
    private Caballo caballo;

    // Estado de simulación: posición durante la carrera, no se persiste
    @Transient
    private int posicionCarrera;

    // Constructor vacío requerido por JPA
    protected Jugador() {
    }

    public Jugador(String nombre, String mail, Caballo caballo) {
        this.nombre = nombre;
        this.mail = mail;
        this.caballo = caballo;
        this.puntaje = 0;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public Caballo getCaballo() { return caballo; }
    public void setCaballo(Caballo caballo) { this.caballo = caballo; }

    // Compatibilidad con código que usaba getCaballoId()
    public Long getCaballoId() {
        return caballo != null ? caballo.getId() : null;
    }

    public int getPosicionCarrera() { return posicionCarrera; }
    public void setPosicionCarrera(int posicionCarrera) { this.posicionCarrera = posicionCarrera; }
}
