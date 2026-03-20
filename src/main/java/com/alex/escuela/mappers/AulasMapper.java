package com.alex.escuela.mappers;

import com.alex.escuela.dto.aulas.AulasRequest;
import com.alex.escuela.dto.aulas.AulasResponse;
import com.alex.escuela.entities.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulasMapper implements CommonMapper<AulasRequest, AulasResponse, Aula>{
    @Override
    public Aula requestToEntity(AulasRequest request) {
        if (request == null) return null;

        return Aula.builder()
                .nombre(request.nombre())
                .capacidad(request.capacidad())
                .build();
    }

    @Override
    public AulasResponse entityToResponse(Aula entity) {
        if (entity == null) return null;

        return new AulasResponse(
                entity.getNombre(),
                entity.getCapacidad()
        );
    }
}
