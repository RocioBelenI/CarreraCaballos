package Controllers;

import Models.Caballo;
import Models.Jugador;
import Models.MomentoCarrera;
import Models.ProgresoCarrera;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller que orquesta la simulación de una carrera.
 * Usa los métodos de comportamiento de cada Caballo (avanzar/reducirEnergia)
 * para calcular el progreso real de la carrera paso a paso.
 */
public class ControllerCarrera {

    private static final int PASOS_MAXIMOS = 15;

    /**
     * Simula una carrera entre los jugadores dados y devuelve el progreso
     * con todos los momentos (snapshots) de la carrera.
     *
     * @param jugadores      Lista de jugadores que participan en la carrera.
     * @param distanciaTotal Distancia total de la carrera en metros.
     * @return ProgresoCarrera con los momentos de la simulación.
     */
    public static ProgresoCarrera simularCarrera(List<Jugador> jugadores, double distanciaTotal) {
        List<MomentoCarrera> momentos = new ArrayList<>();

        // Resetear estado de simulación de cada caballo antes de empezar
        for (Jugador jugador : jugadores) {
            Caballo caballo = jugador.getCaballo();
            caballo.setDistanciaRecorrida(0);
            caballo.setEnergiaActual(caballo.getResistencia());
        }

        boolean carreraTerminada = false;

        for (int paso = 0; paso < PASOS_MAXIMOS && !carreraTerminada; paso++) {
            Map<String, Integer> snapshot = new LinkedHashMap<>();

            for (Jugador jugador : jugadores) {
                Caballo caballo = jugador.getCaballo();

                // Solo avanza si tiene energía
                if (caballo.getEnergiaActual() > 0) {
                    caballo.avanzar();
                    caballo.reducirEnergia();
                }

                // Calcular porcentaje de distancia recorrida (0–100)
                int porcentaje = (int) Math.min(100,
                        (caballo.getDistanciaRecorrida() / distanciaTotal) * 100);
                snapshot.put(jugador.getNombre(), porcentaje);

                if (porcentaje >= 100) {
                    carreraTerminada = true;
                }
            }

            momentos.add(new MomentoCarrera(snapshot));
        }

        return new ProgresoCarrera(momentos);
    }
}
