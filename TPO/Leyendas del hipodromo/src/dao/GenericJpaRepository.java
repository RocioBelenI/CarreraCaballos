package dao;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class GenericJpaRepository<T, ID> {

    protected final Class<T> entityClass;

    protected GenericJpaRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T buscarPorId(ID id) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public List<T> listarTodos() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        } finally {
            em.close();
        }
    }

    public T guardar(T entity) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
            T resultado;
            if (id == null) {
                em.persist(entity);
                resultado = entity;
            } else {
                resultado = em.merge(entity);
            }
            em.getTransaction().commit();
            return resultado;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void eliminar(T entity) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            T mergedEntity = em.merge(entity);
            em.remove(mergedEntity);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
