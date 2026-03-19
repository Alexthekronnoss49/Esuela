package com.alex.escuela.mappers;

import com.alex.escuela.dto.cursos.CursoRequest;
import com.alex.escuela.dto.cursos.CursoResponse;
import com.alex.escuela.dto.datos.DatosCurso;
import com.alex.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso> {
    @Override
    public Curso requestToEntity(CursoRequest request) {
        if (request == null) return null;

        return Curso.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .creditos(request.creditos())
                .build();
    }

    @Override
    public CursoResponse entityToResponse(Curso entity) {
        if (entity == null) return null;

        String descripcion = entity.getDescripcion() == null ?
                "Sin descripción" : entity.getDescripcion();

        return new CursoResponse(
                entity.getId(),
                entity.getNombre(),
                descripcion,
                entity.getCreditos());
    }

    public DatosCurso cursoToDatosCurso(Curso curso){
        if (curso == null) return null;

        String descripcion = curso.getDescripcion() == null ?
                "Sin descripción" : curso.getDescripcion();

        return new DatosCurso(
                 curso.getNombre(),
                descripcion,
                curso.getCreditos());
    }
}
