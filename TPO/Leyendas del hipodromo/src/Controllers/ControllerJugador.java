package controllers;

import dao.JugadorRepository;
import dao.JugadorDAO;
import dao.CaballoRepository;
import dao.CaballoDAO;
import dto.JugadorDTO;
import models.Jugador;
import models.Caballo;

import java.util.ArrayList;
import java.util.List;

public class ControllerJugador {

    private final JugadorRepository jugadorRepo = JugadorRepository.getInstance();
    private final CaballoRepository caballoRepo = CaballoRepository.getInstance();

    /**
     * Convierte los DAOs en DTOs para que la interfaz gráfica los muestre
     * sin tocar directamente la base de datos.
     */
    public List<JugadorDTO> listarJugadoresParaUI() {
        List<JugadorDTO> lista = new ArrayList<>();
        List<JugadorDAO> jugadoresDB = jugadorRepo.listarJugadores();
        
        for (JugadorDAO j : jugadoresDB) {
            lista.add(new JugadorDTO(
                j.getId().toString(),
                j.getNombre(),
                j.getPuntaje(),
                j.getCaballoId().toString()
            ));
        }
        return lista;
    }

    /**
     * Recibe los datos planos de la UI y los guarda en la base de datos usando el DAO.
     */
    public boolean agregarJugador(String nombre, String caballoId) {
        if (nombre == null || nombre.trim().isEmpty() || caballoId == null) {
            return false;
        }
        try {
            Long cabId = Long.parseLong(caballoId);
            // El mail lo dejamos vacío por ahora según la estructura
            JugadorDAO nuevo = new JugadorDAO(nombre, "", cabId);
            jugadorRepo.guardarJugador(nuevo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Convierte los datos de la base de datos (DAO) en el Modelo puro
     * para que el motor de simulación pueda usarlo en la carrera.
     */
    public Jugador crearJugadorDesdeDAO(JugadorDAO dao) {
        // 1. Buscamos el caballo asociado en la base de datos
        CaballoDAO caballoDAO = caballoRepo.buscarPorId(dao.getCaballoId());
        
        // 2. Lo convertimos al modelo puro usando el controlador de caballos
        Caballo caballoModelo = ControllerCaballo.crearCaballoDesdeDAO(caballoDAO);
        
        // 3. Armamos el modelo puro del jugador
        Jugador jugador = new Jugador(dao.getNombre(), dao.getMail(), caballoModelo);
        jugador.setPuntaje(dao.getPuntaje());
        return jugador;
    }
}