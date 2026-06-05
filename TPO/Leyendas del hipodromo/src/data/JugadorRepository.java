package data;

import jakarta.persistence.EntityManager;
import data.dao.JugadorDAO;

import java.util.List;

import config.JPAUtil;

public class JugadorRepository {
    public List<JugadorDAO> listarJugadores() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.createQuery("SELECT p FROM Jugador p ORDER BY p.id", JugadorDAO.class).getResultList();
        } finally {
            em.close();
        }
    }

    public JugadorDAO buscarPorNombre(String name) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            List<JugadorDAO> resultados = em.createQuery("SELECT p FROM Jugador p WHERE p.name = :name", JugadorDAO.class)
                    .setParameter("name", name)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    public JugadorDAO guardarJugador(JugadorDAO jugador) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            if (jugador.getId() == null) {
                em.persist(jugador);
            } else {
                jugador = em.merge(jugador);
            }
            em.getTransaction().commit();
            return jugador;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}




