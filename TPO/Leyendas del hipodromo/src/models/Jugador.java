package models;

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

    @Column(nullable = false)
    private int puntaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caballo_id", nullable = false)
    private Caballo caballo;

    @Transient
    private int posicionCarrera;

    protected Jugador() {
    }

    public Jugador(String nombre, Caballo caballo) {
        this.nombre = nombre;
        this.caballo = caballo;
        this.puntaje = 0;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public Caballo getCaballo() { return caballo; }
    public void setCaballo(Caballo caballo) { this.caballo = caballo; }

    public Long getCaballoId() {
        return caballo != null ? caballo.getId() : null;
    }

    public int getPosicionCarrera() { return posicionCarrera; }
    public void setPosicionCarrera(int posicionCarrera) { this.posicionCarrera = posicionCarrera; }
}