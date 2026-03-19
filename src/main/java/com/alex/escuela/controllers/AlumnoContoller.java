package com.alex.escuela.controllers;

import com.alex.escuela.dto.alumnos.AlumnoRequest;
import com.alex.escuela.dto.alumnos.AlumnoResponse;
import com.alex.escuela.services.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoContoller extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {

    public AlumnoContoller(AlumnoService service){
        super(service);
    }
}
