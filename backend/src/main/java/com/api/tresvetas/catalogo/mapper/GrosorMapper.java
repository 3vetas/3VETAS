package com.api.tresvetas.catalogo.mapper;

import com.api.tresvetas.catalogo.dto.patch.GrosorPatchDTO;
import com.api.tresvetas.catalogo.dto.request.GrosorRequestDTO;
import com.api.tresvetas.catalogo.dto.response.GrosorResponseDTO;
import com.api.tresvetas.catalogo.dto.update.GrosorUpdateDTO;
import com.api.tresvetas.catalogo.entity.Grosor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface GrosorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valorMm", source = "espesorMm")
    Grosor toEntity(GrosorRequestDTO dto);

    @Mapping(target = "espesorMm", source = "valorMm")
    GrosorResponseDTO toResponseDTO(Grosor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valorMm", source = "espesorMm")
    void updateEntity(
            GrosorUpdateDTO dto,
            @MappingTarget Grosor entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valorMm", source = "espesorMm")
    void patchEntity(
            GrosorPatchDTO dto,
            @MappingTarget Grosor entity
    );
}
