package com.api.tresvetas.proyectos.dto.patch;

import jakarta.validation.constraints.Positive;
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
public class TableroProyectoPatchDTO {

    @Positive
    private Integer tipoTableroId;

    @Positive
    private Integer acabadoId;

    @Positive
    private Integer grosorId;
}
