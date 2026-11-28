package com.api.tresvetas.proyectos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 * @author Jabier Zurro Aduriz
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableroProyectoRequestDTO {

    @NotNull
    private Integer tipoTableroId;

    @NotNull
    private Integer acabadoId;

    @NotNull
    private Integer grosorId;

    @Valid
    private List<PiezaRequestDTO> piezas;
}