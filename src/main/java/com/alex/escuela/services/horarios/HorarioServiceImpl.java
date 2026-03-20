package com.alex.escuela.services.horarios;

import com.alex.escuela.dto.horarios.HorarioRequest;
import com.alex.escuela.dto.horarios.HorarioResponse;
import com.alex.escuela.entities.Aula;
import com.alex.escuela.entities.Grupo;
import com.alex.escuela.entities.Horario;
import com.alex.escuela.enums.DiasSemana;
import com.alex.escuela.exceptions.RecursoNoEncontradoException;
import com.alex.escuela.mappers.HorarioMapper;
import com.alex.escuela.repositories.AulaRepository;
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

    private final AulaRepository aulaRepository;

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
        LocalTime inicio = validarFormatoHoraInicio(request.horaInicio());
        LocalTime fin = validarFormatoHoraFin(request.horaFin());

        vaidarCongruencia(inicio, fin);
        evitarTraslapes(dia, inicio, fin, grupo);

        Horario horario = horarioRepository.save(horarioMapper.requestToEntity(request, grupo, dia));

        return horarioMapper.entityToResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        if (request == null || id == null)return null;

        Horario horario = obtenerhorarioOException(id);
        Grupo grupo = obtenerGrupoOException(request.idGrupo());
        DiasSemana dia = DiasSemana.fromDescripcion(request.dia());
        LocalTime inicio = validarFormatoHoraInicio(request.horaInicio());
        LocalTime fin = validarFormatoHoraFin(request.horaFin());

        vaidarCongruencia(inicio, fin);
        evitarTraslapes(dia, inicio, fin, grupo);

        horario.setGrupo(grupo);
        horario.setDia(dia);
        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFin(request.horaFin());

        return horarioMapper.entityToResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        horarioRepository.delete(obtenerhorarioOException(id));
    }

    private Grupo obtenerGrupoOException(Long id){
        return grupoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Grupo no encontrado con el id: "+id));
    }

    private Horario obtenerhorarioOException(Long id){
        return horarioRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Horario no encontrado con el id: "+id));
    }

    private Aula obtenerAulaOException(Long id){
        return aulaRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Aula no encontrado con el id: "+id));
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

    public void vaidarCongruencia(LocalTime horaInicio, LocalTime horaFin){

        if (horaInicio.isAfter(horaFin)){
            throw new IllegalArgumentException("La hora de inicio no puede ser mayor a la de fin y viceversa");
        }
    }

    public void evitarTraslapes(DiasSemana dia, LocalTime inicio, LocalTime fin, Grupo grupo){

        Aula aula = obtenerAulaOException(grupo.getAula().getId());

        List<Grupo> gruposRelacionadosConAula =  grupoRepository.findByAulaId(aula.getId());

        for (Grupo grupoR : gruposRelacionadosConAula) {

            List<Horario> horarios = horarioRepository.findByGrupoId(grupo.getId());

            for (Horario horario : horarios) {
                LocalTime horaInicio = validarFormatoHoraInicio(horario.getHoraInicio());
                LocalTime horaFin = validarFormatoHoraFin(horario.getHoraFin());

                if (horario.getDia().equals(dia)) {
                    boolean traslapado = comprobarSiHayTraslape(inicio, fin, horaInicio, horaFin);
                    if (traslapado){
                        throw new IllegalArgumentException("El aula "+aula.getNombre()+" estará ocupada por el grupo con id "
                                + grupoR.getId() + " en este horario.");
                    }
                }
            }

        }
    }

    public static boolean comprobarSiHayTraslape(LocalTime inicio1, LocalTime fin1,
                                          LocalTime inicio2, LocalTime fin2){
    return inicio1.isBefore(fin2) && fin1.isAfter(inicio2);
    }

}
