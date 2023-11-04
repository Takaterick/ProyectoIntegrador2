package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Asistencia;

public interface IAsistenciaService {
    
    public List<Asistencia> listarTodos();
    public Asistencia guardarAsistencia(Asistencia asistencia);
    public Asistencia buscarPorId(Long id);
    public void eliminarAsistencia(Long id);
    public Asistencia actualizarAsistencia(Asistencia asistencia, Long id);

    public Asistencia marcarAsistencia(Asistencia asistencia);

    public List<Asistencia> listarTalleresNoInscritos(Long Id);
    public List<Asistencia> listarTalleresInscritos(Long Id);
    public List<Asistencia> buscarPorTaller(Long Id);
}
