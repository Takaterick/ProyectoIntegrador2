package com.proyecto.gym.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.Taller;

public interface TallerRepository extends CrudRepository<Taller, Long> {

    public Taller findByNomTaller(String nombre);
}
