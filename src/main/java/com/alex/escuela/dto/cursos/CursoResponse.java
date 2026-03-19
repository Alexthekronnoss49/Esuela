package com.alex.escuela.dto.cursos;

public record CursoResponse(
        Long Id,
        String nombre,
        String descripcion,
        Integer creditos
) {
}
