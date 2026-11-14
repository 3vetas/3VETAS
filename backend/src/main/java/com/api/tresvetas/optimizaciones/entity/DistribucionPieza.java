
package com.api.tresvetas.optimizaciones.entity;

import com.api.tresvetas.proyectos.entity.Pieza;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "DISTRIBUCION_PIEZAS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DistribucionPieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_unidad_tablero", nullable = false)
    private UnidadTableroOptimizacion unidadTablero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_pieza", nullable = false)
    private Pieza pieza;

    @Column(name = "numero_unidad", nullable = false)
    private Integer numeroUnidad;

    @Column(name = "orden_dibujo", nullable = false)
    private Integer ordenDibujo;

    @Column(name = "posicion_x_mm", nullable = false,
            precision = 12, scale = 4)
    private BigDecimal posicionXmm;

    @Column(name = "posicion_y_mm", nullable = false,
            precision = 12, scale = 4)
    private BigDecimal posicionYmm;

    @Column(name = "ancho_final_mm", nullable = false,
            precision = 12, scale = 4)
    private BigDecimal anchoFinalMm;

    @Column(name = "alto_final_mm", nullable = false,
            precision = 12, scale = 4)
    private BigDecimal altoFinalMm;

    @Column(name = "rotada", nullable = false)
    private Boolean rotada = false;
}