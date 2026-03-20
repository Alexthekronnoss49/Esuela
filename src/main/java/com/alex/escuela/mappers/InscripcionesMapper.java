package com.alex.escuela.mappers;

import com.alex.escuela.dto.datos.DatosAlumno;
import com.alex.escuela.dto.datos.DatosGrupo;
import com.alex.escuela.dto.datos.DatosInscripcion;
import com.alex.escuela.dto.datos.DatosMaestro;
import com.alex.escuela.dto.inscripciones.InscripcionesRequest;
import com.alex.escuela.dto.inscripciones.InscripcionesResponse;
import com.alex.escuela.entities.Alumno;
import com.alex.escuela.entities.Grupo;
import com.alex.escuela.entities.Inscripciones;
import com.alex.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
@AllArgsConstructor
public class InscripcionesMapper implements CommonMapper<InscripcionesRequest, InscripcionesResponse, Inscripciones> {

    private final AlumnoMapper alumnoMapper;

    private final GrupoMapper grupoMapper;

    @Override
    public Inscripciones requestToEntity(InscripcionesRequest request) {
        if (request == null) return null;

        Inscripciones inscripcion = new Inscripciones();

        return inscripcion;
    }

    public Inscripciones requestToEntity(InscripcionesRequest request, Alumno alumno, Grupo grupo) {
        if (request == null) return null;

        Inscripciones inscripcion = requestToEntity(request);
        LocalDate fechaInscripcion = LocalDate.now();

        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);
        inscripcion.setFechaInscripcion(fechaInscripcion);

        return inscripcion;
    }

    @Override
    public InscripcionesResponse entityToResponse(Inscripciones inscripcion) {
        if (inscripcion == null) return null;

        DatosAlumno alumno = alumnoMapper.alumnoToDatosAlumno(inscripcion.getAlumno());

        DatosGrupo grupo = grupoMapper.gruposToDatosGrupo(inscripcion.getGrupo());

        BigDecimal calificacion = inscripcion.getCalificacion() == null ? BigDecimal.ZERO
                : inscripcion.getCalificacion().getCalificacion();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return new InscripcionesResponse(
                inscripcion.getId(),
                alumno,
                grupo,
                calificacion,
                inscripcion.getFechaInscripcion().toString()
        );
    }

    public DatosInscripcion inscipcionToDatosInscripcion(Inscripciones inscripciones){
        if (inscripciones == null) return null;

        DatosAlumno alumno = alumnoMapper.alumnoToDatosAlumno(inscripciones.getAlumno());

        DatosGrupo grupo = grupoMapper.gruposToDatosGrupo(inscripciones.getGrupo());

        return new DatosInscripcion(
                alumno,
                grupo,
                inscripciones.getFechaInscripcion().toString());
    }

}
