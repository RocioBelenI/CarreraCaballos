package models;

/**
 * Modelo de negocio puro para el jugador durante la simulación.
 * Libre de anotaciones de base de datos (JPA).
 */
public class Jugador {

    private String nombre;
    private String mail;
    private int puntaje;
    
    // Relación directa al modelo puro del caballo
    private Caballo caballo;

    // Estado de simulación
    private int posicionCarrera;

    public Jugador(String nombre, String mail, Caballo caballo) {
        this.nombre = nombre;
        this.mail = mail;
        this.caballo = caballo;
        this.puntaje = 0;
    }

    // --- Getters y Setters ---

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getMail() { 
        return mail; 
    }
    
    public void setMail(String mail) { 
        this.mail = mail; 
    }

    public int getPuntaje() { 
        return puntaje; 
    }
    
    public void setPuntaje(int puntaje) { 
        this.puntaje = puntaje; 
    }

    public Caballo getCaballo() { 
        return caballo; 
    }
    
    public void setCaballo(Caballo caballo) { 
        this.caballo = caballo; 
    }

    public int getPosicionCarrera() { 
        return posicionCarrera; 
    }
    
    public void setPosicionCarrera(int posicionCarrera) { 
        this.posicionCarrera = posicionCarrera; 
    }
}