package com.api.tresvetas.optimizaciones.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "piezaId",
    "numeroUnidad",
    "ordenDibujo",
    "posicionXmm",
    "posicionYmm",
    "anchoFinalMm",
    "altoFinalMm",
    "rotada"
})
public record DistribucionPiezaResponseDTO(
        Integer id,
        Integer piezaId,
        Integer numeroUnidad,
        Integer ordenDibujo,
        BigDecimal posicionXmm,
        BigDecimal posicionYmm,
        BigDecimal anchoFinalMm,
        BigDecimal altoFinalMm,
        Boolean rotada
) {

}
