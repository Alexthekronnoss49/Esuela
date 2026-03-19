package com.alex.escuela.entities;

import com.alex.escuela.enums.DiasSemana;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="HORARIOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Column(name = "DIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiasSemana dia;

    @Column(name = "HORA_INICIO", nullable = false, length = 5)
    private String horaInicio;

    @Column(name = "HORA_FIN", nullable = false, length = 5)
    private String horaFin;
}
