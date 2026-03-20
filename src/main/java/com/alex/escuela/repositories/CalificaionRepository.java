package com.alex.escuela.repositories;

import com.alex.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificaionRepository extends JpaRepository<Calificacion, Long> {
    boolean existsByInscripcionId(Long idCalificacion);
    boolean existsByInscripcionIdAndIdNot(Long idInscripcion,Long idCalificacion);
}
