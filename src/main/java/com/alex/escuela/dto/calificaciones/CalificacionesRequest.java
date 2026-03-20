package com.alex.escuela.dto.calificaciones;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CalificacionesRequest(
        @NotNull(message = "Se requiere el ID de la inscripción")
        Long idInscripcion,

        @NotNull(message = "La calificación es requerida")
        @PositiveOrZero(message = "La calificación debe ser mayor a 0")
        @Max(value = 10, message = "La calificación no puede ser mayor a 10")
        BigDecimal calificacion
) {
}
