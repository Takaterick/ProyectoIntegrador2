package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Equipamiento;

public interface IEquipamientoService {
    public List<Equipamiento> listarTodos();
    public Equipamiento guardarEquipamiento(Equipamiento equipamiento);
    public Equipamiento buscarPorId(Long id);
    public void eliminarEquipamiento(Long id);
    public Equipamiento actualizarEquipamiento(Equipamiento equipamiento, Long id);
}
