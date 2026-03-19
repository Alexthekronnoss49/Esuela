package com.alex.escuela.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) {
}
