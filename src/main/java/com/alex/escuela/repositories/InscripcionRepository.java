package com.alex.escuela.repositories;

import com.alex.escuela.entities.Inscripciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripciones, Long> {

    boolean existsByAlumnoId(Long alumnoId);
}
