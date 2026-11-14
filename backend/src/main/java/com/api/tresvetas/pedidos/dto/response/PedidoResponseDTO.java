package com.api.tresvetas.pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "personaId",
    "proyectoId",
    "direccionEnvioId",
    "direccionFacturacionId",
    "estado",
    "baseImponible",
    "descuento",
    "importeIva",
    "importeFinal",
    "fechaPedido",
    "fechaActualizacion",
    "lineas",
    "historialEstados"
})
public record PedidoResponseDTO(
        Integer id,
        Integer personaId,
        Integer proyectoId,
        Integer direccionEnvioId,
        Integer direccionFacturacionId,
        String estado,
        BigDecimal baseImponible,
        BigDecimal descuento,
        BigDecimal importeIva,
        BigDecimal importeFinal,
        LocalDateTime fechaPedido,
        LocalDateTime fechaActualizacion,
        List<LineaPedidoResponseDTO> lineas,
        List<PedidoEstadoResponseDTO> historialEstados
) {

}