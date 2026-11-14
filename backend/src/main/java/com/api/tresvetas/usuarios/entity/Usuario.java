package com.api.tresvetas.usuarios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una cuenta de usuario de la aplicación.
 *
 * Almacena los datos de acceso, el rol asignado y la información
 * relacionada con el estado y la actividad de la cuenta.
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_rol", nullable = false)
    private Rol rol;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(
        name = "fecha_creacion",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;
}
