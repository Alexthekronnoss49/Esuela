package com.alex.escuela.controllers;

import com.alex.escuela.dto.alumnos.AlumnoRequest;
import com.alex.escuela.dto.alumnos.AlumnoResponse;
import com.alex.escuela.dto.aulas.AulasRequest;
import com.alex.escuela.dto.aulas.AulasResponse;
import com.alex.escuela.services.alumnos.AlumnoService;
import com.alex.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulasRequest, AulasResponse, AulaService>{

    public AulaController(AulaService service){
        super(service);
    }
}
