package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.InscripcionTaller;

public interface ITallerInscripService {
    public List<InscripcionTaller> listarTodos();
    public InscripcionTaller guardarInscripcion(InscripcionTaller inscripcion);
    public InscripcionTaller buscarPorId(Long Id);
    public void eliminarInscripcion(Long Id);
    public InscripcionTaller actualizarInscripcion(InscripcionTaller inscripcion, Long Id);

    public List<InscripcionTaller> buscarPorEmpleado(Long Id);
}
