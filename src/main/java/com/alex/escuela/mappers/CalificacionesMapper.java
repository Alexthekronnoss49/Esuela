package com.alex.escuela.mappers;

import com.alex.escuela.dto.calificaciones.CalificacionesRequest;
import com.alex.escuela.dto.calificaciones.CalificacionesResponse;
import com.alex.escuela.dto.datos.DatosInscripcion;
import com.alex.escuela.dto.inscripciones.InscripcionesResponse;
import com.alex.escuela.entities.Calificacion;
import com.alex.escuela.entities.Inscripciones;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class CalificacionesMapper implements CommonMapper<CalificacionesRequest, CalificacionesResponse, Calificacion> {

    private final InscripcionesMapper inscripcionesMapper;

    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Calificacion requestToEntity(CalificacionesRequest request) {
        if (request == null) return null;

        LocalDate fecha = LocalDate.now();

        return Calificacion.builder()
                .calificacion(request.calificacion())
                .fechaRegistro(fecha)
                .build();
    }

    public Calificacion requestToEntity(CalificacionesRequest request, Inscripciones inscripciones) {
        if (request == null) return null;

        Calificacion calificacion = requestToEntity(request);
        calificacion.setInscripcion(inscripciones);

        return calificacion;
    }

    @Override
    public CalificacionesResponse entityToResponse(Calificacion calificacion) {
        if (calificacion == null) return null;

        DatosInscripcion inscripcion = inscripcionesMapper.inscipcionToDatosInscripcion(calificacion.getInscripcion());

        return new CalificacionesResponse(
                calificacion.getId(),
                inscripcion,
                calificacion.getCalificacion(),
                calificacion.getFechaRegistro().format(formato)
        );
    }
}
