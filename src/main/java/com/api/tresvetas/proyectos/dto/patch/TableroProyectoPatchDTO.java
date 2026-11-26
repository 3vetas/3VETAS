package com.api.tresvetas.proyectos.dto.patch;

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

    private Integer tipoTableroId;

    private Integer acabadoId;

    private Integer grosorId;
}
