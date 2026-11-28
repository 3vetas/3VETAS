package com.api.tresvetas.usuarios.mapper;

import com.api.tresvetas.usuarios.dto.patch.DireccionPatchDTO;
import com.api.tresvetas.usuarios.dto.request.DireccionRequestDTO;
import com.api.tresvetas.usuarios.dto.response.DireccionResponseDTO;
import com.api.tresvetas.usuarios.dto.update.DireccionUpdateDTO;
import com.api.tresvetas.usuarios.entity.Direccion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface DireccionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    Direccion toEntity(DireccionRequestDTO dto);

    DireccionResponseDTO toResponseDTO(Direccion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    void updateEntity(
            DireccionUpdateDTO dto,
            @MappingTarget Direccion entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    void patchEntity(
            DireccionPatchDTO dto,
            @MappingTarget Direccion entity
    );
}