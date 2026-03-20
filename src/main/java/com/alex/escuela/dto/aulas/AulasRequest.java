package com.alex.escuela.dto.aulas;

import jakarta.validation.constraints.*;

public record AulasRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
        String nombre,

        @NotNull(message = "La capacidad del aula es requerida")
        @Max(value = 40, message = "La capacidad máxima es de 40")
        @Min(value = 1, message = "La capacidad mínima del aula debe ser mayor a 0")
        Integer capacidad
) {
}
