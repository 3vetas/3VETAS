package com.api.tresvetas.catalogo.mapper;

import com.api.tresvetas.catalogo.dto.patch.TipoTableroPatchDTO;
import com.api.tresvetas.catalogo.dto.request.TipoTableroRequestDTO;
import com.api.tresvetas.catalogo.dto.response.TipoTableroResponseDTO;
import com.api.tresvetas.catalogo.dto.update.TipoTableroUpdateDTO;
import com.api.tresvetas.catalogo.entity.TipoTablero;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface TipoTableroMapper {

    @Mapping(target = "id", ignore = true)
    TipoTablero toEntity(TipoTableroRequestDTO dto);

    TipoTableroResponseDTO toResponseDTO(TipoTablero entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(
            TipoTableroUpdateDTO dto,
            @MappingTarget TipoTablero entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    void patchEntity(
            TipoTableroPatchDTO dto,
            @MappingTarget TipoTablero entity
    );
}
