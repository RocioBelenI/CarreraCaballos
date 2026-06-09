package controllers;

import dao.CaballoRepository;
import dto.CaballoDTO;
import models.Caballo;

import java.util.ArrayList;
import java.util.List;

public class ControllerCaballo {

    private final CaballoRepository repository = CaballoRepository.getInstance();

    /**
     * Convierte las entidades Caballo de la BD en DTOs planos para la UI.
     * La UI nunca ve entidades JPA directamente.
     */
    public List<CaballoDTO> listarCaballosParaUI() {
        List<CaballoDTO> listaParaVista = new ArrayList<>();
        List<Caballo> caballosDB = repository.listarCaballos();

        for (Caballo c : caballosDB) {
            listaParaVista.add(new CaballoDTO(
                c.getId().toString(),
                c.getNombre(),
                c.getEmoji(),
                c.getVelocidadBase(),
                c.getResistencia(),
                c.getResistencia(), // Energía inicial = resistencia
                0.0                 // Distancia inicial = 0
            ));
        }
        return listaParaVista;
    }
}