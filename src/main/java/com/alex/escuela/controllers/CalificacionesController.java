package com.alex.escuela.controllers;

import com.alex.escuela.dto.calificaciones.CalificacionesRequest;
import com.alex.escuela.dto.calificaciones.CalificacionesResponse;
import com.alex.escuela.services.calificaciones.CalificacionesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionesController extends CommonController<CalificacionesRequest, CalificacionesResponse, CalificacionesService> {
    public CalificacionesController(CalificacionesService service) {
        super(service);
    }
}
