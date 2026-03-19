package com.alex.escuela.dto.inscripciones;

import com.alex.escuela.dto.datos.DatosAlumno;
import com.alex.escuela.dto.datos.DatosGrupo;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InscripcionesResponse(
        Long idInscripcion,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInsc
) {
}
