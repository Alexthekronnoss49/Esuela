package com.alex.escuela.dto.alumnos;

import com.alex.escuela.dto.datos.DatosCalificaion;

import java.math.BigDecimal;
import java.util.List;

public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<DatosCalificaion> calificaciones,
        BigDecimal promedio
) {
}
