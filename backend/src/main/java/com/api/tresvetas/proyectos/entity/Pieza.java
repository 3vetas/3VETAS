package com.api.tresvetas.proyectos.entity;

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
@Table(name = "PIEZAS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_tableros_proyecto", nullable = false)
    private TableroProyecto tableroProyecto;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "ancho_mm", nullable = false, precision = 12, scale = 4)
    private BigDecimal anchoMm;

    @Column(name = "alto_mm", nullable = false, precision = 12, scale = 4)
    private BigDecimal altoMm;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "rotacion_permitida", nullable = false)
    private Boolean rotacionPermitida;
}