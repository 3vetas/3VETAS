package com.api.tresvetas.pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "estado",
    "comentario",
    "usuarioId",
    "fechaEstado"
})
public record PedidoEstadoResponseDTO(
        Integer id,
        String estado,
        String comentario,
        Integer usuarioId,
        LocalDateTime fechaEstado
) {

}