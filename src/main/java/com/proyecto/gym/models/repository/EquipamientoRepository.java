package com.proyecto.gym.models.repository;

import org.springframework.data.repository.CrudRepository;
import com.proyecto.gym.models.entity.Equipamiento;

public interface EquipamientoRepository extends CrudRepository<Equipamiento, Long> {
    
}
