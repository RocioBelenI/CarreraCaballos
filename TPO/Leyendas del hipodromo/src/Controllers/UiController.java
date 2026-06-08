package controllers;

import dao.JugadorRepository;
import dao.JugadorDAO;
import dto.CaballoDTO;
import dto.JugadorDTO;
import models.Jugador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Actúa como puente entre la Interfaz Gráfica (UI) y la lógica del juego/base de datos.
 */
public class UiController {

    private static final ControllerCaballo controllerCaballo = new ControllerCaballo();
    private static final ControllerJugador controllerJugador = new ControllerJugador();
    private static final JugadorRepository jugadorRepo = JugadorRepository.getInstance();

    // --- MÉTODOS PARA LA INTERFAZ GRÁFICA (Solo DTOs) ---

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
        // Buscamos el jugador en la base de datos según su posición en la lista
        List<JugadorDAO> jugadoresDB = jugadorRepo.listarJugadores();
        
        if (index >= 0 && index < jugadoresDB.size()) {
            JugadorDAO jugadorDAO = jugadoresDB.get(index);
            jugadorDAO.setNombre(nombre);
            
            try {
                jugadorDAO.setCaballoId(Long.parseLong(caballoId));
                jugadorRepo.guardarJugador(jugadorDAO); // Guardamos el DAO en la BD
            } catch (NumberFormatException e) {
                System.err.println("Error: El ID del caballo no es un número válido.");
            }
        }
    }

    // --- MÉTODOS PARA EL MOTOR DE SIMULACIÓN (Modelos Puros) ---

    public static List<Jugador> getJugadoresModelo() {
        List<Jugador> modelos = new ArrayList<>();
        List<JugadorDAO> daos = jugadorRepo.listarJugadores();
        
        // Convertimos los DAOs de la base de datos a modelos puros interactivos
        for (JugadorDAO dao : daos) {
            modelos.add(controllerJugador.crearJugadorDesdeDAO(dao));
        }
        return modelos;
    }

    public static void asignarPuntaje(Map<String, Integer> progresoPorJugador) {
        List<JugadorDAO> jugadores = jugadorRepo.listarJugadores();

        // Le damos 10 puntos al jugador o jugadores que hayan llegado a la meta (100%)
        for (JugadorDAO dao : jugadores) {
            if (progresoPorJugador.containsKey(dao.getNombre())) {
                int porcentaje = progresoPorJugador.get(dao.getNombre());
                
                if (porcentaje >= 100) {
                    dao.setPuntaje(dao.getPuntaje() + 10); 
                    jugadorRepo.guardarJugador(dao); // Actualizamos la base de datos
                }
            }
        }
    }
}