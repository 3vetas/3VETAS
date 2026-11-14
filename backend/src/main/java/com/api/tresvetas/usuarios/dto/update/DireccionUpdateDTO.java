package com.api.tresvetas.usuarios.dto.update;

import com.api.tresvetas.usuarios.entity.TipoDireccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DireccionUpdateDTO {

    @NotNull(message = "El tipo de dirección es obligatorio")
    private TipoDireccion tipo;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String direccion;

    @NotBlank(message = "El código postal es obligatorio")
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "El código postal debe contener exactamente 5 dígitos"
    )
    private String cp;

    @NotBlank(message = "El municipio es obligatorio")
    @Size(max = 120, message = "El municipio no puede superar los 120 caracteres")
    private String municipio;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 120, message = "La provincia no puede superar los 120 caracteres")
    private String provincia;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 120, message = "El país no puede superar los 120 caracteres")
    private String pais;
}
