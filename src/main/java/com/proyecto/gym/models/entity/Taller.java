package com.proyecto.gym.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity//Indica que es una entidad
@Table(name="taller")
public class Taller implements Serializable{

    @Id//Indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Indica que es autoincrementable
    @Column(name="id_taller")
    private Long id_taller;

    @Column(name="nom_taller")
    @NotNull(message = "El nombre del taller no puede estar vacio")
    @NotBlank(message = "El nombre del taller no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El nombre del taller debe tener entre 3 y 50 caracteres")
    private String nomTaller;

    @Column(name="desc_taller")
    private String descTaller;

    public Long getId_taller() {
        return id_taller;
    }

    public void setId_taller(Long id_taller) {
        this.id_taller = id_taller;
    }

    public String getNomTaller() {
        return nomTaller;
    }

    public void setNomTaller(String nomTaller) {
        this.nomTaller = nomTaller;
    }
}
