package com.proyecto.gym.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
    
    public Cliente findByUsuario_Usuario(String usuario);
}
