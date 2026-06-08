package dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "jugadores")
public class JugadorDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column
    private String mail;

    @Column(nullable = false)
    private int puntaje;

    // Guardamos simplemente el ID del caballo seleccionado
    @Column(name = "caballo_id", nullable = false)
    private Long caballoId;

    // Constructor vacío requerido por JPA
    protected JugadorDAO() {}

    public JugadorDAO(String nombre, String mail, Long caballoId) {
        this.nombre = nombre;
        this.mail = mail;
        this.caballoId = caballoId;
        this.puntaje = 0;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public Long getCaballoId() { return caballoId; }
    public void setCaballoId(Long caballoId) { this.caballoId = caballoId; }
}