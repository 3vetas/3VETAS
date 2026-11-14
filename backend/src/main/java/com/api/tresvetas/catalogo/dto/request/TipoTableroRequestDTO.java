package com.api.tresvetas.catalogo.dto.request;

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
@AllArgsConstructor
@NoArgsConstructor
public class TipoTableroRequestDTO {

    @NotBlank
    @Size(max = 120)
    private String nombre;

    private String descripcion;

    @Size(max = 500)
    private String imagen;
}