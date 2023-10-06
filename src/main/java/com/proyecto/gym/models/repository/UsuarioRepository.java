package com.proyecto.gym.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    
    public Usuario findByUsuario(String usuario);
}
