package Models;


public class Jugador{
    private String nombre;
    private String mail;
    private int puntaje;
    private Caballo caballo;
    private int posicionCarrera;

    public Jugador(String nombre, String mail) {
        this.nombre  = nombre;
        this.mail    = mail;
        this.puntaje = 0;
        this.caballo = null;
    }

    public void sumarPuntaje(int puntos) {
        this.puntaje += puntos;
    }

    public void asignarCaballo(Caballo caballo) {
        this.caballo = caballo;
    }


    public String getNombre()  { return nombre;  }
    public String getMail()    { return mail;    }
    public int    getPuntaje() { return puntaje; }
    public Caballo getCaballo(){ return caballo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMail(String mail)     { this.mail   = mail;   }
}



