package com.api.tresvetas.usuarios.dto.patch;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jabier Zurro Aduriz
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaPatchDTO {

    @Pattern(
            regexp = ".*\\S.*",
            message = "El nombre no puede estar vacío ni contener únicamente espacios"
    )
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Pattern(
            regexp = ".*\\S.*",
            message = "Los apellidos no pueden estar vacíos ni contener únicamente espacios"
    )
    @Size(max = 150, message = "Los apellidos no pueden superar los 150 caracteres")
    private String apellidos;

    @Size(max = 25, message = "El teléfono no puede superar los 25 caracteres")
    private String telefono;

    @Size(max = 20, message = "El DNI no puede superar los 20 caracteres")
    private String dni;
}
