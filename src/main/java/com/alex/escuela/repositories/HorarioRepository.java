package com.alex.escuela.repositories;

import com.alex.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByGrupoId(Long id);
}
