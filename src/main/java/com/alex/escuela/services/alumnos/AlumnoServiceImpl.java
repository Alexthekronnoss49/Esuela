package com.alex.escuela.services.alumnos;

import com.alex.escuela.dto.alumnos.AlumnoRequest;
import com.alex.escuela.dto.alumnos.AlumnoResponse;
import com.alex.escuela.entities.Alumno;
import com.alex.escuela.exceptions.EntidadRelacionadaException;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.AlumnoMapper;
import com.alex.escuela.repositories.AlumnoRepository;

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
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listando de todos los alumnos solicitado");
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entityToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entityToResponse(obtenerAlumnoOException(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registrando alumno...");

        String matricula = generarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        String email = generarEmail(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        Alumno alumno = alumnoRepository.save(alumnoMapper.requestToEntity(request, matricula, email));
        log.info("Nuevo alumno regstrado con matricula: {}", matricula);

        return alumnoMapper.entityToResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumnoOException(id);
        log.info("Actualizanfo alumno con id; {}", id);

        if (cambioDatosAlumno(request, alumno)){
            alumno.setNombre(request.nombre());
            alumno.setApellidoPaterno(request.apellidoPaterno());
            alumno.setApellidoMaterno(request.apellidoMaterno());

            regenerarDatosAcademicosAlumno(alumno, request);
            log.info("Datos académicos regenerados para el alumno con id: {}", id);
        }
        return alumnoMapper.entityToResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumnoOException(id);
        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el alumno porque tiene inscripciones asignadas");

        alumnoRepository.delete(alumno);
        log.info("Alumno con id {} eliminado ", id);
    }

    private Alumno obtenerAlumnoOException(Long id){
        log.info("Buscando alumno con id: {}", id);

        return alumnoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Alumno no encontrado con el di: "+id));
    }

    private String generarMatricula(String nombre, String apellidoPaterno, String apellidoMaterno){
       log.info("Generando matrícula...");
        return alumnoRepository.generarMatricula(nombre, apellidoPaterno, apellidoMaterno);
    }

    private String generarEmail(String nombre, String apellidoPaterno, String apellidoMaterno){
        log.info("Generando email...");
        return alumnoRepository.generarEmail(nombre, apellidoPaterno, apellidoMaterno);
    }

    private boolean cambioDatosAlumno(AlumnoRequest request, Alumno alumno){
        return !request.nombre().equalsIgnoreCase(alumno.getNombre()) ||
                !request.apellidoPaterno().equalsIgnoreCase(alumno.getApellidoPaterno()) ||
                !request.apellidoMaterno().equalsIgnoreCase(alumno.getApellidoMaterno());
    }

    private void regenerarDatosAcademicosAlumno(Alumno alumno, AlumnoRequest request){
        String matricula = generarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        String email = generarEmail(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        alumno.setMatricula(matricula);
        alumno.setEmail(email);
    }
}
