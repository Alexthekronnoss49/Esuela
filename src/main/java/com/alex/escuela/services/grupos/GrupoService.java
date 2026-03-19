package com.alex.escuela.services.grupos;

import com.alex.escuela.dto.grupos.GrupoRequest;
import com.alex.escuela.dto.grupos.GrupoResponse;
import com.alex.escuela.services.CrudService;
import org.springframework.stereotype.Service;

public interface GrupoService extends CrudService<GrupoRequest, GrupoResponse> {
}