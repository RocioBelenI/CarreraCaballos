package controllers;

import dao.JugadorRepository;
import dao.CaballoRepository;
import dto.JugadorDTO;
import models.Jugador;
import models.Caballo;

import java.util.ArrayList;
import java.util.List;

public class ControllerJugador {

    private final JugadorRepository jugadorRepo = JugadorRepository.getInstance();
    private final CaballoRepository caballoRepo = CaballoRepository.getInstance();

    /**
     * Convierte las entidades Jugador en DTOs planos para que la UI los muestre
     * sin tocar directamente la base de datos.
     */
    public List<JugadorDTO> listarJugadoresParaUI() {
        List<JugadorDTO> lista = new ArrayList<>();
        List<Jugador> jugadoresDB = jugadorRepo.listarJugadores();

        for (Jugador j : jugadoresDB) {
            lista.add(new JugadorDTO(
                j.getId().toString(),
                j.getNombre(),
                j.getPuntaje(),
                j.getCaballoId() != null ? j.getCaballoId().toString() : ""
            ));
        }
        return lista;
    }

    /**
     * Recibe los datos planos de la UI, busca el Caballo real en la BD
     * y guarda el Jugador con la relación @ManyToOne correcta.
     */
    public boolean agregarJugador(String nombre, String caballoId) {
        if (nombre == null || nombre.trim().isEmpty() || caballoId == null) {
            return false;
        }
        try {
            Long cabId = Long.parseLong(caballoId);
            Caballo caballo = caballoRepo.buscarPorId(cabId);
            if (caballo == null) {
                return false;
            }
            // Construye la entidad real con la relación JPA correcta
            Jugador nuevo = new Jugador(nombre.trim(), caballo);
            jugadorRepo.guardarJugador(nuevo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}