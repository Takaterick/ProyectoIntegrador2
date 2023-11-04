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

@Entity
@Table(name="asistencia_clitaller")
public class Asistencia implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_asisclitaller")
    private Long idAsistencia;

    @Column(name="asis_asisclitaller")
    private String asistencia;

    @ManyToOne
    @JoinColumn(name = "id_cli")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_detemptaller")
    private InscripcionTaller inscripcionTaller;

    public Long getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Long idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public InscripcionTaller getInscripcionTaller() {
        return inscripcionTaller;
    }

    public void setInscripcionTaller(InscripcionTaller inscripcionTaller) {
        this.inscripcionTaller = inscripcionTaller;
    }

    

}
