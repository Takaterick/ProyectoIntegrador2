package com.proyecto.gym.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity//Indica que es una entidad
@Table(name="empleado")
public class Empleado implements Serializable {

    @Id//Indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Indica que es autoincrementable
    @Column(name="id_emp")
    private Long idEmpl;

    @Column(name="nom_emp")
    @NotNull(message = "El nombre del empleado no puede estar vacio")
    @NotBlank(message = "El nombre del empleado no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El nombre del empleado debe tener entre 3 y 50 caracteres")
    private String nombreEmpl;

    @Column(name="ape_emp")
    @NotNull(message = "El apellido del empleado no puede estar vacio")
    @NotBlank(message = "El apellido del empleado no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El apellido del empleado debe tener entre 3 y 50 caracteres")
    private String apellidoEmpl;

    @Column(name="dni_emp")
    @NotNull(message = "El dni del empleado no puede estar vacio")
    @NotBlank(message = "El dni del empleado no puede estar en blanco")
    @Positive(message = "El dni del empleado no puede ser negativo")
    @Min(value = 00000000, message = "El dni del empleado no debe ser menor a 8 dígitos")
    @Max(value = 99999999, message = "El dni del empleado no debe ser mayor a 8 dígitos")
    private String dniEmpl;

    @Column(name="tel_emp")
    @NotNull(message = "El teléfono del empleado no puede estar vacio")
    @NotBlank(message = "El teléfono del empleado no puede estar en blanco")
    @Positive(message = "El teléfono del empleado no puede ser negativo")
    @Min(value = 900000000, message = "El teléfono debe empezar con 9 y no debe ser menor a 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe empezar con 9 y no debe ser mayor a 9 dígitos")
    private String telefonoEmpl;

    @Column(name="correo_emp")
    @NotNull(message = "El correo del empleado no puede estar vacio")
    @NotEmpty(message = "El correo es obligatorio, no puede estar en blanco")
    @Email(message = "El campo correo debe contener un correo electrónico válido")
    private String correoEmpl;

    @Column(name="dir_emp")
    @NotNull(message = "La dirección del empleado no puede estar vacio")
    @NotBlank(message = "La dirección del empleado no puede estar en blanco")
    @Size(min = 5, max = 100, message = "La dirección del empleado debe tener entre 5 y 100 caracteres")
    private String direccionEmpl;

    @ManyToOne
    @JoinColumn(name="id_usu")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="id_rol")
    private Rol rol;

    public Long getIdEmpl() {
        return idEmpl;
    }

    public void setIdEmpl(Long idEmpl) {
        this.idEmpl = idEmpl;
    }

    public String getNombreEmpl() {
        return nombreEmpl;
    }

    public void setNombreEmpl(String nombreEmpl) {
        this.nombreEmpl = nombreEmpl;
    }

    public String getApellidoEmpl() {
        return apellidoEmpl;
    }

    public void setApellidoEmpl(String apellidoEmpl) {
        this.apellidoEmpl = apellidoEmpl;
    }

    public String getDniEmpl() {
        return dniEmpl;
    }

    public void setDniEmpl(String dniEmpl) {
        this.dniEmpl = dniEmpl;
    }

    public String getTelefonoEmpl() {
        return telefonoEmpl;
    }

    public void setTelefonoEmpl(String telefonoEmpl) {
        this.telefonoEmpl = telefonoEmpl;
    }

    public String getCorreoEmpl() {
        return correoEmpl;
    }

    public void setCorreoEmpl(String correoEmpl) {
        this.correoEmpl = correoEmpl;
    }

    public String getDireccionEmpl() {
        return direccionEmpl;
    }

    public void setDireccionEmpl(String direccionEmpl) {
        this.direccionEmpl = direccionEmpl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
