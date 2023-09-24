package com.proyecto.gym.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Todo modelo debe tener la anotacion "entity"
@Entity
//Indicar con otra anotacion el nombre en la tabla de la bd
//La clase debe implementar de la interfaz serializable para el envio de datos
@Table(name="Usuario")
public class Usuario implements Serializable{
    //Indicamos que el atributo es la llave principal de la tabla
    @Id
    //Indicar que sera de forma automatica el id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long id;

    @Column(name = "usu_usu")
    private String usuario;

    @Column(name = "con_usu")
    private String contrasenia;

    @Column(name = "est_usu")
    private int estado;

    @Column(name = "bloq_usu")
    private int bloqueo;

    @Column(name = "desc_bloq_usu")
    private String desc_bloq;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(int bloqueo) {
        this.bloqueo = bloqueo;
    }

    public String getDesc_bloq() {
        return desc_bloq;
    }

    public void setDesc_bloq(String desc_bloq) {
        this.desc_bloq = desc_bloq;
    }
}
