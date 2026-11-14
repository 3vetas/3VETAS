package com.api.tresvetas.proyectos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;


/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "descripcion",
    "anchoMm",
    "altoMm",
    "cantidad",
    "rotacionPermitida"
})
public record PiezaResponseDTO(
        Integer id,
        String descripcion,
        BigDecimal anchoMm,
        BigDecimal altoMm,
        Integer cantidad,
        Boolean rotacionPermitida
        ) {
}
