package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.repository.EmpleadoRepository;

import com.proyecto.gym.models.entity.Empleado;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Override
    public List<Empleado> listarTodos() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarPorId(Long Id) {
        return empleadoRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminarEmpleado(Long Id) {
        empleadoRepository.deleteById(Id);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado, Long Id) {
        Empleado empleadoModificado = empleadoRepository.findById(Id).orElse(null);
        empleadoModificado.setNombreEmpl(empleado.getNombreEmpl());
        empleadoModificado.setApellidoEmpl(empleado.getApellidoEmpl());
        empleadoModificado.setDniEmpl(empleado.getDniEmpl());
        empleadoModificado.setTelefonoEmpl(empleado.getTelefonoEmpl());
        empleadoModificado.setCorreoEmpl(empleado.getCorreoEmpl());
        empleadoModificado.setDireccionEmpl(empleado.getDireccionEmpl());
        empleadoModificado.setUsuario(empleado.getUsuario());
        empleadoModificado.setRol(empleado.getRol());
        empleadoRepository.save(empleadoModificado);
        return empleadoModificado;
    }
}
