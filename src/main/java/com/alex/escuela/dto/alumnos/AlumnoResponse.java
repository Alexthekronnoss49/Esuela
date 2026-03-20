package com.alex.escuela.dto.alumnos;

import com.alex.escuela.dto.datos.DatosCalificaion;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.List;

public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        @JsonFormat(pattern = "dd/MM/yyyy")
        String fechaIngreso,
        List<DatosCalificaion> calificaciones,
        BigDecimal promedio
) {
}
