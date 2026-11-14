package com.api.tresvetas.optimizaciones.entity;

import com.api.tresvetas.proyectos.entity.TableroProyecto;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "OPTIMIZACIONES")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Optimizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_tableros_proyecto", nullable = false)
    private TableroProyecto tableroProyecto;

    @Column(name = "nombre_opcion", nullable = false, length = 150)
    private String nombreOpcion;

    @Column(name = "superficie_utilizada", nullable = false,
            precision = 24, scale = 8)
    private BigDecimal superficieUtilizada;

    @Column(name = "superficie_desperdiciada", nullable = false,
            precision = 24, scale = 8)
    private BigDecimal superficieDesperdiciada;

    @Column(name = "porcentaje_aprovechamiento", nullable = false,
            precision = 5, scale = 2)
    private BigDecimal porcentajeAprovechamiento;

    @Column(name = "coste_estimado", nullable = false,
            precision = 10, scale = 2)
    private BigDecimal costeEstimado;

    @Column(name = "seleccionada", nullable = false)
    private Boolean seleccionada = false;

    @Column(name = "fecha_calculo", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime fechaCalculo;
    
    @OneToMany(mappedBy = "optimizacion", fetch = FetchType.LAZY)
    private List<DetalleOptimizacion> detalles = new ArrayList<>();
}
