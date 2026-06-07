package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProgresoCarrera {
    private final List<MomentoCarrera> momentos;

    public ProgresoCarrera(List<MomentoCarrera> momentos) {
        this.momentos = Collections.unmodifiableList(new ArrayList<>(momentos));
    }

    public List<MomentoCarrera> getMomentos() {
        return momentos;
    }

    public static ProgresoCarrera desdeListaDeMapas(List<Map<String, Integer>> mapasDeMomentos) {
        List<MomentoCarrera> listaMomentos = new ArrayList<>();
        for (Map<String, Integer> mapa : mapasDeMomentos) {
            listaMomentos.add(new MomentoCarrera(new LinkedHashMap<>(mapa)));
        }
        return new ProgresoCarrera(listaMomentos);
    }
}