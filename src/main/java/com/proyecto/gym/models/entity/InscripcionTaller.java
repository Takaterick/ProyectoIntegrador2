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
@Table(name = "det_emp_taller")
public class InscripcionTaller implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detemptaller")
    private Long idInsTaller;

    @Column(name = "fecha_detemptaller")
    private Date fechaTaller;

    @Column(name = "hora_inicio_detemptaller")
    private Date horaInicio;

    @Column(name = "hora_fin_detemptaller")
    private Date horaFin;

    @Column(name = "cupos_detemptaller")
    private int cupos;

    @ManyToOne
    @JoinColumn(name = "id_taller")
    private Taller taller;

    @ManyToOne
    @JoinColumn(name = "id_emp")
    private Empleado empleado;

    public Long getIdInsTaller() {
        return idInsTaller;
    }

    public void setIdInsTaller(Long idInsTaller) {
        this.idInsTaller = idInsTaller;
    }

    public Date getFechaTaller() {
        return fechaTaller;
    }

    public void setFechaTaller(Date fechaTaller) {
        this.fechaTaller = fechaTaller;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public Taller getTaller() {
        return taller;
    }

    public void setTaller(Taller taller) {
        this.taller = taller;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
