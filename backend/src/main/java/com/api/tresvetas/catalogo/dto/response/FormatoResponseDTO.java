package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "ancho_mm",
    "alto_mm"
})
public record FormatoResponseDTO(
        Integer id,
        @JsonProperty("ancho_mm") BigDecimal anchoMm,
        @JsonProperty("alto_mm") BigDecimal altoMm
        ) {
}