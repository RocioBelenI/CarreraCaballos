package models;

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

/**
 * Entidad JPA y Modelo de dominio para la simulación de la carrera.
 * Usa SINGLE_TABLE: todas las subclases (Veloz, Resistente, Equilibrado)
 * se guardan en la misma tabla "caballo" con una columna discriminadora.
 */
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
    private String codigo; // Identificador de tipo: "veloz", "resistente", "equilibrado"

    @Column(nullable = false)
    private String nombre;

    @Column
    private String emoji;

    @Column(nullable = false)
    protected double velocidadBase;

    @Column(nullable = false)
    protected double resistencia;

    // --- Estado de simulación: ignorado por JPA ---

    @Transient
    protected double energiaActual;

    @Transient
    protected double distanciaRecorrida;

    @Transient
    protected double motivacion; // Factor aleatorio de "batacazo"

    @Transient
    protected double penalidadCansancio; // Qué tan rápido cae el rendimiento al cansarse

    // Constructor vacío requerido por JPA (protected para no romper encapsulamiento)
    protected Caballo() {
    }

    // Constructor para inicializar desde código de negocio / seeders
    public Caballo(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.emoji = emoji;
        this.velocidadBase = velocidadBase;
        this.resistencia = resistencia;
    }

    /**
     * Prepara el estado de simulación antes de arrancar la carrera.
     * Inicializa la energía y calcula la motivación aleatoria (lógica de los compañeros).
     */
    public void prepararParaCarrera() {
        this.distanciaRecorrida = 0;
        this.energiaActual = this.resistencia;

        // Cálculo de la Motivación (Probabilidad de "Batacazo")
        double suerte = Math.random();
        if (suerte < 0.05) {
            this.motivacion = 1.4 + (Math.random() * 0.2); // Día de Gloria (1.4 - 1.6)
        } else if (suerte > 0.95) {
            this.motivacion = 0.7 + (Math.random() * 0.1); // Mal Día (0.7 - 0.8)
        } else {
            this.motivacion = 0.95 + (Math.random() * 0.1); // Normal (0.95 - 1.05)
        }
    }

    // Métodos de comportamiento de la carrera que implementarán los hijos
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