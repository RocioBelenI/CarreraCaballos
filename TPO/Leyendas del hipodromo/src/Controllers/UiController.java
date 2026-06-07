package Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Models.Caballo;
import Models.Jugador;
import dao.CaballoRepository;
import dao.JugadorRepository;
import dto.CaballoDTO;
import dto.JugadorDTO;

public class UiController {

    private static final List<Caballo> caballos = new ArrayList<>();
    private static final List<Jugador> jugadores = new ArrayList<>();

    static {
        CaballoRepository.getInstance().cargarDatosCaballo();
        caballos.addAll(CaballoRepository.getInstance().listarCaballos());
    }

    public static List<CaballoDTO> getCaballosDisponibles() {
        List<CaballoDTO> dtos = new ArrayList<>();
        for (Caballo c : caballos) {
            dtos.add(new CaballoDTO(
                String.valueOf(c.getId()), 
                c.getNombre(), 
                c.getEmoji(), 
                (int) c.getVelocidadBase(), 
                (int) c.getResistencia()
            ));
        }
        return dtos;
    }

    public static List<JugadorDTO> getJugadores() {
        List<JugadorDTO> dtos = new ArrayList<>();
        for (Jugador j : jugadores) {
            Caballo c = j.getCaballo();
            CaballoDTO caballoDTO = new CaballoDTO(
                String.valueOf(c.getId()), 
                c.getNombre(), 
                c.getEmoji(), 
                (int) c.getVelocidadBase(), 
                (int) c.getResistencia()
            );
            JugadorDTO jdto = new JugadorDTO(
                String.valueOf(j.getId()),
                j.getNombre(),
                caballoDTO
            );
            jdto.setPuntaje(j.getPuntaje());
            dtos.add(jdto);
        }
        return dtos;
    }

    /**
     * Expone la lista interna de Jugador (con sus Caballos reales del modelo)
     * para ser usada por ControllerCarrera en la simulación de la carrera.
     */
    public static List<Jugador> getJugadoresModelo() {
        return Collections.unmodifiableList(jugadores);
    }


    // TODO: este metodo deberia estar en el controller de jugador, no en el controlador de la UI.
    public static boolean agregarJugador(String nombre, CaballoDTO caballoDTO) {
        if (nombre == null || nombre.isBlank() || caballoDTO == null) {
            return false;
        }

        Long caballoId = Long.parseLong(caballoDTO.getId());
        Caballo caballo = CaballoRepository.getInstance().buscarPorId(caballoId);

        // Ahora Jugador se construye con el objeto Caballo real (no con un ID crudo)
        Jugador jugador = new Jugador(nombre.trim(), null, caballo);
        JugadorRepository.getInstance().guardarJugador(jugador);
        recargarJugadores();

        return true;
    }

    // TODO: este metodo deberia estar en el controller de jugador, no en el controlador de la UI.
    public static void actualizarJugador(int index, String nombre, CaballoDTO caballoDTO) {
        if (index < 0 || index >= jugadores.size()) {
            return;
        }

        Long caballoId = Long.parseLong(caballoDTO.getId());
        Caballo caballo = CaballoRepository.getInstance().buscarPorId(caballoId);

        Jugador jugador = jugadores.get(index);
        jugador.setNombre(nombre.trim());
        jugador.setCaballo(caballo);
        JugadorRepository.getInstance().guardarJugador(jugador);
        recargarJugadores();
    }

    // TODO: este metodo deberia estar en el controller de jugador, no en el controlador de la UI.
    private static void recargarJugadores() {
        jugadores.clear();
        jugadores.addAll(JugadorRepository.getInstance().listarJugadores());
    }

    // TODO: este metodo tiene que estar en el controller de la carrera, no en el controlador de la UI.
    public static void asignarPuntaje(Map<String, Integer> finalProgress) {
        Map<String, Integer> resultScore = new LinkedHashMap<>();
        finalProgress.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEachOrdered(entry -> resultScore.put(entry.getKey(), entry.getValue()));

        int posicion = 1;

        for (String name : resultScore.keySet()) {
            Jugador player = jugadores.stream()
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

                posicion++;
            }
        }
    }
}
