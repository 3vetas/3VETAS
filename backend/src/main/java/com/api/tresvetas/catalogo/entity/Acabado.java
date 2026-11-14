package com.api.tresvetas.catalogo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "ACABADOS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Acabado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "familia", length = 120)
    private String familia;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "color_base", length = 100)
    private String colorBase;

    @Column(name = "textura", length = 120)
    private String textura;

    @Column(name = "fabricante", length = 150)
    private String fabricante;

    @Column(name = "cod_fabricante", length = 100)
    private String codFabricante;
}