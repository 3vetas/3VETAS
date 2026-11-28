package com.api.tresvetas.proyectos.mapper;

import com.api.tresvetas.proyectos.dto.patch.PiezaPatchDTO;
import com.api.tresvetas.proyectos.dto.request.PiezaRequestDTO;
import com.api.tresvetas.proyectos.dto.response.PiezaResponseDTO;
import com.api.tresvetas.proyectos.dto.update.PiezaUpdateDTO;
import com.api.tresvetas.proyectos.entity.Pieza;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface PiezaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tableroProyecto", ignore = true)
    Pieza toEntity(PiezaRequestDTO dto);

    PiezaResponseDTO toResponseDTO(Pieza entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tableroProyecto", ignore = true)
    void updateEntity(
            PiezaUpdateDTO dto,
            @MappingTarget Pieza entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tableroProyecto", ignore = true)
    void patchEntity(
            PiezaPatchDTO dto,
            @MappingTarget Pieza entity
    );
}
