package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import models.MomentoCarrera;
import models.ProgresoCarrera;

public class ControllerCarrera {

    public ProgresoCarrera crearProgreso(List<MomentoCarrera> momentos) {
        return new ProgresoCarrera(momentos);
    }

    public static ProgresoCarrera desdeListaDeMapas(List<Map<String, Integer>> mapasDeMomentos) {
        List<MomentoCarrera> listaMomentos = new ArrayList<>();
        for (Map<String, Integer> mapa : mapasDeMomentos) {
            listaMomentos.add(new MomentoCarrera(new LinkedHashMap<>(mapa)));
        }
        return new ProgresoCarrera(listaMomentos);
    }
}