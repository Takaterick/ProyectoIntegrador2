package com.proyecto.gym.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity//Indica que es una entidad
@Table(name="taller")
public class Taller {

    @Id//Indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Indica que es autoincrementable
    @Column(name="id_taller")
    private Long id_taller;

    @Column(name="nom_taller")
    private String nom_taller;

    public Long getId_taller() {
        return id_taller;
    }

    public void setId_taller(Long id_taller) {
        this.id_taller = id_taller;
    }

    public String getNom_taller() {
        return nom_taller;
    }

    public void setNombre_taller(String nom_taller) {
        this.nom_taller = nom_taller;
    }
}
