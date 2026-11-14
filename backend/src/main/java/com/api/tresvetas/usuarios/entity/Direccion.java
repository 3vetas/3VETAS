package com.api.tresvetas.usuarios.entity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una dirección de envío o facturación
 * asociada a una persona.
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "DIRECCIONES")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_persona", nullable = false)
    private Persona persona;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "tipo",
        nullable = false,
        columnDefinition = "ENUM('envio','facturacion')"
    )
    private TipoDireccion tipo;

    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;

    @Column(name = "cp", nullable = false, length = 5, columnDefinition = "CHAR(5)")
    private String cp;

    @Column(name = "municipio", nullable = false, length = 120)
    private String municipio;

    @Column(name = "provincia", nullable = false, length = 120)
    private String provincia;

    @Column(name = "pais", nullable = false, length = 120)
    private String pais;
}
