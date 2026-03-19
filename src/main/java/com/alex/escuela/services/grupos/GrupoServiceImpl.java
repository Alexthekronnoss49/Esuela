package com.alex.escuela.services.grupos;

import com.alex.escuela.dto.grupos.GrupoRequest;
import com.alex.escuela.dto.grupos.GrupoResponse;
import com.alex.escuela.entities.Aula;
import com.alex.escuela.entities.Curso;
import com.alex.escuela.entities.Grupo;
import com.alex.escuela.entities.Maestro;
import com.alex.escuela.exceptions.EntidadRelacionadaException;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.GrupoMapper;
import com.alex.escuela.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class GrupoServiceImpl implements GrupoService{

    private final GrupoRepository grupoRepository;

    private final CursoRepository cursoRepository;

    private final MaestroRepository maestroRepository;

    private final AulaRepository aulaRepository;

    private final InscripcionRepository inscripcionRepository;

    private final HorarioRepository horarioRepository;

    private final GrupoMapper grupoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entityToResponse).toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entityToResponse(obtenerGrupoOException(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {

        Curso curso = obtenerCursoOException(request.idCurso());

        Maestro maestro = obtenerMaestroOException(request.idMaestro());

        Aula aula = obtenerAulaOException(request.idAula());

        comprobarNoDuplicidad(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo());

        Grupo grupo = grupoRepository.save(grupoMapper.requestToEntity(request, curso, maestro, aula));

        return grupoMapper.entityToResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Grupo grupo = obtenerGrupoOException(id);

        comprobarNoDuplicidadActualizar(request.idCurso(),
                request.idMaestro(), request.idAula(), request.periodo(), id);

        Curso curso = obtenerCursoOException(request.idCurso());

        Maestro maestro = obtenerMaestroOException(request.idMaestro());

        Aula aula = obtenerAulaOException(request.idAula());

        grupo.setCurso(curso);
        grupo.setMaestro(maestro);
        grupo.setAula(aula);
        grupo.setPeriodo(request.periodo());

        return grupoMapper.entityToResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        comprobarAntesDeEliminar(id);

        grupoRepository.delete(obtenerGrupoOException(id));
    }

    private Grupo obtenerGrupoOException(Long id){
        return grupoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Grupo no encontrado con el id: "+id));
    }

    private void comprobarNoDuplicidad(Long idCurso, Long idMaestro, Long idAula, String periodo){
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(idCurso, idMaestro, idAula, periodo)){
            throw new EntidadRelacionadaException("Esta combinación ya existe");
        }
    }

    private void comprobarNoDuplicidadActualizar(Long idCurso, Long idMaestro, Long idAula, String periodo, Long id){
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(idCurso, idMaestro, idAula, periodo, id)){
            throw new EntidadRelacionadaException("Esta combinación ya existe");
        }
    }

    private Curso obtenerCursoOException(Long id){
        return cursoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Curso no encontrado con el id: "+id));
    }

    private Maestro obtenerMaestroOException(Long id){
        return maestroRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Maestro no encontrado con el id: "+id));
    }

    private Aula obtenerAulaOException(Long id){
        return aulaRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Aula no encontrada con el id: "+id));
    }

    private void comprobarAntesDeEliminar(Long id){
        if (inscripcionRepository.existsByGrupoId(id)){
            throw new EntidadRelacionadaException("Este grupo tiene inscripciones relacionadas");
        }
        if (horarioRepository.existsByGrupoId(id)){
            throw new EntidadRelacionadaException("Este grupo tiene horarios relacionados");
        }
    }

}
