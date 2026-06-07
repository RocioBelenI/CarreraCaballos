package data;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import data.dao.CaballoDAO;

import java.util.List;

public class CaballoRepository {
    
    public List<CaballoDAO> listarCaballos() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.createQuery("SELECT c FROM Caballo c ORDER BY c.id", CaballoDAO.class).getResultList();
        } finally {
            em.close();
        }
    }

    public CaballoDAO buscarPorId(int id) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.find(CaballoDAO.class, id);
        } finally {
            em.close();
        }
    }

    public CaballoDAO buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            // Actualizamos la consulta de JPQL para buscar por c.nombre
            List<CaballoDAO> resultados = em.createQuery("SELECT c FROM Caballo c WHERE c.nombre = :nombre", CaballoDAO.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    public CaballoDAO guardar(CaballoDAO caballo) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            if (caballo.getId() == null) {
                em.persist(caballo);
            } else {
                caballo = em.merge(caballo);
            }
            em.getTransaction().commit();
            return caballo;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void cargarDatosCaballo() {
        List<CaballoDAO> caballos = listarCaballos();
        if (!caballos.isEmpty()) {
            return;
        }

        guardar(new CaballoDAO("veloz", "Relámpago", "⚡", 90, 60));
        guardar(new CaballoDAO("resistente", "Tormenta", "🌩️", 70, 90));
        guardar(new CaballoDAO("equilibrado", "Brisa", "🌀", 80, 80));
        guardar(new CaballoDAO("fuerte", "Centella", "🔥", 85, 70));
    }
}
