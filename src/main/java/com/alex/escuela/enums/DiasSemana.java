package com.alex.escuela.enums;

import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DiasSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado");

    private final String descripcion;

    private static String quitarAcentos(String s){
        return s.toLowerCase()
                .replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o")
                .replace("ú", "u").replace("ü", "u");
    }

    public static DiasSemana fromDescripcion(String descripcion){
        String buscado = quitarAcentos(descripcion.trim());
        for (DiasSemana dia : values()){
            String descDia = quitarAcentos(dia.descripcion);
            if (descDia.equalsIgnoreCase(buscado))
                return dia;
        }
        throw new RecursoNoEncontradoException("No existe día de la semana con la descripción: "+descripcion);
    }
}
