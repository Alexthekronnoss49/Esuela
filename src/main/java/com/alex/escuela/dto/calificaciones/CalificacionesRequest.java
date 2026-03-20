package com.alex.escuela.dto.calificaciones;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalificacionesRequest(
        @NotNull(message = "Se requiere el ID de la inscripción")
        Long idInscripcion,

        @NotNull(message = "La calificación es requerida")
        BigDecimal calificacion
) {
}
