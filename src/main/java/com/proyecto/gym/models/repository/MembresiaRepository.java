package com.proyecto.gym.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.gym.models.entity.Membresia;

@Repository
public interface MembresiaRepository extends CrudRepository<Membresia, Long>{
    
}
