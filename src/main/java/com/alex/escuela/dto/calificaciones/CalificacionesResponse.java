package com.alex.escuela.dto.calificaciones;

import com.alex.escuela.dto.datos.DatosInscripcion;
import com.alex.escuela.dto.inscripciones.InscripcionesResponse;
import com.alex.escuela.entities.Inscripciones;

import java.math.BigDecimal;

public record CalificacionesResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificación,
        String fechaRegistro
) {
}
