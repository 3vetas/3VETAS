package com.api.tresvetas.proyectos.entity;

import com.api.tresvetas.catalogo.entity.Acabado;
import com.api.tresvetas.catalogo.entity.Grosor;
import com.api.tresvetas.catalogo.entity.TipoTablero;
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
import jakarta.persistence.UniqueConstraint;
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
@Table(
        name = "TABLEROS_PROYECTO",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_tableros_proyecto_caracteristicas",
                    columnNames = {
                        "ID_proyecto",
                        "ID_tipo_tablero",
                        "ID_acabado",
                        "ID_grosor"
                    }
            )
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableroProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_tipo_tablero", nullable = false)
    private TipoTablero tipoTablero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_acabado", nullable = false)
    private Acabado acabado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_grosor", nullable = false)
    private Grosor grosor;
    
    @OneToMany(mappedBy = "tableroProyecto", fetch = FetchType.LAZY)
    private List<Pieza> piezas = new ArrayList<>();
}