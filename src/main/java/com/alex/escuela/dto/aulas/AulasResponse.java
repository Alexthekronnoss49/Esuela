package com.alex.escuela.dto.aulas;

public record AulasResponse(
        Long idAula,
        String nombre,
        Integer capacidad
) {
}
