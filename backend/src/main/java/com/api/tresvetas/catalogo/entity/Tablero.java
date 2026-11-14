package com.api.tresvetas.catalogo.entity;

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
@Table(name = "TABLEROS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tablero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_tipo", nullable = false)
    private TipoTablero tipoTablero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_acabado", nullable = false)
    private Acabado acabado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_formato", nullable = false)
    private Formato formato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_grosor", nullable = false)
    private Grosor grosor;

    @Column(name = "referencia", nullable = false, unique = true, length = 100)
    private String referencia;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio_tablero", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTablero;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}