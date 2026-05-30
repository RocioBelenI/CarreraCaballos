package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import data.dao.CaballoDAO;
import data.dao.JugadorDAO;

import data.CaballoRepository;
import data.JugadorRepository;

public class UiController {
    private static final List<CaballoDAO> caballos = new ArrayList<>();
    private static final List<JugadorDAO> jugadores = new ArrayList<>();

    static {
        new CaballoRepository().cargarDatosCaballo();
        caballos.addAll(new CaballoRepository().listarCaballos());
    }

    public static List<CaballoDAO> getCaballosDisponibles() {
        return Collections.unmodifiableList(caballos);
    }

    public static List<JugadorDAO> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    //TODO: este metodo deberia estar en el controller de jugador, no en el controlador de la UI.
    public static boolean agregarJugador(String nombre, CaballoDAO caballo) {
        if (nombre == null || nombre.isBlank() || caballo == null) {
            return false;
        }
        JugadorDAO jugadorDAO = new JugadorDAO();
        jugadorDAO.setNombre(nombre.trim());
        jugadorDAO.setCaballo(caballo.getId());

        new JugadorRepository().guardarJugador(jugadorDAO);
        recargarJugadores(); 

        return true;
    }

    //TODO: este metodo deberia estar en el controller de jugador, no en el controlador de la UI.
    public static void actualizarJugador(int index, String nombre, CaballoDAO caballo) {
        if (index < 0 || index >= jugadores.size()) {
            return;
        }
        
        JugadorDAO jugadorDAO = jugadores.get(index);
        jugadorDAO.setNombre(nombre.trim());
        jugadorDAO.setCaballo(caballo.getId());
        new JugadorRepository().guardarJugador(jugadorDAO);
        recargarJugadores();
    }

    //TODO: este metodo deberia estar en el controller de jugador, no en el controlador de la UI.
    private static void recargarJugadores() {
        jugadores.clear();
        jugadores.addAll(new JugadorRepository().listarJugadores());
    }

    // TODO: este metodo tiene que estar en el controller de la carrera, no en el controlador de la UI.
    public static void asignarPuntaje(Map<String, Integer> finalProgress) {
        Map<String, Integer> resultScore = new LinkedHashMap<>();
        finalProgress.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEachOrdered(entry -> resultScore.put(entry.getKey(), entry.getValue()));

        int posicion = 1;

        for (String name : resultScore.keySet()) {
            JugadorDAO player = jugadores.stream()
                    .filter(p -> p.getNombre().equals(name))
                    .findFirst()
                    .orElse(null);
                    
            if (player != null) {
                int currentPoints = player.getPuntaje();
                if (posicion == 1) {
                    player.setPuntaje(currentPoints + 100);
                } else if (posicion == 2) {
                    player.setPuntaje(currentPoints + 50);
                } else {
                    player.setPuntaje(currentPoints + 10);
                }
                
                posicion++; // Sumamos 1 a la posición para el siguiente jugador en el bucle
            }
        }
    }
}
