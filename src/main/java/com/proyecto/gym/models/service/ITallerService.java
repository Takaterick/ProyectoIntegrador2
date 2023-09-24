package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Taller;

public interface ITallerService {
    //metodo que lista
    public List<Taller> listarTalleres();
    //metodo que guarda
    public Taller guardarTaller(Taller taller);
    //metodo que busca por id
    public Taller buscarPorId(Long id);
    //metodo que elimina
    public void eliminarTaller(Long id);
    //metodo que actualiza
    public Taller actualizarTaller(Taller taller, Long id);
    //metodo que elimina
    public Boolean eliminarDos(Long id);
    //metodo que verifica si existe
    public Taller buscarPorNombre(String nom_taller);
}
