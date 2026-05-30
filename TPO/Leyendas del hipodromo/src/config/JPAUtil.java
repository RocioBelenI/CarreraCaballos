package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final JPAUtil INSTANCE = new JPAUtil();
    private final EntityManagerFactory emf;

    private JPAUtil() {
        emf = Persistence.createEntityManagerFactory("carreraPU");
    }

    public static JPAUtil getInstance() {
        return INSTANCE;
    }

    public EntityManager crearEntityManager() {
        return emf.createEntityManager();
    }
}
