package com.alex.escuela.controllers;

import com.alex.escuela.dto.maestros.MaestroRequest;
import com.alex.escuela.dto.maestros.MaestroResponse;
import com.alex.escuela.services.maestros.MaestoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maestros")
public class MaestroController extends CommonController<MaestroRequest, MaestroResponse, MaestoService> {
    public MaestroController(MaestoService service){
        super(service);
    }
}
