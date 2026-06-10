package models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad JPA para el caballo resistente.
 *
 * PERFIL: Fondista. Arranca a velocidad moderada (~72m/turno) y mantiene
 * el 75% de esa velocidad incluso al agotarse. Su energía dura 25 turnos,
 * lo que equivale a ~1800m de carrera antes de degradarse.
 *
 * Distancia óptima: > 800m. En pistas cortas pierde frente al sprint del
 * Veloz (que termina antes de que el turno 8 llegue).
 *
 * Fórmula avanzar:
 *   factor = 0.75 + (energiaActual / resistencia) × 0.25   → 0.75 a 1.00
 *   avance = velocidadBase × factor × motivacion
 *   → Inicio: 72m/turno. Agotado: 54m/turno.
 *
 * Fórmula reducirEnergia:
 *   drain = resistencia / 25  por turno  → agota en 25 turnos
 *   floor = 1
 */
@Entity
@DiscriminatorValue("resistente")
public class CaballoResistente extends Caballo {

    // Constructor vacío requerido por JPA
    protected CaballoResistente() {
        super();
        this.penalidadCansancio = 0.60;
    }

    public CaballoResistente(String codigo, String nombre, String emoji, double velocidadBase, double resistencia) {
        super(codigo, nombre, emoji, velocidadBase, resistencia);
        this.penalidadCansancio = 0.60;
    }

    /**
     * Ritmo casi constante: el factor varía sólo entre 0.75 y 1.00.
     * Incluso completamente agotado mantiene el 75% de su velocidad base.
     * Sin multiplicador → no lidera al inicio, pero es constante en el largo.
     */
    @Override
    public void avanzar() {
        double factorEnergia = 0.75 + (getEnergiaActual() / getResistencia()) * 0.25; // 0.75 → 1.00
        double avance = getVelocidadBase() * factorEnergia * getMotivacion();
        this.distanciaRecorrida += avance;
        reducirEnergia();
    }

    /**
     * Consume resistencia/25 de energía por turno.
     * Con resistencia=100 → 4.0/turno → agota en 25 turnos efectivos (~1800m).
     * Floor=1: incluso agotado sigue a ~54m/turno (75% de su velocidad base).
     */
    @Override
    public void reducirEnergia() {
        this.energiaActual -= getResistencia() / 25.0;
        if (this.energiaActual < 1) {
            this.energiaActual = 1;
        }
    }
}