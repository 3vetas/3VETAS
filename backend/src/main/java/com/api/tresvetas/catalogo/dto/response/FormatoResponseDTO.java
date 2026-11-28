package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "anchoMm",
    "altoMm"
})
public record FormatoResponseDTO(
        Integer id,
        BigDecimal anchoMm,
        BigDecimal altoMm
) {
}