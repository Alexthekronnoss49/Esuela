package com.alex.escuela.services.cursos;

import com.alex.escuela.dto.cursos.CursoRequest;
import com.alex.escuela.dto.cursos.CursoResponse;
import com.alex.escuela.entities.Curso;
import com.alex.escuela.exceptions.EntidadRelacionadaException;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.CursoMapper;
import com.alex.escuela.repositories.CursoRepository;
import com.alex.escuela.repositories.GrupoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final GrupoRepository grupoRepository;
    private final CursoMapper cursoMapper;

    private Curso obtenerCursoOException(Long id){
        return cursoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Cruso no encontrado con el id: "+id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entityToResponse(obtenerCursoOException(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        comprobarNombre(request.nombre());

        Curso curso = cursoRepository.save(cursoMapper.requestToEntity(request));

        return cursoMapper.entityToResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        comprobarNombreActualizar(request.nombre(), id);

        Curso curso = obtenerCursoOException(id);

        curso.setNombre(request.nombre());
        curso.setDescripcion(request.descripcion());
        curso.setCreditos(request.creditos());

        return cursoMapper.entityToResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = obtenerCursoOException(id);

        if (grupoRepository.existsByCursoId(id)){
            throw new EntidadRelacionadaException("Existe un grupo relacionado a este curso");
        }

        cursoRepository.delete(curso);
    }

    private void comprobarNombre(String nombre){
        if (cursoRepository.existsByNombreIgnoreCase(nombre)){
            throw new IllegalArgumentException("Ya existe un curso con este nombre");
        }
    }

    private void comprobarNombreActualizar(String nombre, Long id){
        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)){
            throw new IllegalArgumentException("Ya existe un curso con este nombre");
        }
    }
}
