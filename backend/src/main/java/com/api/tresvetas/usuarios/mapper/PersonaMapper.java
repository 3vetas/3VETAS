package com.api.tresvetas.usuarios.mapper;

import com.api.tresvetas.usuarios.dto.patch.PersonaPatchDTO;
import com.api.tresvetas.usuarios.dto.request.PersonaRequestDTO;
import com.api.tresvetas.usuarios.dto.response.PersonaResponseDTO;
import com.api.tresvetas.usuarios.dto.update.PersonaUpdateDTO;
import com.api.tresvetas.usuarios.entity.Persona;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface PersonaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Persona toEntity(PersonaRequestDTO dto);

    PersonaResponseDTO toResponseDTO(Persona entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(
            PersonaUpdateDTO dto,
            @MappingTarget Persona entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void patchEntity(
            PersonaPatchDTO dto,
            @MappingTarget Persona entity
    );
}