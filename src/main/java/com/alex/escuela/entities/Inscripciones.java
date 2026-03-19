package com.alex.escuela.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="INSCRIPCIONES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Inscripciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Column(name= "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion;

    @OneToOne(mappedBy = "inscripcion")
    private Calificacion calificacion;
}
