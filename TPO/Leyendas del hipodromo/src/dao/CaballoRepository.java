package dao;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import models.Caballo;
import models.CaballoVeloz;
import models.CaballoResistente;
import models.CaballoEquilibrado;

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
            List<Caballo> resultados = em.createQuery(
                    "SELECT c FROM Caballo c WHERE c.nombre = :nombre", Caballo.class)
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

    /**
     * Carga los datos iniciales de caballos si la tabla está vacía.
     * Instancia las entidades reales (con su tipo/comportamiento) directamente.
     */
    public void cargarDatosCaballo() {
        List<Caballo> caballos = listarCaballos();
        if (!caballos.isEmpty()) {
            return;
        }

        // ⚡ Relámpago (Veloz): sprint puro. Vel altísima, poca resistencia.
        //    Agota en ~8 turnos → gana pistas ≤ 600m
        guardar(new CaballoVeloz("veloz", "Relámpago", "⚡", 90, 70));

        // 🌩️ Tormenta (Resistente): fondista. Vel moderada, máxima resistencia.
        //    Dura ~33 turnos a ritmo estable → gana pistas > 1200m
        guardar(new CaballoResistente("resistente", "Tormenta", "🌩️", 72, 100));

        // 🌀 Brisa (Equilibrado clásico): balance perfecto.
        //    ~18 turnos de energía → zona óptima 700-1500m
        guardar(new CaballoEquilibrado("equilibrado", "Brisa", "🌀", 80, 80));

        // 🔥 Centella (Equilibrado ofensivo): más veloz que Brisa, levemente menos resistente.
        //    ~17 turnos de energía → zona óptima 600-1200m
        guardar(new CaballoEquilibrado("fuerte", "Centella", "🔥", 82, 74));

    }
}