package com.proyecto.gym.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.Alquiler;

public interface AlquilerRepository extends CrudRepository<Alquiler, Long> {

    @Query(value = "SELECT * FROM Alquiler_equipo WHERE id_cli = ?1", nativeQuery = true)
    public List<Alquiler> listAlquilerCliente(Long id);
}
