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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "det_emp_taller")
public class InscripcionTaller implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detemptaller")
    private Long idInsTaller;

    @Column(name = "fecha_detemptaller")
    @NotNull(message = "El fecha del taller no puede estar vacio")
    @Future(message = "La fecha del taller debe ser mayor que ahora")
    private Date fechaTaller;

    @Temporal(value = TemporalType.TIME)
    @Column(name = "hora_inicio_detemptaller")
    @NotNull(message = "La hora de inicio del taller no puede estar vacio")
    private Date horaInicio;

    @Temporal(TemporalType.TIME)
    @Column(name = "hora_fin_detemptaller")
    @NotNull(message = "La hora de fin del taller no puede estar vacio")
    private Date horaFin;

    @Column(name = "cupos_detemptaller")
    @NotNull(message = "Los cupos no puede estar vacio")
    @Min(value = 5, message = "La cantidad de cupo debe ser mayor o igual a 5")
    @Max(value = 30, message = "El cantidad de cupo debe ser menor o igual a 30")
    private Integer cupos;

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

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
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
