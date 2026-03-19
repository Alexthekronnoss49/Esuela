package com.alex.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AULAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    /*
    @OneToMany(mappedBy = "aula")
    private List<Grupo> grupos = new ArrayList<>();
    */
}
