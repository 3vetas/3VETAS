package com.api.tresvetas.usuarios.dto.patch;

import com.api.tresvetas.usuarios.entity.TipoDireccion;
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
public class DireccionPatchDTO {

    private TipoDireccion tipo;

    @Pattern(
            regexp = ".*\\S.*",
            message = "La dirección no puede estar vacía ni contener únicamente espacios"
    )
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String direccion;

    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "El código postal debe contener exactamente 5 dígitos"
    )
    private String cp;

    @Pattern(
            regexp = ".*\\S.*",
            message = "El municipio no puede estar vacío ni contener únicamente espacios"
    )
    @Size(max = 120, message = "El municipio no puede superar los 120 caracteres")
    private String municipio;

    @Pattern(
            regexp = ".*\\S.*",
            message = "La provincia no puede estar vacía ni contener únicamente espacios"
    )
    @Size(max = 120, message = "La provincia no puede superar los 120 caracteres")
    private String provincia;

    @Pattern(
            regexp = ".*\\S.*",
            message = "El país no puede estar vacío ni contener únicamente espacios"
    )
    @Size(max = 120, message = "El país no puede superar los 120 caracteres")
    private String pais;
}