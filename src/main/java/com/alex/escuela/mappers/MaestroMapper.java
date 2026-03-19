package com.alex.escuela.mappers;

import com.alex.escuela.dto.datos.DatosCurso;
import com.alex.escuela.dto.datos.DatosMaestro;
import com.alex.escuela.dto.maestros.MaestroRequest;
import com.alex.escuela.dto.maestros.MaestroResponse;
import com.alex.escuela.entities.Curso;
import com.alex.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro>{

    private final CursoMapper cursoMapper;

    @Override
    public Maestro requestToEntity(MaestroRequest request) {
        if (request == null) return null;

        return Maestro.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoMaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .email(request.email())
                .telefono(request.telefono())
                .grupos(new ArrayList<>())
                .build();
    }

    @Override
    public MaestroResponse entityToResponse(Maestro entity) {
        if(entity == null) return null;

        List<DatosCurso> cursos = maestroDatosCurso(entity);

        return new MaestroResponse(
                entity.getId(),
                String.join( " ",
                        entity.getNombre(),
                        entity.getApellidoPaterno(),
                        entity.getApellidoMaterno()),
                entity.getEmail(),
                entity.getTelefono(),
                cursos);
    }

    private List<DatosCurso> maestroDatosCurso(Maestro maestro){
        if (maestro == null) return null;

        return maestro.getGrupos().stream()
                .map(grupo ->
                        cursoMapper.cursoToDatosCurso(grupo.getCurso())).toList();
    }

    public DatosMaestro maestroToDatosMaestro(Maestro maestro){
        if (maestro == null) return null;

        return new DatosMaestro(
                maestro.getId(),
                maestro.getNombre(),
                maestro.getEmail(),
                maestro.getTelefono());
    }
}

