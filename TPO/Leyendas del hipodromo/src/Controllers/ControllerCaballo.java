package controllers;

import data.dao.CaballoDAO;
import models.Caballo;
import models.CaballoVeloz;
import models.CaballoResistente;
import models.CaballoEquilibrado;

public class ControllerCaballo {
    
    public static Caballo crearCaballoDesdeDAO(CaballoDAO dao) {
        if (dao == null || dao.getCodigo() == null) {
            return new CaballoEquilibrado("Desconocido");
        }

        String tipo = dao.getCodigo().toLowerCase();
        Caballo caballo;
        
        switch (tipo) {
            case "veloz":
                caballo = new CaballoVeloz(dao.getNombre());
                break;
            case "resistente":
                caballo = new CaballoResistente(dao.getNombre());
                break;
            case "equilibrado":
            default:
                // Cualquier otro tipo (ej. "fuerte") se mapea a equilibrado por defecto
                caballo = new CaballoEquilibrado(dao.getNombre());
                break;
        }
        
        return caballo;
    }
}