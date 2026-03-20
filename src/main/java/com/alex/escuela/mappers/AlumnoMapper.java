package com.alex.escuela.mappers;

import com.alex.escuela.dto.alumnos.AlumnoRequest;
import com.alex.escuela.dto.alumnos.AlumnoResponse;
import com.alex.escuela.dto.datos.DatosAlumno;
import com.alex.escuela.dto.datos.DatosCalificaion;
import com.alex.escuela.dto.datos.DatosMaestro;
import com.alex.escuela.entities.Alumno;
import com.alex.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Alumno requestToEntity(AlumnoRequest request) {
        if (request == null ) return null;

        LocalDate fecha = LocalDate.now();

        return Alumno.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .fechaIngreso(fecha)
                .build();
    }

    public Alumno requestToEntity(AlumnoRequest request, String matricula, String email) {
        if (request == null) return null;

        Alumno alumno = requestToEntity(request);
        alumno.setMatricula(matricula);
        alumno.setEmail(email);
        return alumno;
    }

    @Override
    public AlumnoResponse entityToResponse(Alumno entity) {
        if (entity==null) return null;

        List<DatosCalificaion> calificaciones = alumnoToDatosCalificaion(entity);

        return new AlumnoResponse(
                entity.getId(),
                String.join(" ",
                        entity.getNombre(),
                        entity.getApellidoPaterno(),
                        entity.getApellidoMaterno()
                ),
                entity.getEmail(),
                entity.getMatricula(),
                entity.getFechaIngreso().toString(),
                calificaciones,
                calificacionesToPromedio(calificaciones));
    }

    private List<DatosCalificaion> alumnoToDatosCalificaion(Alumno alumno){
        if (alumno == null || alumno.getInscripciones() == null)
            return List.of();

        return alumno.getInscripciones().stream()
                .map(inscripciones -> new DatosCalificaion(
                        inscripciones.getGrupo().getCurso().getNombre(),
                        inscripciones.getGrupo().getPeriodo(),
                        inscripciones.getCalificacion() != null
                        ? inscripciones.getCalificacion().getCalificacion() : null
                )).toList();
    }

    private BigDecimal calificacionesToPromedio(List<DatosCalificaion> calificaciones){
        if (calificaciones == null || calificaciones.isEmpty())
            return  BigDecimal.ZERO;

        //filtrar las calificaciones no nulas
        List<BigDecimal> calificacionesValidas = calificaciones.stream()
                .map(DatosCalificaion::calificacion)
                .filter(Objects::nonNull).toList();

        if(calificacionesValidas.isEmpty()){
            return BigDecimal.ZERO;
        }

        BigDecimal suma = calificacionesValidas.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return suma.divide(BigDecimal.valueOf(calificacionesValidas.size()), 2, RoundingMode.HALF_UP);
    }

    public DatosAlumno alumnoToDatosAlumno(Alumno alumno){
        if (alumno == null) return null;
        /*
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fecha = formato.parse(alumno.getFechaIngreso().toString());
        }catch (ParseException e){
            throw new IllegalArgumentException("No se pudo formatear la fecha.");
        }*/


        return new DatosAlumno(
                String.join(" ",
                        alumno.getNombre(),
                        alumno.getApellidoPaterno(),
                        alumno.getApellidoMaterno()),
                alumno.getEmail(),
                alumno.getMatricula(),
                alumno.getFechaIngreso().toString());
    }
}
