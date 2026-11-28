package com.api.tresvetas.usuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
        "id",
        "email",
        "rol",
        "activo",
        "persona"
})
public record UsuarioResponseDTO(
        Integer id,
        String email,
        RolResponseDTO rol,
        Boolean activo,
        PersonaResponseDTO persona
) {
}