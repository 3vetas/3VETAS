package com.api.tresvetas.documentos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "optimizacionId",
    "pedidoId",
    "tipoDocumento",
    "nombreOriginal",
    "nombreFisico",
    "extension",
    "tamanoBytes",
    "rutaArchivo",
    "fechaGeneracion",
    "activo"
})
public record DocumentoResponseDTO(
        Integer id,
        Integer optimizacionId,
        Integer pedidoId,
        String tipoDocumento,
        String nombreOriginal,
        String nombreFisico,
        String extension,
        Long tamanoBytes,
        String rutaArchivo,
        LocalDateTime fechaGeneracion,
        Boolean activo
) {

}
