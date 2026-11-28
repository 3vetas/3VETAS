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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcabadoPatchDTO {

    @Pattern(
        regexp = ".*\\S.*",
        message = "El nombre no puede estar vacío"
    )
    @Size(max = 120)
    private String nombre;

    @Size(max = 120)
    private String familia;

    @Size(max = 100)
    private String colorBase;

    @Size(max = 120)
    private String textura;

    @Size(max = 150)
    private String fabricante;

    @Size(max = 100)
    private String codFabricante;
}