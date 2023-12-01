package com.proyecto.gym.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alquiler_equipo")
public class Alquiler implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alquiler")
    private Long idAlquiler;

    @Column(name = "fecha_alquiler")
    private Date fechaAlquiler;

    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @Column(name = "estado_alquiler")
    private String estadoAlquiler;

    @Column(name = "dias_alquiler")
    private int diasAlquiler;

    @Column(name = "cantidad_alquiler")
    private int cantidadAlquiler;

    @ManyToOne
    @JoinColumn(name = "id_equipamiento")
    private Equipamiento equipamiento;

    @ManyToOne
    @JoinColumn(name = "id_cli")
    private Cliente cliente;

    public Long getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Long idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Date getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(Date fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstadoAlquiler() {
        return estadoAlquiler;
    }

    public void setEstadoAlquiler(String estadoAlquiler) {
        this.estadoAlquiler = estadoAlquiler;
    }

    public int getDiasAlquiler() {
        return diasAlquiler;
    }

    public void setDiasAlquiler(int diasAlquiler) {
        this.diasAlquiler = diasAlquiler;
    }

    public int getCantidadAlquiler() {
        return cantidadAlquiler;
    }

    public void setCantidadAlquiler(int cantidadAlquiler) {
        this.cantidadAlquiler = cantidadAlquiler;
    }

    public Equipamiento getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(Equipamiento equipamiento) {
        this.equipamiento = equipamiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
}
