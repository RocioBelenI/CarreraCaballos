package controllers;

import dao.CaballoRepository;
import dao.JugadorRepository;
import dto.CaballoDTO;
import dto.JugadorDTO;
import models.Jugador;
import models.Caballo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UiController {

    private static final ControllerCaballo controllerCaballo = new ControllerCaballo();
    private static final ControllerJugador controllerJugador = new ControllerJugador();
    private static final JugadorRepository jugadorRepo = JugadorRepository.getInstance();
    private static final CaballoRepository caballoRepo = CaballoRepository.getInstance();


    public static List<CaballoDTO> getCaballosDisponiblesParaUI() {
        return controllerCaballo.listarCaballosParaUI();
    }

    public static List<JugadorDTO> getJugadoresParaUI() {
        return controllerJugador.listarJugadoresParaUI();
    }

    public static boolean agregarJugador(String nombre, String caballoId) {
        return controllerJugador.agregarJugador(nombre, caballoId);
    }

    public static void actualizarJugador(int index, String nombre, String caballoId) {
        List<Jugador> jugadoresDB = jugadorRepo.listarJugadores();

        if (index >= 0 && index < jugadoresDB.size()) {
            Jugador jugador = jugadoresDB.get(index);
            jugador.setNombre(nombre);

            try {

                Caballo caballo = caballoRepo.buscarPorId(Long.parseLong(caballoId));
                if (caballo != null) {
                    jugador.setCaballo(caballo);
                }
                jugadorRepo.guardarJugador(jugador);
            } catch (NumberFormatException e) {
                System.err.println("Error: El ID del caballo no es un número válido.");
            }
        }
    }

    public static List<Jugador> getJugadoresModelo() {
        return jugadorRepo.listarJugadores();
    }

    public static void asignarPuntaje(Map<String, Integer> progresoPorJugador) {
        List<Jugador> jugadores = jugadorRepo.listarJugadores();

        // Ordenamos los nombres de los jugadores por su porcentaje de progreso de mayor a menor
        List<String> posiciones = new ArrayList<>(progresoPorJugador.keySet());
        posiciones.sort((j1, j2) -> Integer.compare(progresoPorJugador.get(j2), progresoPorJugador.get(j1)));

        for (int i = 0; i < posiciones.size(); i++) {
            String nombreJugador = posiciones.get(i);
            Jugador jugador = jugadores.stream()
                    .filter(j -> j.getNombre().equals(nombreJugador))
                    .findFirst()
                    .orElse(null);

            if (jugador != null) {
                if (i == 0) {
                    jugador.setPuntaje(jugador.getPuntaje() + 100);
                } else if (i == 1) {
                    jugador.setPuntaje(jugador.getPuntaje() + 50);
                } else {
                    jugador.setPuntaje(jugador.getPuntaje() + 10);
                }
                jugadorRepo.guardarJugador(jugador);
            }
        }
    }
}