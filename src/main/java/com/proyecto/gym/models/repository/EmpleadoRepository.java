package com.proyecto.gym.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Long>{
    
}
