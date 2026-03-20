package com.alex.escuela.dto.inscripciones;

import com.alex.escuela.dto.datos.DatosAlumno;
import com.alex.escuela.dto.datos.DatosGrupo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InscripcionesResponse(
        Long idInscripcion,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        @JsonFormat(pattern = "dd/MM/yyyy")
        String fechaInsc
) {
}
