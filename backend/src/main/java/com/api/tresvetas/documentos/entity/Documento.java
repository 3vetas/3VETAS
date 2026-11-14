package com.api.tresvetas.documentos.entity;

import com.api.tresvetas.optimizaciones.entity.Optimizacion;
import com.api.tresvetas.pedidos.entity.Pedido;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Entity
@Table(name = "DOCUMENTOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_optimizacion")
    private Optimizacion optimizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_pedido")
    private Pedido pedido;

    @Column(name = "tipo_documento", nullable = false, length = 80)
    private String tipoDocumento;

    @Column(name = "nombre_original", nullable = false, length = 255)
    private String nombreOriginal;

    @Column(name = "nombre_fisico", nullable = false, length = 255)
    private String nombreFisico;

    @Column(name = "extension", nullable = false, length = 20)
    private String extension;

    @Column(name = "tamano_bytes", nullable = false)
    private Long tamanoBytes;

    @Column(name = "ruta_archivo", nullable = false, length = 1024)
    private String rutaArchivo;

    @Column(name = "fecha_generacion", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
