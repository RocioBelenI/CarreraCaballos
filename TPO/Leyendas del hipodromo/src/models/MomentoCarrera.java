package models;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MomentoCarrera {
    private final Map<String, Integer> progresoPorJugador;

    public MomentoCarrera(Map<String, Integer> progresoPorJugador) {
        this.progresoPorJugador = Collections.unmodifiableMap(new LinkedHashMap<>(progresoPorJugador));
    }

    public Map<String, Integer> getProgresoPorJugador() {
        return progresoPorJugador;
    }
}
