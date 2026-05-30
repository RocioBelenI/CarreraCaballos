package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.MomentoCarrera;
import models.ProgresoCarrera;

public class ControladorAnimacionCarrera {
    
    public static ProgresoCarrera crearProgresoCarreraDeMuestra(List<String> jugadores) {
        List<MomentoCarrera> momentos = new ArrayList<>();
        int cantidadJugadores = jugadores.size();
        int[] progreso = new int[cantidadJugadores];

        for (int paso = 0; paso < 10; paso++) { // Le damos margen de pasos
            Map<String, Integer> momento = new LinkedHashMap<>();
            boolean alguienLlego = false;

            for (int i = 0; i < cantidadJugadores; i++) {
                // Mantenemos al último caballo como el más rápido para probar
                int incremento = 10 + (i * 5); 
                progreso[i] = Math.min(100, progreso[i] + incremento);
                momento.put(jugadores.get(i), progreso[i]);

                if (progreso[i] >= 100) {
                    alguienLlego = true; // ¡Tenemos un ganador!
                }
            }
            momentos.add(new MomentoCarrera(momento));

            // Si alguien cruzó la meta, cortamos la animación para que no empaten
            if (alguienLlego) {
                break; 
            }
        }

        return new ProgresoCarrera(momentos);
    }
    
    public static ProgresoCarrera crearProgresoCarreraDesdeBackend(List<Map<String, Integer>> momentosBackend) {
        return ProgresoCarrera.desdeListaDeMapas(momentosBackend);
    }
}