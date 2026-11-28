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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TipoTableroUpdateDTO {

    @NotBlank
    @Size(max = 120)
    private String nombre;

    private String descripcion;

    @Size(max = 500)
    private String imagen;
}