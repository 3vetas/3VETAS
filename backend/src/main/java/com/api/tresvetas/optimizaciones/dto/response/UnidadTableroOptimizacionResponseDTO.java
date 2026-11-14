package com.api.tresvetas.optimizaciones.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "numeroTablero",
    "distribuciones"
})
public record UnidadTableroOptimizacionResponseDTO(
        Integer id,
        Integer numeroTablero,
        List<DistribucionPiezaResponseDTO> distribuciones
) {

}
