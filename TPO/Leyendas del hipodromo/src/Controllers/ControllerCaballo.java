package controllers;

import data.CaballoRepository;
import data.dao.CaballoDAO;
import dto.CaballoDTO;
import java.util.ArrayList;
import java.util.List;

public class ControllerCaballo {
	
    private CaballoRepository repository = new CaballoRepository();

    public List<CaballoDTO> listarCaballosParaUI() {
        List<CaballoDTO> listaParaVista = new ArrayList<>();

        List<CaballoDAO> caballosDB = repository.listarCaballos(); 

        
        for (CaballoDAO c : caballosDB) {
            listaParaVista.add(new CaballoDTO(
                c.getId().toString(),
                c.getNombre(),
                c.getEmoji(),
                (double) c.getVelocidad(), 
                (double) c.getResistencia(),
                100.0,
                0.0
            ));
        }
        return listaParaVista;
    }
}