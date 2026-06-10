package controllers;

import dao.CaballoRepository;
import dto.CaballoDTO;
import models.Caballo;

import java.util.ArrayList;
import java.util.List;

public class ControllerCaballo {

    private final CaballoRepository repository = CaballoRepository.getInstance();

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
                    c.getResistencia(),
                    0.0
            ));
        }
        return listaParaVista;
    }
}
