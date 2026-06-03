package data.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "caballo") // Ahora la tabla en MySQL se llamará "caballo"
public class CaballoDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se mantiene el código por ahora, pero como ya buscas por nombre en el DAO,
    // a futuro podrías llegar a eliminar este atributo si no lo usás para otra cosa.
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String emoji;

    @Column
    private int velocidad;

    @Column
    private int resistencia;

    public CaballoDAO() {
    }

    public CaballoDAO(String codigo, String nombre, String emoji, int velocidad, int resistencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.emoji = emoji;
        this.velocidad = velocidad;
        this.resistencia = resistencia;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }
}

