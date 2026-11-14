package com.api.tresvetas.catalogo.entity;

import com.api.tresvetas.usuarios.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "MOVIMIENTOS_STOCK")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_tablero", nullable = false)
    private Tablero tablero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false,
            columnDefinition = "ENUM('entrada','salida','ajuste')")
    private TipoMovimientoStock tipoMovimiento;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_mov", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime fechaMov;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_usuario", nullable = false)
    private Usuario usuario;
}
