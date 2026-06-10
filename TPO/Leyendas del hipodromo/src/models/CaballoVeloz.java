package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad JPA para el caballo veloz.
 *
 * PERFIL: Sprint puro. Arranca con una velocidad explosiva (135m/turno)
 * y se agota completamente en ~8 turnos. Al agotarse, avanza ~2m/turno
 * (prácticamente detenido). La distancia de la pista determina si esos
 * 8 turnos son suficientes para llegar antes que los demás.
 *
 * Distancia óptima: ≤ 600m. Pierde en pistas más largas.
 *
 * Fórmula avanzar:
 *   factor = energiaActual / resistencia          → 0.0 a 1.0 (sin mínimo)
 *   avance = velocidadBase × factor × 1.5 × motivacion
 *   → Inicio: vel × 1.5 = 135m/turno. Agotado: ≈ 2m/turno.
 *
 * Fórmula reducirEnergia:
 *   drain = resistencia / 8  por turno  → agota en 8 turnos
 *   floor = 1 (prácticamente detenido, avance ≈ 2m/turno)
 */
@Entity
@DiscriminatorValue("veloz")
public class CaballoVeloz extends Caballo {

    private static final double MULTIPLICADOR_VELOCIDAD = 1.5;

    // Constructor vacío requerido por JPA
    protected CaballoVeloz() {
        super();
        this.penalidadCansancio = 0.25;
    }

    public CaballoVeloz(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.25;
    }

    /**
     * Avance explosivo: la velocidad es directamente proporcional a la energía.
     * Sin mínimo → al agotarse (energía=1) avanza ≈ 2m/turno en lugar de
     * los 135m iniciales. La distancia de la pista determina si los 8 turnos
     * con energía alcanzan para llegar antes que los demás.
     */
    @Override
    public void avanzar() {
        double factorEnergia = getEnergiaActual() / getResistencia(); // 0.0 → 1.0 sin mínimo
        double avance = getVelocidadBase() * factorEnergia * MULTIPLICADOR_VELOCIDAD * getMotivacion();
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    /**
     * Consume resistencia/8 de energía por turno.
     * Con resistencia=70 → 8.75/turno → agota en 8 turnos efectivos.
     * Floor=1: al agotarse el factor cae a 1/70 ≈ 0.014 → ~2m/turno.
     */
    @Override
    public void reducirEnergia() {
        this.energiaActual -= getResistencia() / 8.0;
        if (this.energiaActual < 1) {
            this.energiaActual = 1;
        }
    }
}