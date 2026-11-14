package com.api.tresvetas.pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "optimizacionId",
    "importe"
})
public record LineaPedidoResponseDTO(
        Integer id,
        Integer optimizacionId,
        BigDecimal importe
) {

}