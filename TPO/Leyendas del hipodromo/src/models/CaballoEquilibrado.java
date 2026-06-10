package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad JPA para el caballo equilibrado.
 *
 * PERFIL: Balance real entre velocidad y resistencia. Arranca bien (80-82m/turno)
 * y mantiene el 60% de su velocidad al agotarse. Su energía dura 15 turnos,
 * lo que equivale a ~1000m de carrera antes de degradarse.
 * Supera al Veloz agotado pero pierde ante Tormenta en pistas muy largas.
 *
 * Distancia óptima:
 *   Centella 🔥 (vel=82, res=74): 500–1200m
 *   Brisa   🌀 (vel=80, res=80): 600–1500m
 *
 * Fórmula avanzar:
 *   factor = 0.60 + (energiaActual / resistencia) × 0.40   → 0.60 a 1.00
 *   avance = velocidadBase × factor × motivacion
 *   → Inicio: 80m/turno. Agotado: 48m/turno.
 *
 * Fórmula reducirEnergia:
 *   drain = resistencia / 15  por turno  → agota en 15 turnos
 *   floor = 1
 *
 * También representa el tipo "fuerte" (Centella, código="fuerte") con el
 * mismo discriminador JPA ("equilibrado") pero con distintas estadísticas.
 */
@Entity
@DiscriminatorValue("equilibrado")
public class CaballoEquilibrado extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoEquilibrado() {
        super();
        this.penalidadCansancio = 0.40;
    }

    public CaballoEquilibrado(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.40;
    }

    /**
     * Avance balanceado: factor entre 0.60 y 1.00.
     * Rango más amplio que Resistente → degrada más con el cansancio,
     * pero parte de una velocidad base mayor.
     * Sin multiplicador de explosividad: no aplasta al inicio, no colapsa al final.
     */
    @Override
    public void avanzar() {
        double factorEnergia = 0.60 + (getEnergiaActual() / getResistencia()) * 0.40; // 0.60 → 1.00
        double avance = getVelocidadBase() * factorEnergia * getMotivacion();
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    /**
     * Consume resistencia/15 de energía por turno.
     * Con resistencia=80 → 5.33/turno → agota en 15 turnos (~1000m).
     * Con resistencia=74 (Centella) → 4.93/turno → agota en ~15 turnos (~950m).
     * Floor=1.
     */
    @Override
    public void reducirEnergia() {
        this.energiaActual -= getResistencia() / 15.0;
        if (this.energiaActual < 1) {
            this.energiaActual = 1;
        }
    }
}