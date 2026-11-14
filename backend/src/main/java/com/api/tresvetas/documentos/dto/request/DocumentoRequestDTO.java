package com.api.tresvetas.documentos.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DocumentoRequestDTO {

    @Positive(message = "El identificador de la optimización debe ser positivo")
    private Integer optimizacionId;

    @Positive(message = "El identificador del pedido debe ser positivo")
    private Integer pedidoId;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Size(max = 80, message = "El tipo de documento no puede superar los 80 caracteres")
    private String tipoDocumento;

    @NotBlank(message = "El nombre original es obligatorio")
    @Size(max = 255, message = "El nombre original no puede superar los 255 caracteres")
    private String nombreOriginal;

    @NotBlank(message = "El nombre físico es obligatorio")
    @Size(max = 255, message = "El nombre físico no puede superar los 255 caracteres")
    private String nombreFisico;

    @NotBlank(message = "La extensión es obligatoria")
    @Size(max = 20, message = "La extensión no puede superar los 20 caracteres")
    private String extension;

    @NotNull(message = "El tamaño del archivo es obligatorio")
    @PositiveOrZero(message = "El tamaño del archivo no puede ser negativo")
    private Long tamanoBytes;

    @NotBlank(message = "La ruta del archivo es obligatoria")
    @Size(max = 1024, message = "La ruta del archivo no puede superar los 1024 caracteres")
    private String rutaArchivo;

    @AssertTrue(message = "El documento debe pertenecer a una optimización o a un pedido, pero no a ambos")
    public boolean isReferenciaValida() {
        return (optimizacionId != null) != (pedidoId != null);
    }
}
