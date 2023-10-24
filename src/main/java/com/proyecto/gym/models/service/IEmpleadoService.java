package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Empleado;

public interface IEmpleadoService {
    public List<Empleado> listarTodos();
    public Empleado guardarEmpleado(Empleado empleado);
    public Empleado buscarPorId(Long Id);
    public void eliminarEmpleado(Long Id);
    public Empleado actualizarEmpleado(Empleado empleado, Long Id);

    public Empleado buscarPorUsuario(String usuario);
}
