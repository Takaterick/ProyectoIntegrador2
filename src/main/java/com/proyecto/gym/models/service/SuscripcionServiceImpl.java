package com.proyecto.gym.models.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Suscripcion;
import com.proyecto.gym.models.repository.SuscripcionRepository;


@Service
public class SuscripcionServiceImpl implements ISuscripcion{

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Override
    public List<Suscripcion> listarTodos() {
        return (List<Suscripcion>) suscripcionRepository.findAll();
    }

    @Override
    public Suscripcion guardarSuscripcion(Suscripcion suscripcion) {

        suscripcion.setFechaInicio(new Date());
        suscripcion.setFechaFin(new Date());
        suscripcion.setEstado("Pendiente");

        return suscripcionRepository.save(suscripcion);
    }

    @Override
    public Suscripcion buscarPorId(Long Id) {
        return suscripcionRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminarSuscripcion(Long Id) {
        suscripcionRepository.deleteById(Id);
    }

    @Override
    public Suscripcion actualizarSuscripcion(Suscripcion suscripcion, Long Id) {
        Suscripcion suscripcionActual = suscripcionRepository.findById(Id).orElse(null);

        suscripcionActual.setFechaInicio(suscripcion.getFechaInicio());
        suscripcionActual.setFechaFin(suscripcion.getFechaFin());

        if (suscripcionActual.getFechaFin().after(new Date())) {
            suscripcionActual.setEstado("Pagado");
        } else {
            suscripcionActual.setEstado("Vencido");
        }
        return suscripcionRepository.save(suscripcionActual);
    }

    @Override
    public Suscripcion buscarPorCliente(Long Id) {
        return suscripcionRepository.findByCliente_Id(Id);
    }
    
}
