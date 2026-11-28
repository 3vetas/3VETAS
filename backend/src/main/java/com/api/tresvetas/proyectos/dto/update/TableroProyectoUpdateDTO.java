package com.api.tresvetas.proyectos.dto.update;

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
public class TableroProyectoUpdateDTO {

    @NotNull
    private Integer id;

    @NotNull
    private Integer tipoTableroId;

    @NotNull
    private Integer acabadoId;

    @NotNull
    private Integer grosorId;

    @Valid
    private List<PiezaUpdateDTO> piezas;
}
