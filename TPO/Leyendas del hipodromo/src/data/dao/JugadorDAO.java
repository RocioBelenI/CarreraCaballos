package data.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugador") // La tabla en la base de datos ahora se llamará "jugador"
public class JugadorDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caballo_id", nullable = false) // Modificamos el nombre de la columna foránea
    private Long caballo;

    @Column
    private int puntaje;

    public JugadorDAO() {
    }

    public JugadorDAO(String nombre, Long caballo, int puntaje) {
        this.nombre = nombre;
        this.caballo = caballo;
        this.puntaje = puntaje;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCaballoId() {
        return caballo;
    }

    public void setCaballo(Long caballo) {
        this.caballo = caballo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}