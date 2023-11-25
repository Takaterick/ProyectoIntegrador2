package com.proyecto.gym.models.repository;

import com.proyecto.gym.models.entity.Suscripcion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SuscripcionRepository extends CrudRepository<Suscripcion, Long> {
    
    @Query("SELECT s FROM Suscripcion s WHERE s.cliente.id_cli = ?1")
    public Suscripcion findByCliente_Id(Long id);

    @Query(value = "select sum(s.precio_sus*TIMESTAMPDIFF(MONTH, d.inicio_detsuscli, d.venc_detsuscli)) as Precio_total from det_sus_cli d\n" + //
            "inner join suscripcion s on (d.id_sus=s.id_sus)\n" + //
            "where d.est_detsuscli=\"Pagado\" and d.inicio_detsuscli = CURDATE()", nativeQuery = true)
    public Double ventaDiaria();

    @Query(value = "select sum(s.precio_sus*TIMESTAMPDIFF(MONTH, d.inicio_detsuscli, d.venc_detsuscli)) as Precio_total from det_sus_cli d\n" + //
            "inner join suscripcion s on (d.id_sus=s.id_sus)\n" + //
            "where d.est_detsuscli=\"Pagado\" AND MONTH(d.inicio_detsuscli) = MONTH(CURDATE()) AND\n" + //
            "YEAR(d.inicio_detsuscli) = YEAR(CURDATE())", nativeQuery = true)
    public Double ventaMensual();

    @Query(value = "select count(id_detsuscli) from det_sus_cli\n" + //
            "where est_detsuscli=\"Pagado\" and venc_detsuscli > CURDATE()", nativeQuery = true)
    public Integer suscripcionesTotales();
}
