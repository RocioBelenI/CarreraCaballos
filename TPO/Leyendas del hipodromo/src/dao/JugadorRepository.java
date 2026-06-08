package dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import config.JPAUtil;

public class JugadorRepository extends GenericJpaRepository<JugadorDAO, Long> {

    private static JugadorRepository instance;

    private JugadorRepository() {
        super(JugadorDAO.class);
    }

    public static JugadorRepository getInstance() {
        if (instance == null) {
            instance = new JugadorRepository();
        }
        return instance;
    }

    // Usamos JugadorDAO en lugar del modelo Jugador
    public List<JugadorDAO> listarJugadores() {
        return listarTodos();
    }

    // Usamos JugadorDAO en lugar del modelo Jugador
    public JugadorDAO buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            List<JugadorDAO> resultados = em.createQuery("SELECT p FROM JugadorDAO p WHERE p.nombre = :nombre", JugadorDAO.class)
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

    // Usamos JugadorDAO en lugar del modelo Jugador
    public JugadorDAO guardarJugador(JugadorDAO jugador) {
        return guardar(jugador);
    }
}