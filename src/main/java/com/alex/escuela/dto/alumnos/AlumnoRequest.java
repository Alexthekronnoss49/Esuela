package com.alex.escuela.dto.alumnos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 3, max = 50, message = "El apelido materno debe tener entre 3 y 50 caracteres")
        String apellidoMaterno

) {
}
