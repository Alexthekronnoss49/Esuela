package com.alex.escuela.dto.maestros;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MaestroRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellid materno es requerido")
        @Size(min = 3, max = 50, message = "El apelido materno debe tener entre 3 y 50 caracteres")
        String apellidoMaterno,

        @NotBlank(message = "El email es requerido")
        @Size(max = 100, message = "El email debe tener máximo 100 caracteres")
        @Email(message = "El email debe tener un formato válido")
        String email,

        @NotBlank(message = "El teléfono es requerido")
       @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener 10 digitos exactamente")
        String telefono
) {
}
