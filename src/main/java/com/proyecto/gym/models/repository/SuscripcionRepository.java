package com.proyecto.gym.models.repository;

import com.proyecto.gym.models.entity.Suscripcion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SuscripcionRepository extends CrudRepository<Suscripcion, Long> {
    
    @Query("SELECT s FROM Suscripcion s WHERE s.cliente.id_cli = ?1")
    public Suscripcion findByCliente_Id(Long id);
}
