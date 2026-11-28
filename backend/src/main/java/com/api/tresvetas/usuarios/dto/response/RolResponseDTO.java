package com.api.tresvetas.usuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
        "id",
        "nombre"
})
public record RolResponseDTO(
        Integer id,
        String nombre
) {
}