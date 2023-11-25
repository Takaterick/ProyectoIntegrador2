package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Equipamiento;
import com.proyecto.gym.models.repository.EquipamientoRepository;

@Service
public class EquipamientoServiceImpl implements IEquipamientoService {

    @Autowired
    private EquipamientoRepository equipamientoRepository;

    @Override
    public List<Equipamiento> listarTodos() {
        return (List<Equipamiento>) equipamientoRepository.findAll(); 
    }

    @Override
    public Equipamiento guardarEquipamiento(Equipamiento equipamiento) {
        return equipamientoRepository.save(equipamiento);
    }

    @Override
    public Equipamiento buscarPorId(Long id) {
        return equipamientoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarEquipamiento(Long id) {
        equipamientoRepository.deleteById(id);
    }

    @Override
    public Equipamiento actualizarEquipamiento(Equipamiento equipamiento, Long id) {
        Equipamiento equipamientoActual = equipamientoRepository.findById(id).orElse(null);
        equipamientoActual.setNombreEquipamiento(equipamiento.getNombreEquipamiento());
        equipamientoActual.setStockEquipamiento(equipamiento.getStockEquipamiento());
        return equipamientoRepository.save(equipamientoActual);
    }
    
}
