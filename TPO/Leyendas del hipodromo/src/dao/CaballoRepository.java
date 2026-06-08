package dao;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CaballoRepository extends GenericJpaRepository<CaballoDAO, Long> {

    private static CaballoRepository instance;

    private CaballoRepository() {
        super(CaballoDAO.class);
    }

    public static CaballoRepository getInstance() {
        if (instance == null) {
            instance = new CaballoRepository();
        }
        return instance;
    }

    public List<CaballoDAO> listarCaballos() {
        return listarTodos();
    }

    public CaballoDAO buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            // Asegurate de que la query llame a la clase DAO correcta
            List<CaballoDAO> resultados = em.createQuery("SELECT c FROM CaballoDAO c WHERE c.nombre = :nombre", CaballoDAO.class)
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
        List<CaballoDAO> caballos = listarCaballos();
        if (!caballos.isEmpty()) {
            return;
        }

        // Instanciamos los DAOs, que son los que se guardan en la base de datos
        guardar(new CaballoDAO("veloz", "Relámpago", "⚡", 90, 60));
        guardar(new CaballoDAO("resistente", "Tormenta", "🌩️", 70, 90));
        guardar(new CaballoDAO("equilibrado", "Brisa", "🌀", 80, 80));
        guardar(new CaballoDAO("fuerte", "Centella", "🔥", 85, 70));
    }
}