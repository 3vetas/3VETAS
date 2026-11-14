package com.api.tresvetas.proyectos.dto.patch;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class ProyectoPatchDTO {

    @Positive
    private Integer usuarioId;

    @Pattern(regexp = ".*\\S.*", message = "El nombre no puede estar vacío ni contener únicamente espacios")
    @Size(max = 150)
    private String nombre;

    private String descripcion;

    @Pattern(regexp = ".*\\S.*", message = "El estado no puede estar vacío ni contener únicamente espacios")
    @Size(max = 40)
    private String estado;
}