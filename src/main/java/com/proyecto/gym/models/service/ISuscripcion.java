package com.proyecto.gym.models.service;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.proyecto.gym.models.entity.Suscripcion;

public interface ISuscripcion {
    public List<Suscripcion> listarTodos();
    public Suscripcion guardarSuscripcion(Suscripcion suscripcion);
    public Suscripcion buscarPorId(Long Id);
    public void eliminarSuscripcion(Long Id);
    public Suscripcion actualizarSuscripcion(Suscripcion suscripcion, Long Id);
    public Suscripcion buscarPorCliente(Long Id);
    public ResponseEntity<byte[]> exportarBoucher(Long Id);
    public Double ventaDiaria();
    public Double ventaMensual();
    public Integer suscripcionesTotales();
}
