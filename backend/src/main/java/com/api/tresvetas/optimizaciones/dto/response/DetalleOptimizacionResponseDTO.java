package com.api.tresvetas.optimizaciones.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "tableroId",
    "cantidad",
    "precio",
    "unidades"
})
public record DetalleOptimizacionResponseDTO(
        Integer id,
        Integer tableroId,
        Integer cantidad,
        BigDecimal precio,
        List<UnidadTableroOptimizacionResponseDTO> unidades
) {

}
