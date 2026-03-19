package com.alex.escuela.dto.datos;

import java.math.BigDecimal;
import java.util.List;

public record DatosAlumno(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso
) {
}
