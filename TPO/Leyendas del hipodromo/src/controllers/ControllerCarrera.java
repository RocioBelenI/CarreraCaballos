package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Caballo;
import models.Jugador;
import models.MomentoCarrera;
import models.ProgresoCarrera;

public class ControllerCarrera {

    public static ProgresoCarrera simularCarrera(List<Jugador> jugadores, int distanciaMeta) {
        List<MomentoCarrera> momentos = new ArrayList<>();
        List<Caballo> corredores = new ArrayList<>();
        List<String> nombresJugadores = new ArrayList<>();

        // 1. Obtener los caballos de los jugadores y prepararlos
        for (Jugador jugador : jugadores) {
            Caballo caballo = jugador.getCaballo();
            if (caballo != null) {
                caballo.prepararParaCarrera();
                corredores.add(caballo);
                nombresJugadores.add(jugador.getNombre());
            }
        }

        boolean carreraTerminada = false;

        // 2. Bucle de Simulación por turnos
        while (!carreraTerminada) {
            Map<String, Integer> progresoTurno = new LinkedHashMap<>();
            boolean alguienCruzoMeta = false;

            for (int i = 0; i < corredores.size(); i++) {
                Caballo caballo = corredores.get(i);
                String nombreJugador = nombresJugadores.get(i);

                caballo.avanzar();

                double distancia = caballo.getDistanciaRecorrida();
                int porcentaje = (int) Math.min(100, (distancia / distanciaMeta) * 100);
                progresoTurno.put(nombreJugador, porcentaje);

                if (porcentaje >= 100) {
                    alguienCruzoMeta = true;
                }
            }

            momentos.add(new MomentoCarrera(progresoTurno));

            if (alguienCruzoMeta) {
                carreraTerminada = true;
            }
        }

        return new ProgresoCarrera(momentos);
    }
}
