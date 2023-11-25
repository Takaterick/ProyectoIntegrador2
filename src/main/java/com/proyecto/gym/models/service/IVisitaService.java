package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.proyecto.gym.models.entity.Visita;

public interface IVisitaService {
    public List<Visita> listarTodos();
    public Visita guardarVisita(Visita visita);
    public Visita buscarPorId(Long Id);
    public void eliminarVisita(Long Id);
    public Visita actualizarVisita(Visita visita, Long Id);
    public ResponseEntity<byte[]> exportarBoucher(Long Id);
    public Integer visitasTotales();
}
