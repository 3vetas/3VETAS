package com.api.tresvetas.usuarios.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class LoginVerificacionRequestDTO {

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 254, message = "El correo electrónico no puede superar los 254 caracteres")
    private String email;

    @NotBlank(message = "El código de verificación es obligatorio")
    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "El código de verificación debe contener exactamente 6 dígitos"
    )
    private String codigo;
}