package com.alex.escuela.services.calificaciones;

import com.alex.escuela.dto.calificaciones.CalificacionesRequest;
import com.alex.escuela.dto.calificaciones.CalificacionesResponse;
import com.alex.escuela.entities.Calificacion;
import com.alex.escuela.entities.Curso;
import com.alex.escuela.entities.Inscripciones;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.CalificacionesMapper;
import com.alex.escuela.repositories.CalificaionRepository;
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
public class CalificacionesServiceImpl implements CalificacionesService{

    private final CalificaionRepository calificaionRepository;

    private final InscripcionRepository inscripcionRepository;

    private final CalificacionesMapper calificacionesMapper;

    private Calificacion obtenerCalificacionOException(Long id){
        return calificaionRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Calificacion no encontrada con el id: "+id));
    }

    private Inscripciones obtenerInscripcionesOException(Long id){
        return inscripcionRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Calificacion no encontrada con el id: "+id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionesResponse> listar() {
        return calificaionRepository.findAll().stream()
                .map(calificacionesMapper::entityToResponse)
                .toList();
    }

    @Override
    public CalificacionesResponse obtenerPorId(Long id) {

        return calificacionesMapper.entityToResponse(obtenerCalificacionOException(id));

    }

    @Override
    public CalificacionesResponse registrar(CalificacionesRequest request) {

        comprobarCalificacionEnInscripcion(request.idInscripcion());

        Inscripciones inscripciones = obtenerInscripcionesOException(request.idInscripcion());

        Calificacion calificacion =
                calificaionRepository.save(calificacionesMapper.requestToEntity(request, inscripciones));

        return calificacionesMapper.entityToResponse(calificacion);
    }

    @Override
    public CalificacionesResponse actualizar(CalificacionesRequest request, Long id) {

        comprobarCalificacionEnInscripcionActualizar(request.idInscripcion(), id);

        Calificacion calificacion = obtenerCalificacionOException(id);

        Inscripciones inscripciones = obtenerInscripcionesOException(request.idInscripcion());

        calificacion.setInscripcion(inscripciones);
        calificacion.setCalificacion(request.calificacion());

        return calificacionesMapper.entityToResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        calificaionRepository.delete(obtenerCalificacionOException(id));
    }

    private void comprobarCalificacionEnInscripcion(Long idInscripcion){
        if (calificaionRepository.existsByInscripcionId(idInscripcion)){
            throw new IllegalArgumentException("Esta inscripción ya tiene una calificación");
        }
    }

    private void comprobarCalificacionEnInscripcionActualizar(Long idInscripcion, Long idCalificacion){
        if (calificaionRepository.existsByInscripcionIdAndIdNot(idInscripcion, idCalificacion)){
            throw new IllegalArgumentException("Esta inscripción ya tiene una calificación");
        }
    }
}
