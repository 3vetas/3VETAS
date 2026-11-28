package com.api.tresvetas.proyectos.mapper;

import com.api.tresvetas.proyectos.dto.patch.TableroProyectoPatchDTO;
import com.api.tresvetas.proyectos.dto.request.TableroProyectoRequestDTO;
import com.api.tresvetas.proyectos.dto.response.TableroProyectoResponseDTO;
import com.api.tresvetas.proyectos.dto.update.TableroProyectoUpdateDTO;
import com.api.tresvetas.proyectos.entity.TableroProyecto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring", uses = PiezaMapper.class)
public interface TableroProyectoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "tipoTablero", ignore = true)
    @Mapping(target = "acabado", ignore = true)
    @Mapping(target = "grosor", ignore = true)
    @Mapping(target = "piezas", ignore = true)
    TableroProyecto toEntity(TableroProyectoRequestDTO dto);

    @Mapping(target = "tipoTableroId", source = "tipoTablero.id")
    @Mapping(target = "acabadoId", source = "acabado.id")
    @Mapping(target = "grosorId", source = "grosor.id")
    TableroProyectoResponseDTO toResponseDTO(TableroProyecto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "tipoTablero", ignore = true)
    @Mapping(target = "acabado", ignore = true)
    @Mapping(target = "grosor", ignore = true)
    @Mapping(target = "piezas", ignore = true)
    void updateEntity(
            TableroProyectoUpdateDTO dto,
            @MappingTarget TableroProyecto entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "tipoTablero", ignore = true)
    @Mapping(target = "acabado", ignore = true)
    @Mapping(target = "grosor", ignore = true)
    @Mapping(target = "piezas", ignore = true)
    void patchEntity(
            TableroProyectoPatchDTO dto,
            @MappingTarget TableroProyecto entity
    );
}
