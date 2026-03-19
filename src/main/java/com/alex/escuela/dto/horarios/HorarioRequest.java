package com.alex.escuela.dto.horarios;

import com.alex.escuela.entities.Grupo;
import com.alex.escuela.enums.DiasSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HorarioRequest(
        @NotNull(message = "El id del grupo no debe ser nulo")
        Grupo grupo,

        @NotBlank(message = "El día es requerido")
        DiasSemana dia,

        @NotBlank(message = "Se debe especificar la hora de inicio")
        String horaInicio,

        @NotBlank(message = "Se debe especificar la hora de fin")
        String horaFin
) {
}
