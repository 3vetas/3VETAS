package com.api.tresvetas.optimizaciones.entity;

import com.api.tresvetas.catalogo.entity.Tablero;
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
import java.math.BigDecimal;
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
        name = "DETALLE_OPTIMIZACION",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_detalle_optimizacion_tablero",
                    columnNames = {
                        "ID_optimizacion",
                        "ID_tablero"
                    }
            )
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetalleOptimizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_optimizacion", nullable = false)
    private Optimizacion optimizacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_tablero", nullable = false)
    private Tablero tablero;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @OneToMany(mappedBy = "detalleOptimizacion", fetch = FetchType.LAZY)
    private List<UnidadTableroOptimizacion> unidades = new ArrayList<>();
}