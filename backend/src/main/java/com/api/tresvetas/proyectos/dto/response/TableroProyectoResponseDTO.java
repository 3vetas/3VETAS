package com.api.tresvetas.proyectos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;


/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "tipoTableroId",
    "acabadoId",
    "grosorId",
    "piezas"
})
public record TableroProyectoResponseDTO(
        Integer id,
        Integer tipoTableroId,
        Integer acabadoId,
        Integer grosorId,
        List<PiezaResponseDTO> piezas
        ) {
}
