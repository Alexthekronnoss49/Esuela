package com.alex.escuela.services.aulas;

import com.alex.escuela.dto.alumnos.AlumnoResponse;
import com.alex.escuela.dto.aulas.AulasRequest;
import com.alex.escuela.dto.aulas.AulasResponse;
import com.alex.escuela.entities.Aula;
import com.alex.escuela.exceptions.EntidadRelacionadaException;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.AulasMapper;
import com.alex.escuela.repositories.AulaRepository;
import com.alex.escuela.repositories.GrupoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AulaServiceImpl implements AulaService{
    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulasMapper aulasMapper;

    private Aula obtenerAulaOException(Long id){
        return aulaRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Aula no encontrada no encontrado con el di: "+id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulasResponse> listar(){
        return aulaRepository.findAll()
                .stream()
                .map(aulasMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulasResponse obtenerPorId(Long id) {
        return aulasMapper.entityToResponse(obtenerAulaOException(id));
    }

    @Override
    public AulasResponse registrar(AulasRequest request){
        compronarNombreUnico(request.nombre());

        Aula aula = aulaRepository.save(aulasMapper.requestToEntity(request));

        return aulasMapper.entityToResponse(aula);
    }

    @Override
    public AulasResponse actualizar(AulasRequest request, Long id){
        compronarNombreUnicoActualizar(request.nombre(), id);

        Aula aula = obtenerAulaOException(id);

        aula.setNombre(request.nombre());
        aula.setCapacidad(request.capacidad());

        return aulasMapper.entityToResponse(aula);
    }

    @Override
    public void eliminar(Long id){

        Aula aula = obtenerAulaOException(id);

        if (grupoRepository.existsByAulaId(id)){
            throw new EntidadRelacionadaException("Existe un grupo relacionado a esta aula");
        }

        aulaRepository.delete(aula);
    }

    public void compronarNombreUnico(String nombre){
        if (aulaRepository.existsByNombreIgnoreCase(nombre))
            throw new IllegalArgumentException("Ya existe un aula con el nombre "+nombre);
    }

    public void compronarNombreUnicoActualizar(String nombre, Long id){
        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id))
            throw new IllegalArgumentException("Ya existe un aula con el nombre "+nombre);
    }

}
