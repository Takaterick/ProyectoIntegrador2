package com.proyecto.gym.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import com.proyecto.gym.models.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
    
    public Cliente findByUsuario_Usuario(String usuario);

    @Query(value = "SELECT * FROM cliente ORDER BY created_date DESC LIMIT 3", nativeQuery = true)
    public List<Cliente> listarClientesRecientes();
}
