package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.InscripcionTaller;
import com.proyecto.gym.models.repository.InscripcionTallerRepository;

@Service
public class InscripTallerImpl implements ITallerInscripService{
    
    @Autowired
    private InscripcionTallerRepository inscripcionRepository;

    @Override
    public List<InscripcionTaller> listarTodos() {
        return (List<InscripcionTaller>) inscripcionRepository.findAll();
    }

    @Override
    public InscripcionTaller guardarInscripcion(InscripcionTaller inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public InscripcionTaller buscarPorId(Long Id) {
        return inscripcionRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminarInscripcion(Long Id) {
        inscripcionRepository.deleteById(Id);
    }

    @Override
    public InscripcionTaller actualizarInscripcion(InscripcionTaller inscripcion, Long Id) {
        InscripcionTaller inscripcionActualizada = inscripcionRepository.findById(Id).orElse(null);
        inscripcionActualizada.setFechaTaller(inscripcion.getFechaTaller());
        inscripcionActualizada.setHoraInicio(inscripcion.getHoraInicio());
        inscripcionActualizada.setHoraFin(inscripcion.getHoraFin());
        inscripcionActualizada.setCupos(inscripcion.getCupos());
        inscripcionActualizada.setTaller(inscripcion.getTaller());
        inscripcionActualizada.setEmpleado(inscripcion.getEmpleado());

        inscripcionRepository.save(inscripcionActualizada);
        return inscripcionActualizada;

    }

    @Override
    public List<InscripcionTaller> buscarPorEmpleado(Long Id) {
        return inscripcionRepository.findByEmpleado_idEmpl(Id);
    }
}
