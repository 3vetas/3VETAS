package com.api.tresvetas.usuarios.mapper;

import com.api.tresvetas.usuarios.dto.response.RolResponseDTO;
import com.api.tresvetas.usuarios.entity.Rol;
import org.mapstruct.Mapper;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface RolMapper {

    RolResponseDTO toResponseDTO(Rol entity);
}