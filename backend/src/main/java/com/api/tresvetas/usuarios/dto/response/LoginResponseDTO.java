package com.api.tresvetas.usuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
        "requiereVerificacion"
})
public record LoginResponseDTO(
        Boolean requiereVerificacion
) {
}