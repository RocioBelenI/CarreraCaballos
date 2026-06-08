package controllers;

import dao.CaballoRepository;
import dao.CaballoDAO;
import dto.CaballoDTO;
import models.Caballo;
import models.CaballoVeloz;
import models.CaballoResistente;
import models.CaballoEquilibrado;

import java.util.ArrayList;
import java.util.List;

public class ControllerCaballo {
    
    // CORRECCIÓN 1: Usamos el Singleton en lugar del constructor privado (new)
    private final CaballoRepository repository = CaballoRepository.getInstance();

    /**
     * Used by the UI to display horses without exposing the database DAOs.
     */
    public List<CaballoDTO> listarCaballosParaUI() {
        List<CaballoDTO> listaParaVista = new ArrayList<>();
        List<CaballoDAO> caballosDB = repository.listarCaballos(); 
        
        for (CaballoDAO c : caballosDB) {
            listaParaVista.add(new CaballoDTO(
                c.getId().toString(),
                c.getNombre(),
                c.getEmoji(),
                (double) c.getVelocidadBase(), // CORRECCIÓN 2: getVelocidadBase()
                (double) c.getResistencia(),
                100.0, // Default full energy
                0.0    // Default 0 distance
            ));
        }
        return listaParaVista;
    }

    /**
     * Used by the Simulation Controller to convert DB data into interactive Models.
     */
    public static Caballo crearCaballoDesdeDAO(CaballoDAO dao) {
        // CORRECCIÓN 3: getTipo() en lugar de getCodigo()
        if (dao == null || dao.getTipo() == null) {
            return new CaballoEquilibrado("Desconocido");
        }

        String tipo = dao.getTipo().toLowerCase(); 
        
        switch (tipo) {
            case "veloz":
                return new CaballoVeloz(dao.getNombre());
            case "resistente":
                return new CaballoResistente(dao.getNombre());
            case "equilibrado":
            case "fuerte":
            default:
                return new CaballoEquilibrado(dao.getNombre());
        }
    }
}