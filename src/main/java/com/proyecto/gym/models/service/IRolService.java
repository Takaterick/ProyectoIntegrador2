package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Rol;

public interface IRolService {
    public List<Rol> listarRoles();
    public Rol guardarRol(Rol rol);
    public Rol buscarPorId(Long id);
    public void eliminarRol(Long id);
    public Rol actualizarRol(Rol rol, Long id);
}
