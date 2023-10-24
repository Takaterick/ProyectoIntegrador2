package com.proyecto.gym.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.InscripcionTaller;

public interface InscripcionTallerRepository extends CrudRepository<InscripcionTaller, Long> {
    
    //@Query("SELECT i FROM InscripcionTaller i WHERE i.empleado.idEmpl = ?1")
    public List<InscripcionTaller> findByEmpleado_idEmpl(Long id);
}
