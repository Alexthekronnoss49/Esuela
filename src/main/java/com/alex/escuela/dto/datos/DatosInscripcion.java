package com.alex.escuela.dto.datos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record DatosInscripcion(
        DatosAlumno alumno,
        DatosGrupo grupo,
        @JsonFormat(pattern = "dd/MM/yyyy")
        String fechaInsc
) {
}
