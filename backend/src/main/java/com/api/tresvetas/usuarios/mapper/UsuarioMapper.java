package com.api.tresvetas.usuarios.mapper;

import com.api.tresvetas.usuarios.dto.response.UsuarioResponseDTO;
import com.api.tresvetas.usuarios.entity.Persona;
import com.api.tresvetas.usuarios.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(
        componentModel = "spring",
        uses = {
            RolMapper.class,
            PersonaMapper.class
        }
)
public interface UsuarioMapper {

    @Mapping(target = "id", source = "usuario.id")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "rol", source = "usuario.rol")
    @Mapping(target = "activo", source = "usuario.activo")
    @Mapping(target = "persona", source = "persona")
    UsuarioResponseDTO toResponseDTO(
            Usuario usuario,
            Persona persona
    );
}