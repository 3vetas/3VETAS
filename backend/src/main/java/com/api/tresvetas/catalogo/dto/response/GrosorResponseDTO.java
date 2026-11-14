package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({"id", "espesor_mm"})
public record GrosorResponseDTO(
        Integer id,
        BigDecimal espesorMm
) {
}