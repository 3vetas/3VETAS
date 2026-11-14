package com.api.tresvetas.optimizaciones.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(
        name = "UNIDADES_TABLERO_OPTIMIZACION",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_unidades_detalle_numero",
                    columnNames = {
                        "ID_detalle_optimizacion",
                        "numero_tablero"
                    }
            )
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnidadTableroOptimizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_detalle_optimizacion", nullable = false)
    private DetalleOptimizacion detalleOptimizacion;

    @Column(name = "numero_tablero", nullable = false)
    private Integer numeroTablero;
    
    @OneToMany(mappedBy = "unidadTablero", fetch = FetchType.LAZY)
    private List<DistribucionPieza> distribuciones = new ArrayList<>();
}