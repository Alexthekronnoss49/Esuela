package com.alex.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosCalificaion(
        String curso,
        String periodo,
        BigDecimal calificacion
) {
}
