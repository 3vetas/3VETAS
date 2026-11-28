package com.api.tresvetas.documentos.dto.patch;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoPatchDTO {

    @Positive(message = "El identificador de la optimización debe ser positivo")
    private Integer optimizacionId;

    @Positive(message = "El identificador del pedido debe ser positivo")
    private Integer pedidoId;

    @Size(max = 80, message = "El tipo de documento no puede superar los 80 caracteres")
    @Pattern(regexp = ".*\\S.*", message = "El tipo de documento no puede estar vacío")
    private String tipoDocumento;

    @Size(max = 255, message = "El nombre original no puede superar los 255 caracteres")
    @Pattern(regexp = ".*\\S.*", message = "El nombre original no puede estar vacío")
    private String nombreOriginal;

    @Size(max = 255, message = "El nombre físico no puede superar los 255 caracteres")
    @Pattern(regexp = ".*\\S.*", message = "El nombre físico no puede estar vacío")
    private String nombreFisico;

    @Size(max = 20, message = "La extensión no puede superar los 20 caracteres")
    @Pattern(regexp = ".*\\S.*", message = "La extensión no puede estar vacía")
    private String extension;

    @PositiveOrZero(message = "El tamaño del archivo no puede ser negativo")
    private Long tamanoBytes;

    @Size(max = 1024, message = "La ruta del archivo no puede superar los 1024 caracteres")
    @Pattern(regexp = ".*\\S.*", message = "La ruta del archivo no puede estar vacía")
    private String rutaArchivo;

    private Boolean activo;
}
