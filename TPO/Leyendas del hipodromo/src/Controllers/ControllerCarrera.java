package controllers;

import models.Caballo;
import models.Jugador;
import models.MomentoCarrera;
import models.ProgresoCarrera;

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

    /**
     * Simula una carrera entre los jugadores dados y devuelve el progreso
     * con todos los momentos (snapshots) de la carrera.
     *
     * @param jugadores      Lista de jugadores que participan en la carrera.
     * @param distanciaMeta  Distancia total de la carrera en metros.
     * @return ProgresoCarrera con los momentos de la simulación.
     */
    public static ProgresoCarrera simularCarrera(List<Jugador> jugadores, double distanciaMeta) {
        List<MomentoCarrera> momentos = new ArrayList<>();

        // 1. Preparar el estado de simulación de cada caballo antes de empezar
        for (Jugador jugador : jugadores) {
            jugador.getCaballo().prepararParaCarrera();
        }

        boolean carreraTerminada = false;

        // 2. Bucle de Simulación por turnos
        while (!carreraTerminada) {
            Map<String, Integer> progresoTurno = new LinkedHashMap<>();
            boolean alguienCruzoMeta = false;

            for (Jugador jugador : jugadores) {
                Caballo caballo = jugador.getCaballo();

                // El caballo avanza y se cansa según su tipo (Veloz, Resistente, Equilibrado)
                caballo.avanzar();
                
                // Convertir la distancia en porcentaje (0 a 100)
                double distancia = caballo.getDistanciaRecorrida();
                int porcentaje = (int) Math.min(100, (distancia / distanciaMeta) * 100);
                progresoTurno.put(jugador.getNombre(), porcentaje);

                if (porcentaje >= 100) {
                    alguienCruzoMeta = true; // ¡Tenemos un ganador!
                }
            }

            // Guardamos la foto del momento actual para la animación
            momentos.add(new MomentoCarrera(progresoTurno));

            if (alguienCruzoMeta) {
                carreraTerminada = true;
            }
        }

        return new ProgresoCarrera(momentos);
    }
}