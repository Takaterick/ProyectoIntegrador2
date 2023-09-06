package com.proyecto.gym.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Suscripcion")
public class Membresia  implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_sus")
    private Long id_sus;

    
    @Column(name = "nom_sus")
    @NotNull(message = "El nombre de la suscripcion no puede ser vacio")
    @NotBlank(message = "El nombre de la suscripcion no puede estar en blanco")
    private String nom_sus;

    @Column(name="precio_sus")
    @NotNull(message = "El precio de la suscripcion no puede ser vacio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de la suscripcion debe ser mayor a 0")
    private Double precio_sus;

    public Long getId_sus() {
        return id_sus;
    }

    public void setId_sus(Long id_sus) {
        this.id_sus = id_sus;
    }

    public String getNom_sus() {
        return nom_sus;
    }

    public void setNom_sus(String nom_sus) {
        this.nom_sus = nom_sus;
    }

    public Double getPrecio_sus() {
        return precio_sus;
    }

    public void setPrecio_sus(Double precio_sus) {
        this.precio_sus = precio_sus;
    }

    
}
