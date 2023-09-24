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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Entity
@Table(name="empleado")
public class Empleado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_emp")
    private Long idEmpl;

    @Column(name="nom_emp")
    private String nombreEmpl;

    @Column(name="ape_emp")
    private String apellidoEmpl;

    @Column(name="dni_emp")
    private String dniEmpl;

    @Column(name="tel_emp")
    private String telefonoEmpl;

    @Column(name="correo_emp")
    private String correoEmpl;

    @Column(name="dir_emp")
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
