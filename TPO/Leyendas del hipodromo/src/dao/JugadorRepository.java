package dao;

import jakarta.persistence.EntityManager;
import Models.Jugador;

import java.util.List;

import config.JPAUtil;

public class JugadorRepository extends GenericJpaRepository<Jugador, Long> {

    private static JugadorRepository instance;

    private JugadorRepository() {
        super(Jugador.class);
    }

    public static JugadorRepository getInstance() {
        if (instance == null) {
            instance = new JugadorRepository();
        }
        return instance;
    }

    public List<Jugador> listarJugadores() {
        return listarTodos();
    }

    public Jugador buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            List<Jugador> resultados = em.createQuery("SELECT p FROM Jugador p WHERE p.nombre = :nombre", Jugador.class)
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

    public Jugador guardarJugador(Jugador jugador) {
        return guardar(jugador);
    }
}
