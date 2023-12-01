package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Alquiler;

public interface IAlquilerService {
    
    public List<Alquiler> listarTodos();
    public List<Alquiler> buscarPorCliente(Long id);
    public Alquiler guardarAlquiler(Alquiler alquiler);
    public Alquiler buscarPorId(Long id);
    public void eliminarAlquiler(Long id);
    public Alquiler actualizarAlquiler(Alquiler alquiler, Long id);
    public Alquiler devolverAlquiler(Long id);
}
