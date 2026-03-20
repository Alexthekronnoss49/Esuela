package com.alex.escuela.services.inscripciones;

import com.alex.escuela.dto.inscripciones.InscripcionesRequest;
import com.alex.escuela.dto.inscripciones.InscripcionesResponse;
import com.alex.escuela.entities.Alumno;
import com.alex.escuela.entities.Grupo;
import com.alex.escuela.entities.Inscripciones;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.InscripcionesMapper;
import com.alex.escuela.repositories.AlumnoRepository;
import com.alex.escuela.repositories.GrupoRepository;
import com.alex.escuela.repositories.InscripcionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class InscripcionesServiceImpl implements InscripcionService{

    private final InscripcionRepository inscripcionRepository;

    private final GrupoRepository grupoRepository;

    private final AlumnoRepository alumnoRepository;

    private final InscripcionesMapper inscripcionesMapper;

    @Override
    public List<InscripcionesResponse> listar() {
        return inscripcionRepository.findAll().stream()
                .map(inscripcionesMapper::entityToResponse)
                .toList();
    }

    @Override
    public InscripcionesResponse obtenerPorId(Long id) {
        Inscripciones inscripcion = obtenerInscripcionOException(id);

        return inscripcionesMapper.entityToResponse(inscripcion);
    }

    @Override
    public InscripcionesResponse registrar(InscripcionesRequest request) {

        Alumno alumno = obtenerAlumnoOException(request.idAlumno());
        Grupo grupo = obtenerGrupoOException(request.idGrupo());

        comprobarAlumnoEnGrupo(request.idAlumno(), request.idGrupo());
        Inscripciones inscripciones =
                inscripcionRepository.save(inscripcionesMapper.requestToEntity(request, alumno, grupo));

        return inscripcionesMapper.entityToResponse(inscripciones);
    }

    @Override
    public InscripcionesResponse actualizar(InscripcionesRequest request, Long id) {

        Inscripciones inscripciones = obtenerInscripcionOException(id);

        Alumno alumno = obtenerAlumnoOException(request.idAlumno());
        Grupo grupo = obtenerGrupoOException(request.idGrupo());

        comprobarAlumnoEnGrupo(request.idAlumno(), request.idGrupo());

        inscripciones.setAlumno(alumno);
        inscripciones.setGrupo(grupo);

        return inscripcionesMapper.entityToResponse(inscripciones);
    }

    @Override
    public void eliminar(Long id) {
        inscripcionRepository.delete(obtenerInscripcionOException(id));
    }

    private Inscripciones obtenerInscripcionOException(Long id){
        return inscripcionRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Inscripción no encontrada con el id: "+id));
    }

    private Grupo obtenerGrupoOException(Long id){
        return grupoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Grupo no encontrado con el id: "+id));
    }

    private Alumno obtenerAlumnoOException(Long id){
        return alumnoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Grupo no encontrado con el id: "+id));
    }

    private void comprobarAlumnoEnGrupo(Long idAlumno, Long idGrupo){
        if (inscripcionRepository.existsByAlumnoIdAndGrupoId(idAlumno, idGrupo)){
            throw new IllegalArgumentException("Este alumno ya está escrito a este grupo.");
        }
    }
}
