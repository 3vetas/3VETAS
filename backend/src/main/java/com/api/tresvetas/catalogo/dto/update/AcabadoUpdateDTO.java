package com.api.tresvetas.catalogo.dto.update;

import jakarta.validation.constraints.NotBlank;
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
public class AcabadoUpdateDTO {

    @Size(max = 120)
    private String familia;

    @NotBlank
    @Size(max = 120)
    private String nombre;

    @Size(max = 100)
    private String colorBase;

    @Size(max = 120)
    private String textura;

    @Size(max = 150)
    private String fabricante;

    @Size(max = 100)
    private String codFabricante;
}
