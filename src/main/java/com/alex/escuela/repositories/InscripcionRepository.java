package com.alex.escuela.repositories;

import com.alex.escuela.entities.Inscripciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripciones, Long> {

    boolean existsByAlumnoId(Long alumnoId);
    boolean existsByGrupoId(Long id);
    boolean existsByAlumnoIdAndGrupoId(Long idAlumno, Long idGrupo);
    boolean existsByAlumnoIdAndGrupoIdAndIdNot(Long idAlumno, Long idGrupo, Long id);

}
