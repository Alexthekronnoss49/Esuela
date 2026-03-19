package com.alex.escuela.services.horarios;

import com.alex.escuela.dto.horarios.HorarioRequest;
import com.alex.escuela.dto.horarios.HorarioResponse;
import com.alex.escuela.entities.Grupo;
import com.alex.escuela.entities.Horario;
import com.alex.escuela.enums.DiasSemana;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.HorarioMapper;
import com.alex.escuela.repositories.GrupoRepository;
import com.alex.escuela.repositories.HorarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService{

    private final HorarioRepository horarioRepository;

    private final GrupoRepository grupoRepository;

    private final HorarioMapper horarioMapper;

    @Override
    public List<HorarioResponse> listar() {
        return horarioRepository.findAll()
                .stream()
                .map(horarioMapper::entityToResponse)
                .toList();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        Horario horario = obtenerhorarioOException(id);

        return horarioMapper.entityToResponse(horario);
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {

        Grupo grupo = obtenerGrupoOException(request.idGrupo());

        DiasSemana dia = DiasSemana.fromDescripcion(request.dia());

        vaidarCongruencia(request.horaInicio(), request.horaFin());

        Horario horario = horarioRepository.save(horarioMapper.requestToEntity(request, grupo, dia));

        return horarioMapper.entityToResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    private Grupo obtenerGrupoOException(Long id){
        return grupoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Grupo no encontrado con el id: "+id));
    }

    private Horario obtenerhorarioOException(Long id){
        return horarioRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Horario no encontrado con el id: "+id));
    }

    public LocalTime validarFormatoHoraInicio(String horaInicio){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime inicio= LocalTime.parse(horaInicio, formatter);
            return inicio;
        } catch (Exception e) {
            throw new IllegalArgumentException("La hora de inicio no tiene el formato correcto ", e);
        }
    }

    public LocalTime validarFormatoHoraFin(String horaFin){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime fin=LocalTime.parse(horaFin, formatter);
            return fin;
        } catch (Exception e) {
            throw new IllegalArgumentException("La hora de fin no tiene el formato correcto ", e);
        }
    }

    public void vaidarCongruencia(String horaInicio, String horaFin){
        LocalTime inicio = validarFormatoHoraInicio(horaInicio);
        LocalTime fin = validarFormatoHoraFin(horaFin);

        if (inicio.isAfter(fin) || fin.isBefore(inicio)){
            throw new IllegalArgumentException("La hora de inicio no puede ser mayor a la de fin y viceversa");
        }
    }
}
