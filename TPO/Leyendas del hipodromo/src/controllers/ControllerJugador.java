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

            Jugador nuevo = new Jugador(nombre.trim(), caballo);
            jugadorRepo.guardarJugador(nuevo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}