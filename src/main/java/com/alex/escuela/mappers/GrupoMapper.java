package com.alex.escuela.mappers;

import com.alex.escuela.dto.aulas.AulasResponse;
import com.alex.escuela.dto.datos.DatosCurso;
import com.alex.escuela.dto.datos.DatosGrupo;
import com.alex.escuela.dto.datos.DatosMaestro;
import com.alex.escuela.dto.grupos.GrupoRequest;
import com.alex.escuela.dto.grupos.GrupoResponse;
import com.alex.escuela.dto.horarios.HorarioResponse;
import com.alex.escuela.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo> {

    private final CursoMapper cursoMapper;
    private final MaestroMapper maestroMapper;
    private final AulasMapper aulasMapper;

    @Override
    public Grupo requestToEntity(GrupoRequest request) {
        if (request == null) return null;

        Grupo grupo = new Grupo();
        grupo.setPeriodo(request.periodo());
        return grupo;
    }

    public Grupo requestToEntity(GrupoRequest request, Curso curso, Maestro maestro, Aula aula) {
        if (request == null) return null;

        Grupo grupo = requestToEntity(request);
        grupo.setCurso(curso);
        grupo.setMaestro(maestro);
        grupo.setAula(aula);

        return grupo;
    }

    @Override
    public GrupoResponse entityToResponse(Grupo entity) {
        if (entity == null)return null;

       DatosCurso curso = cursoMapper.cursoToDatosCurso(entity.getCurso());
       DatosMaestro maestro = maestroMapper.maestroToDatosMaestro(entity.getMaestro());
       AulasResponse aula = aulasMapper.entityToResponse(entity.getAula());

        return new GrupoResponse(
                entity.getId(),
                curso,
                maestro,
                aula,
                entity.getHorarios().stream()
                        .map(horario -> entity.getHorarios() == null
                                ? "Sin horarios asignados"
                                :  horario.getDia().getDescripcion() + ": "
                                + horario.getHoraInicio() + "-"
                                + horario.getHoraFin())
                        .toList(),
                entity.getPeriodo());
    }

    public DatosGrupo gruposToDatosGrupo(Grupo grupo){
        if (grupo == null) return null;

        return new DatosGrupo(
                grupo.getCurso().getNombre(),
                String.join(" ",
                        grupo.getMaestro().getNombre(),
                        grupo.getMaestro().getApellidoPaterno(),
                        grupo.getMaestro().getApellidoMaterno()),
                grupo.getAula().getNombre(),
                grupo.getPeriodo());
    }

}