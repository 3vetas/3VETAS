package com.api.tresvetas.catalogo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "FORMATOS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Formato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ancho_mm", nullable = false, precision = 12, scale = 4)
    private BigDecimal anchoMm;

    @Column(name = "alto_mm", nullable = false, precision = 12, scale = 4)
    private BigDecimal altoMm;
}