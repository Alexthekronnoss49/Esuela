package com.alex.escuela.dto.horarios;

public record HorarioResponse(
        Long id,
        Long idGrupo,
        String dia,
        String HoraInicio,
        String HoraFin
) {
}
