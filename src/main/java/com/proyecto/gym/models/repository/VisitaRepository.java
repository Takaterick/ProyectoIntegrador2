package com.proyecto.gym.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.Visita;

public interface VisitaRepository extends CrudRepository<Visita, Long> {
    
    @Query(value = "select count(id_visita) from visita\n" + //
            "where YEAR(fecha_visita) = YEAR(CURDATE());", nativeQuery = true)
    public Integer visitasTotales();
}
