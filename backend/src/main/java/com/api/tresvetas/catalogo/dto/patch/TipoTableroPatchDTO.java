package com.api.tresvetas.catalogo.dto.patch;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TipoTableroPatchDTO {

    @Pattern(
            regexp = ".*\\S.*",
            message = "El nombre no puede estar vacío"
    )
    @Size(max = 120)
    private String nombre;

    private String descripcion;

    @Size(max = 500)
    private String imagen;
}