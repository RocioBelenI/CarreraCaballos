package models;

public class CaballoEquilibrado extends Caballo {

    public CaballoEquilibrado(String nombre) {
        super(nombre);
        this.velocidadBase = 12.0;
        this.resistencia = 800.0;
        this.penalidadCansancio = 0.40; // Balance perfecto
    }
}
