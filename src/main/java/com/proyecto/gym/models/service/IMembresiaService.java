package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Membresia;


public interface IMembresiaService {
    public List<Membresia> listarTodos();
    public Membresia guardar(Membresia membresia);
    public Membresia buscarPorId(Long Id);
    public void eliminar(Long Id);

    public Membresia actualizarPorId(Membresia membresia, Long Id);
    public Boolean eliminarDos(Long Id);
}
