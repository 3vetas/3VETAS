package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "tipoTableroId",
    "acabadoId",
    "formatoId",
    "grosorId",
    "referencia",
    "descripcion",
    "precioTablero",
    "stockActual",
    "activo"
})
public record TableroResponseDTO(
        Integer id,
        Integer tipoTableroId,
        Integer acabadoId,
        Integer formatoId,
        Integer grosorId,
        String referencia,
        String descripcion,
        BigDecimal precioTablero,
        Integer stockActual,
        Boolean activo
) {
}