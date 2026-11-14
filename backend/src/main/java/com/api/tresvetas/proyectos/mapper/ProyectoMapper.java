package com.api.tresvetas.proyectos.mapper;

import com.api.tresvetas.proyectos.dto.patch.ProyectoPatchDTO;
import com.api.tresvetas.proyectos.dto.request.ProyectoRequestDTO;
import com.api.tresvetas.proyectos.dto.response.ProyectoResponseDTO;
import com.api.tresvetas.proyectos.dto.update.ProyectoUpdateDTO;
import com.api.tresvetas.proyectos.entity.Proyecto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring", uses = TableroProyectoMapper.class)
public interface ProyectoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "tableros", ignore = true)
    Proyecto toEntity(ProyectoRequestDTO dto);

    @Mapping(target = "usuarioId", source = "usuario.id")
    ProyectoResponseDTO toResponseDTO(Proyecto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "tableros", ignore = true)
    void updateEntity(
            ProyectoUpdateDTO dto,
            @MappingTarget Proyecto entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "tableros", ignore = true)
    void patchEntity(
            ProyectoPatchDTO dto,
            @MappingTarget Proyecto entity
    );
}
