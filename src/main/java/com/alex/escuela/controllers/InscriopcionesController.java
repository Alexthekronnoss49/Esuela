package com.alex.escuela.controllers;

import com.alex.escuela.dto.inscripciones.InscripcionesRequest;
import com.alex.escuela.dto.inscripciones.InscripcionesResponse;
import com.alex.escuela.services.inscripciones.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscriopcionesController extends CommonController<InscripcionesRequest, InscripcionesResponse, InscripcionService> {
    public InscriopcionesController(InscripcionService service) {
        super(service);
    }
}
