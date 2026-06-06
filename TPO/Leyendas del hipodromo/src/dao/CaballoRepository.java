package dao;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import Models.Caballo;

import java.util.List;

public class CaballoRepository extends GenericJpaRepository<Caballo, Long> {

    private static CaballoRepository instance;

    private CaballoRepository() {
        super(Caballo.class);
    }

    public static CaballoRepository getInstance() {
        if (instance == null) {
            instance = new CaballoRepository();
        }
        return instance;
    }

    public List<Caballo> listarCaballos() {
        return listarTodos();
    }

    public Caballo buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            List<Caballo> resultados = em.createQuery("SELECT c FROM Caballo c WHERE c.nombre = :nombre", Caballo.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
            em.getTransaction().commit();
            return resultados.isEmpty() ? null : resultados.get(0);
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void cargarDatosCaballo() {
        List<Caballo> caballos = listarCaballos();
        if (!caballos.isEmpty()) {
            return;
        }

        guardar(new Models.CaballoVeloz("veloz", "Relámpago", "⚡", 90, 60));
        guardar(new Models.CaballoResistente("resistente", "Tormenta", "🌩️", 70, 90));
        guardar(new Models.CaballoEquilibrado("equilibrado", "Brisa", "🌀", 80, 80));
        guardar(new Models.CaballoVeloz("fuerte", "Centella", "🔥", 85, 70));
    }
}