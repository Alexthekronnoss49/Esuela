package com.alex.escuela.controllers;

import com.alex.escuela.dto.horarios.HorarioRequest;
import com.alex.escuela.dto.horarios.HorarioResponse;
import com.alex.escuela.services.horarios.HorarioService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorariosController extends CommonController<HorarioRequest, HorarioResponse, HorarioService> {

    public HorariosController(HorarioService service) {
        super(service);
    }
}
