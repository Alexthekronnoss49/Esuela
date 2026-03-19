package com.alex.escuela.services.maestros;

import com.alex.escuela.dto.maestros.MaestroRequest;
import com.alex.escuela.dto.maestros.MaestroResponse;
import com.alex.escuela.entities.Maestro;
import com.alex.escuela.exceptions.EntidadRelacionadaException;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.MaestroMapper;
import com.alex.escuela.repositories.CursoRepository;
import com.alex.escuela.repositories.GrupoRepository;
import com.alex.escuela.repositories.MaestroRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MaestroServiceImpl implements MaestoService{

    private final MaestroRepository maestroRepository;

    private final MaestroMapper maestroMapper;

    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        log.info("Listando maestros...");
        return maestroRepository.findAll().stream()
                .map(maestroMapper::entityToResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entityToResponse(obtenerMaestroOException(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {
        log.info("registrando maestro");

        validarEmailUnico(request.email());
        validarTelefonoUnico(request.telefono());

        Maestro maestro = maestroRepository.save(maestroMapper.requestToEntity(request));
        log.info("Nuevo maestro registrado: {}", maestro.getNombre());

        return maestroMapper.entityToResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        Maestro maestro = obtenerMaestroOException(id);
        log.info("Actualizando maestro con id: {}", id);

        validarCambiosUnicos(request, id);

        maestro.setNombre(request.nombre());
        maestro.setApellidoPaterno(request.apellidoPaterno());
        maestro.setApellidoMaterno(request.apellidoMaterno());
        maestro.setEmail(request.email());
        maestro.setTelefono(request.telefono());

        log.info("Maetro con id {} atualizado", id);
        return maestroMapper.entityToResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {
        Maestro maestro = obtenerMaestroOException(id);

        log.info("Eliminando maestro con id: {}", id);

        if (grupoRepository.existsByMaestroId(id)){
            throw new EntidadRelacionadaException(
                    "No se uede eliminar el maestro ya que tiene grupos asignados");
        }

        maestroRepository.delete(maestro);

        log.info("Maestro con id {} eliminado", id);
    }

    private Maestro obtenerMaestroOException(Long id){
        log.info("Buscando maestro con id: "+id);
        return maestroRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Maestro no encontrado con el id: "+ id));
    }

    private void validarEmailUnico(String email){
        if (maestroRepository.existsByEmailIgnoreCase(email))
            throw new IllegalArgumentException("Ya existe un maestro con este email: "+email);
    }

    private void validarTelefonoUnico(String telefono){
        if (maestroRepository.existsByTelefono(telefono))
            throw new IllegalArgumentException("Ya existe un maestro con este telefono: "+telefono);
    }

    private void validarCambiosUnicos(MaestroRequest request, Long id){

        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), id))
            throw new IllegalArgumentException("Ya existe un maestro con este email: "+request.email());

        if (maestroRepository.existsByTelefonoAndIdNot(request.telefono(), id))
            throw new IllegalArgumentException("Ya existe un maestro con este telefono: "+request.telefono());
    }
}
