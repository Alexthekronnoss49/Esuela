package com.alex.escuela.dto.grupos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record GrupoRequest(

        @NotNull(message = "El id del curso no sebe ser nulo")
        Long idCurso,

        @NotNull(message = "El id del maestro no sebe ser nulo")
        Long idMaestro,

        @NotNull(message = "El id del aula no sebe ser nulo")
        Long idAula,

        @NotBlank(message = "El período es requerido")
        String periodo
) {
}
