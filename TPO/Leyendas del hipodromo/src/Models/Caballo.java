package Models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "caballo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_caballo", discriminatorType = DiscriminatorType.STRING)
public abstract class Caballo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Datos persistidos en la BD ---

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String emoji;

    @Column(nullable = false)
    private double velocidadBase;

    @Column(nullable = false)
    private double resistencia;

    // --- Estado de simulación: ignorado por JPA ---

    @Transient
    private double energiaActual;

    @Transient
    private double distanciaRecorrida;

    // Constructor vacío requerido por JPA (protected para no romper encapsulamiento)
    protected Caballo() {
    }

    // Constructor para inicializar desde código de negocio
    public Caballo(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.emoji = emoji;
        this.velocidadBase = velocidadBase;
        this.resistencia = resistencia;
        this.energiaActual = resistencia; // La energía inicial es la resistencia máxima
        this.distanciaRecorrida = 0;
    }

    // Métodos de comportamiento de la carrera (no persistidos)
    public abstract void avanzar();
    public abstract void reducirEnergia();

    // --- Getters y Setters ---

    public Long getId() { return id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public double getVelocidadBase() { return velocidadBase; }
    public void setVelocidadBase(double velocidadBase) { this.velocidadBase = velocidadBase; }

    public double getResistencia() { return resistencia; }
    public void setResistencia(double resistencia) { this.resistencia = resistencia; }

    // Getters/Setters del estado de simulación (@Transient)
    public double getEnergiaActual() { return energiaActual; }
    public void setEnergiaActual(double energiaActual) { this.energiaActual = energiaActual; }

    public double getDistanciaRecorrida() { return distanciaRecorrida; }
    public void setDistanciaRecorrida(double distanciaRecorrida) { this.distanciaRecorrida = distanciaRecorrida; }
}
