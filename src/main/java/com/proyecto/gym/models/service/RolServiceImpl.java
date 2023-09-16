package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Rol;
import com.proyecto.gym.models.repository.RolRepository;

@Service
public class RolServiceImpl implements IRolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listarRoles() {
        return (List<Rol>) rolRepository.findAll();
    }

    @Override
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol buscarPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Rol actualizarRol(Rol rol, Long id) {
        Rol rolModificado = rolRepository.findById(id).orElse(null);
        rolModificado.setNom_rol(rol.getNom_rol());
        rolRepository.save(rolModificado);
        return rolModificado;
    }
    
}
