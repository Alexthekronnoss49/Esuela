package com.alex.escuela.dto.horarios;

import com.alex.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo idGrupo,
        String horario
) {
}
