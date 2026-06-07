package models;

public class CaballoVeloz extends Caballo {

    public CaballoVeloz(String nombre) {
        super(nombre);
        this.velocidadBase = 16.0;
        this.resistencia = 400.0;
        this.penalidadCansancio = 0.25; // Pierde mucho ritmo al cansarse
    }
}
