package com.alex.escuela.repositories;

import com.alex.escuela.entities.Aula;
import com.alex.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    boolean existsByMaestroId(Long maestroId);
    boolean existsByAulaId(Long aulaId);
    boolean existsByCursoId(Long cursoId);
    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(Long cursoId, Long maestroId, Long aulaId, String periodo);
    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(Long cursoId, Long maestroId, Long aulaId, String periodo, Long id);
    List<Grupo> findByAulaId(Long id);
}
