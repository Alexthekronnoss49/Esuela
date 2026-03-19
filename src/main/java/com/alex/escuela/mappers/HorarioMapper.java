package com.alex.escuela.mappers;

import com.alex.escuela.dto.datos.DatosGrupo;
import com.alex.escuela.dto.horarios.HorarioRequest;
import com.alex.escuela.dto.horarios.HorarioResponse;
import com.alex.escuela.entities.Grupo;
import com.alex.escuela.entities.Horario;
import com.alex.escuela.enums.DiasSemana;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    private final GrupoMapper grupoMapper;

    @Override
    public Horario requestToEntity(HorarioRequest request) {
        if (request == null) return null;

        Horario horario = new Horario();

        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFin(request.horaFin());

        return horario;
    }

    public Horario requestToEntity(HorarioRequest request, Grupo grupo, DiasSemana dia) {
        if (request == null) return null;

        Horario horario = requestToEntity(request);

        horario.setGrupo(grupo);
        horario.setDia(dia);

        return horario;
    }

    @Override
    public HorarioResponse entityToResponse(Horario horario) {
        if (horario == null) return null;

        DatosGrupo grupo = grupoMapper.gruposToDatosGrupo(horario.getGrupo());

        return new HorarioResponse(
                horario.getId(),
                grupo,
                String.join( " ",
                        horario.getDia().getDescripcion(),
                        horario.getHoraInicio()+" -",
                        horario.getHoraFin()
                )
        );
    }
}
