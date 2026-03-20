package com.alex.escuela.dto.datos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.List;

public record DatosAlumno(
        String nombre,
        String email,
        String matricula,
        @JsonFormat(pattern = "dd/MM/yyyy")
        String fechaIngreso
) {
}
