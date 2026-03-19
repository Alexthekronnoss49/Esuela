package com.alex.escuela.dto.grupos;

import com.alex.escuela.dto.aulas.AulasResponse;
import com.alex.escuela.dto.datos.DatosCurso;
import com.alex.escuela.dto.datos.DatosMaestro;
import com.alex.escuela.entities.Aula;
import com.alex.escuela.entities.Maestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        AulasResponse aula,
        List<String> horario,
        String periodo
) {
}
