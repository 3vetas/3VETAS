package com.api.tresvetas.catalogo.mapper;

import com.api.tresvetas.catalogo.dto.patch.TableroPatchDTO;
import com.api.tresvetas.catalogo.dto.request.TableroRequestDTO;
import com.api.tresvetas.catalogo.dto.response.TableroResponseDTO;
import com.api.tresvetas.catalogo.dto.update.TableroUpdateDTO;
import com.api.tresvetas.catalogo.entity.Tablero;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface TableroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoTablero", ignore = true)
    @Mapping(target = "acabado", ignore = true)
    @Mapping(target = "formato", ignore = true)
    @Mapping(target = "grosor", ignore = true)
    @Mapping(target = "stockActual", ignore = true)
    Tablero toEntity(TableroRequestDTO dto);

    @Mapping(target = "tipoTableroId", source = "tipoTablero.id")
    @Mapping(target = "acabadoId", source = "acabado.id")
    @Mapping(target = "formatoId", source = "formato.id")
    @Mapping(target = "grosorId", source = "grosor.id")
    TableroResponseDTO toResponseDTO(Tablero entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoTablero", ignore = true)
    @Mapping(target = "acabado", ignore = true)
    @Mapping(target = "formato", ignore = true)
    @Mapping(target = "grosor", ignore = true)
    @Mapping(target = "stockActual", ignore = true)
    void updateEntity(
            TableroUpdateDTO dto,
            @MappingTarget Tablero entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoTablero", ignore = true)
    @Mapping(target = "acabado", ignore = true)
    @Mapping(target = "formato", ignore = true)
    @Mapping(target = "grosor", ignore = true)
    @Mapping(target = "stockActual", ignore = true)
    void patchEntity(
            TableroPatchDTO dto,
            @MappingTarget Tablero entity
    );
}
