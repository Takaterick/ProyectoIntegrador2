package com.proyecto.gym.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="equipamiento")
public class Equipamiento implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_equipamiento")
    private Long idEquipamiento;

    @Column(name="nombre_equipamiento")
    private String nombreEquipamiento;

    @Column(name="stock_equipamiento")
    private Integer stockEquipamiento;

    public Long getIdEquipamiento() {
        return idEquipamiento;
    }

    public void setIdEquipamiento(Long idEquipamiento) {
        this.idEquipamiento = idEquipamiento;
    }

    public String getNombreEquipamiento() {
        return nombreEquipamiento;
    }

    public void setNombreEquipamiento(String nombreEquipamiento) {
        this.nombreEquipamiento = nombreEquipamiento;
    }

    public Integer getStockEquipamiento() {
        return stockEquipamiento;
    }

    public void setStockEquipamiento(Integer stockEquipamiento) {
        this.stockEquipamiento = stockEquipamiento;
    }
}
