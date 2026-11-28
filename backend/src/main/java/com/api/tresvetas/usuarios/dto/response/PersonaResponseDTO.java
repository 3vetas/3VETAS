package com.api.tresvetas.usuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
        "id",
        "nombre",
        "apellidos",
        "telefono",
        "dni"
})
public record PersonaResponseDTO(
        Integer id,
        String nombre,
        String apellidos,
        String telefono,
        String dni
) {
}
