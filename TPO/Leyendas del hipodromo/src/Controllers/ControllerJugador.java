package Controllers;

import Models.Jugador;

public class ControllerJugador {
    private Jugador jugador;

    public Jugador CrearJugador(String nombre, String mail) {
        return new Jugador(nombre, mail);
    }
}
