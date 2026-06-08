package dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "caballos")
public class CaballoDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String emoji;

    @Column(nullable = false)
    private double velocidadBase;

    @Column(nullable = false)
    private double resistencia;

    // Constructor vacío requerido por JPA
    protected CaballoDAO() {}

    // Constructor usado en los seeders de CaballoRepository
    public CaballoDAO(String tipo, String nombre, String emoji, double velocidadBase, double resistencia) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.emoji = emoji;
        this.velocidadBase = velocidadBase;
        this.resistencia = resistencia;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public double getVelocidadBase() { return velocidadBase; }
    public void setVelocidadBase(double velocidadBase) { this.velocidadBase = velocidadBase; }

    public double getResistencia() { return resistencia; }
    public void setResistencia(double resistencia) { this.resistencia = resistencia; }
}