package com.alex.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosInscripcion(
        DatosAlumno alumno,
        DatosGrupo grupo,
        String fechaInsc
) {
}
