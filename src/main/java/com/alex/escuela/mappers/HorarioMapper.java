package com.alex.escuela.mappers;

import com.alex.escuela.dto.horarios.HorarioRequest;
import com.alex.escuela.dto.horarios.HorarioResponse;
import com.alex.escuela.entities.Horario;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    @Override
    public Horario requestToEntity(HorarioRequest request) {
        if (request == null) return null;

        Horario horario = new Horario();

        horario.setGrupo(request.grupo());
        horario.setDia(request.dia());
        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFin(request.horaFin());

        return horario;
    }

    @Override
    public HorarioResponse entityToResponse(Horario entity) {
        if (entity == null) return null;

        return new HorarioResponse(
                entity.getId(),
                entity.getGrupo().getId(),
                entity.getDia().name(),
                entity.getHoraInicio(),
                entity.getHoraFin()
        );
    }
}
