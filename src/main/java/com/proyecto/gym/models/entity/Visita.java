package com.proyecto.gym.models.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="visita")
public class Visita implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_visita")
    private Long idVisita;

    @Column(name="miembro_visita")
    private String miembroVisita;

    @Column(name="membresia_visita")
    private String membresiaVisita;

    @Column(name="tipo_visita")
    private String tipoVisita;

    @Column(name="fecha_visita")
    private Date fechaVisita;

    @Column(name="pago_visita")
    private Double pagoVisita;

    @ManyToOne
    @JoinColumn(name="id_detsuscli")
    private Suscripcion suscripcion;

    public Long getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Long idVisita) {
        this.idVisita = idVisita;
    }

    public String getMiembroVisita() {
        return miembroVisita;
    }

    public void setMiembroVisita(String miembroVisita) {
        this.miembroVisita = miembroVisita;
    }

    public String getMembresiaVisita() {
        return membresiaVisita;
    }

    public void setMembresiaVisita(String membresiaVisita) {
        this.membresiaVisita = membresiaVisita;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public Double getPagoVisita() {
        return pagoVisita;
    }

    public void setPagoVisita(Double pagoVisita) {
        this.pagoVisita = pagoVisita;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }
    
    
}
