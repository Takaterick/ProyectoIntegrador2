package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.repository.EmpleadoRepository;
import com.proyecto.gym.models.repository.UsuarioRepository;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        /* QUE HA PASAO ? */
        Usuario usuarioModificado = usuarioRepository.findById(empleadoModificado.getUsuario().getId()).orElse(null);
        usuarioModificado.setUsuario(empleado.getUsuario().getUsuario());
        usuarioModificado.setContrasenia(empleado.getUsuario().getContrasenia());
        empleadoModificado.setUsuario(usuarioModificado);
        /* QUE HA PASAO ? */
        
        empleadoModificado.setNombreEmpl(empleado.getNombreEmpl());
        empleadoModificado.setApellidoEmpl(empleado.getApellidoEmpl());
        empleadoModificado.setDniEmpl(empleado.getDniEmpl());
        empleadoModificado.setTelefonoEmpl(empleado.getTelefonoEmpl());
        empleadoModificado.setCorreoEmpl(empleado.getCorreoEmpl());
        empleadoModificado.setDireccionEmpl(empleado.getDireccionEmpl());
        empleadoModificado.setRol(empleado.getRol());
        empleadoRepository.save(empleadoModificado);
        return empleadoModificado;
    }

    @Override
    public Empleado buscarPorUsuario(String usuario) {
        return empleadoRepository.findByUsuario_Usuario(usuario);
    }
}
