package com.alex.escuela.dto.datos;

import com.alex.escuela.dto.aulas.AulasResponse;

import java.util.List;

public record DatosGrupo(
        String curso,
        String maestro,
        String aula,
        String periodo
) {
}
