package com.alex.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;

public record IncripcionesRequest(
        @NotNull(message = "El id del alumno no sebe ser nulo")
        Long idAlumno,

        @NotNull(message = "El id del grupo no sebe ser nulo")
        Long idGrupo
) {
}
