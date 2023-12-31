package com.proyecto.gym.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.gym.models.entity.InscripcionTaller;

public interface InscripcionTallerRepository extends CrudRepository<InscripcionTaller, Long> {
    
    //@Query("SELECT i FROM InscripcionTaller i WHERE i.empleado.idEmpl = ?1")
    public List<InscripcionTaller> findByEmpleado_idEmpl(Long id);

    @Query(value = "SELECT * FROM\n" + //
                    "taller t\n" + //
                    " INNER JOIN det_emp_taller d ON t.id_taller = d.id_taller\n" + //
                    "WHERE\n" + //
                    " NOT EXISTS (\n" + //
                    "    SELECT\n" + //
                    "      1\n" + //
                    "    FROM\n" + //
                    "      asistencia_clitaller a\n" + //
                    "    WHERE\n" + //
                    "      a.id_cli = ?1 AND\n" + //
                    "      a.id_detemptaller = d.id_detemptaller\n" + //
                    " ) AND\n" + //
                    " d.fecha_detemptaller >= CURDATE();", nativeQuery = true)
    public List<InscripcionTaller> listTallersNoInscript(Long id);

    @Query(value = "SELECT\n" + //
                    " *\n" + //
                    "FROM\n" + //
                    " taller t\n" + //
                    " INNER JOIN det_emp_taller d ON t.id_taller = d.id_taller\n" + //
                    " INNER JOIN asistencia_clitaller a ON d.id_detemptaller = a.id_detemptaller\n" + //
                    "WHERE\n" + //
                    " a.id_cli = ?1 AND\n" + //
                    " d.fecha_detemptaller >= CURDATE();", nativeQuery = true)
    public List<InscripcionTaller> listTallersInscript(Long id);
}