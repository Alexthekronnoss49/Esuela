package com.alex.escuela.controllers;

import com.alex.escuela.dto.grupos.GrupoRequest;
import com.alex.escuela.dto.grupos.GrupoResponse;
import com.alex.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {

    public GrupoController(GrupoService service) {
        super(service);
    }
}
