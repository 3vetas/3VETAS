package com.api.tresvetas.pedidos.entity;

import com.api.tresvetas.optimizaciones.entity.Optimizacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(
        name = "LINEAS_PEDIDO",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_lineas_pedido_optimizacion",
                    columnNames = {"ID_pedido", "ID_optimizacion"}
            )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_optimizacion", nullable = false)
    private Optimizacion optimizacion;

    @Column(name = "importe", nullable = false, precision = 10, scale = 2)
    private BigDecimal importe;
}