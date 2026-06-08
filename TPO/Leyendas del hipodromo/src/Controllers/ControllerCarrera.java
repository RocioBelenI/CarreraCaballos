package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import data.dao.CaballoDAO;
import data.dao.JugadorDAO;
import models.Caballo;
import models.MomentoCarrera;
import models.ProgresoCarrera;

public class ControllerCarrera {

    // Se asume 800m por defecto para la simulacion, o se puede parametrizar
    public static ProgresoCarrera simularCarrera(List<JugadorDAO> jugadoresDAO, List<CaballoDAO> caballosDAO, int distanciaMeta) {
        List<MomentoCarrera> momentos = new ArrayList<>();
        List<Caballo> corredores = new ArrayList<>();
        List<String> nombresJugadores = new ArrayList<>();

        // 1. Instanciar caballos y asociarlos al jugador
        for (JugadorDAO jugador : jugadoresDAO) {
            // Buscamos el caballo DAO correspondiente
            CaballoDAO cabDAO = caballosDAO.stream()
                .filter(c -> c.getId().equals(jugador.getCaballo()))
                .findFirst()
                .orElse(null);

            Caballo modeloCaballo = ControllerCaballo.crearCaballoDesdeDAO(cabDAO);
            modeloCaballo.prepararParaCarrera();
            corredores.add(modeloCaballo);
            nombresJugadores.add(jugador.getNombre());
        }

        boolean carreraTerminada = false;

        // 2. Bucle de Simulación por turnos
        while (!carreraTerminada) {
            Map<String, Integer> progresoTurno = new LinkedHashMap<>();
            boolean alguienCruzoMeta = false;

            for (int i = 0; i < corredores.size(); i++) {
                Caballo caballo = corredores.get(i);
                String nombreJugador = nombresJugadores.get(i);

                // El caballo avanza y se cansa
                caballo.avanzar();
                caballo.reducirEnergia();

                // Convertir la distancia en porcentaje (0 a 100)
                double distancia = caballo.getDistanciaRecorrida();
                int porcentaje = (int) Math.min(100, (distancia / distanciaMeta) * 100);
                progresoTurno.put(nombreJugador, porcentaje);

                if (porcentaje >= 100) {
                    alguienCruzoMeta = true; // ¡Tenemos un ganador!
                }
            }

            momentos.add(new MomentoCarrera(progresoTurno));

            if (alguienCruzoMeta) {
                carreraTerminada = true;
            }
        }

        return new ProgresoCarrera(momentos);
    }
    
    /* 
     * Sugerencia de Integración en la Interfaz (UI) - AppFrame.java
     * -------------------------------------------------------------
     * public void startRaceWithPlayers(List<String> selectedPlayers) {
     *     List<data.dao.JugadorDAO> todosJugadores = controllers.UiController.getJugadores();
     *     List<data.dao.CaballoDAO> todosCaballos = controllers.UiController.getCaballosDisponibles();
     *      
     *     List<data.dao.JugadorDAO> participantes = todosJugadores.stream()
     *         .filter(j -> selectedPlayers.contains(j.getNombre()))
     *         .collect(java.util.stream.Collectors.toList());
     *
     *     models.ProgresoCarrera progress = controllers.ControllerCarrera.simularCarrera(participantes, todosCaballos, 800);
     *     startRace(progress);
     * }
     */
}
