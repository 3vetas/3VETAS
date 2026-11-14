package com.api.tresvetas.proyectos.dto.update;

import com.api.tresvetas.proyectos.dto.update.TableroProyectoUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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
public class ProyectoUpdateDTO {

    @NotNull
    private Integer usuarioId;

    @NotBlank
    @Size(max = 150)
    private String nombre;

    private String descripcion;

    @NotBlank
    @Size(max = 40)
    private String estado;

    @Valid
    private List<TableroProyectoUpdateDTO> tableros;
}
