package com.api.tresvetas.pedidos.entity;

import com.api.tresvetas.proyectos.entity.Proyecto;
import com.api.tresvetas.usuarios.entity.Direccion;
import com.api.tresvetas.usuarios.entity.Persona;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "PEDIDOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_persona", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_direccion_envio", nullable = false)
    private Direccion direccionEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_direccion_facturacion", nullable = false)
    private Direccion direccionFacturacion;

    @Column(name = "estado", nullable = false, length = 40)
    private String estado;

    @Column(name = "base_imponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseImponible;

    @Column(name = "descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(name = "importe_iva", nullable = false, precision = 10, scale = 2)
    private BigDecimal importeIva;

    @Column(name = "importe_final", nullable = false, precision = 10, scale = 2)
    private BigDecimal importeFinal;

    @Column(name = "fecha_pedido", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "fecha_actualizacion", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;
    
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<LineaPedido> lineas = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<PedidoEstado> historialEstados = new ArrayList<>();
}