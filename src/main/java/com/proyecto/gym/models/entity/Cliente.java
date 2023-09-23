package com.proyecto.gym.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity//Indica que es una entidad
@Table(name="cliente")
public class Cliente implements Serializable{
    
    @Id//Indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Indica que es autoincrementable
    @Column(name="id_cli")
    private Long id_cli;

    @Column(name="nom_cli")
    @NotNull(message = "El nombre del cliente no puede estar vacio")
    @NotBlank(message = "El nombre del cliente no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El nombre del cliente debe tener entre 3 y 50 caracteres")
    private String nom_cli;

    @Column(name="ape_cli")
    @NotNull(message = "El apellido del cliente no puede estar vacio")
    @NotBlank(message = "El apellido del cliente no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El apellido del cliente debe tener entre 3 y 50 caracteres")
    private String ape_cli;

    @Column(name="dni_cli")
    @NotNull(message = "El dni del cliente no puede estar vacio")
    @NotBlank(message = "El dni del cliente no puede estar en blanco")
    @Size(min = 8, max = 8, message = "El dni del cliente debe tener 8 caracteres")
    private String dni_cli;

    @Column(name="tel_cli")
    @NotNull(message = "El teléfono del cliente no puede estar vacio")
    @NotBlank(message = "El teléfono del cliente no puede estar en blanco")
    @Size(min = 9, max = 9, message = "El teléfono del cliente debe tener 9 caracteres")
    private String tel_cli;

    @Column(name="correo_cli")
    @NotNull(message = "El correo del cliente no puede estar vacio")
    @NotEmpty(message = "El correo es obligatorio, no puede estar en blanco")
    @Email(message = "El campo correo debe contener un correo electrónico válido")
    private String correo_cli;

    @Column(name="dir_cli")
    @NotNull(message = "La dirección del cliente no puede estar vacio")
    @NotBlank(message = "La dirección del cliente no puede estar en blanco")
    @Size(min = 5, max = 100, message = "La dirección del cliente debe tener entre 5 y 100 caracteres")
    private String dir_cli;

    public Long getId_cli() {
        return id_cli;
    }

    public void setId_cli(Long id_cli) {
        this.id_cli = id_cli;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getApe_cli() {
        return ape_cli;
    }

    public void setApe_cli(String ape_cli) {
        this.ape_cli = ape_cli;
    }

    public String getDni_cli() {
        return dni_cli;
    }

    public void setDni_cli(String dni_cli) {
        this.dni_cli = dni_cli;
    }

    public String getTel_cli() {
        return tel_cli;
    }

    public void setTel_cli(String tel_cli) {
        this.tel_cli = tel_cli;
    }

    public String getCorreo_cli() {
        return correo_cli;
    }

    public void setCorreo_cli(String correo_cli) {
        this.correo_cli = correo_cli;
    }

    public String getDir_cli() {
        return dir_cli;
    }

    public void setDir_cli(String dir_cli) {
        this.dir_cli = dir_cli;
    }
    
}
