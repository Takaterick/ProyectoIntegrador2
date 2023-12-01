package com.proyecto.gym.models.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Alquiler;
import com.proyecto.gym.models.entity.Equipamiento;
import com.proyecto.gym.models.repository.AlquilerRepository;
import com.proyecto.gym.models.repository.EquipamientoRepository;

@Service
public class AlquilerServiceImpl implements IAlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private EquipamientoRepository equipamientoRepository;

    @Override
    public List<Alquiler> listarTodos() {
        return (List<Alquiler>) alquilerRepository.findAll();
    }

    @Override
    public Alquiler guardarAlquiler(Alquiler alquiler) {
        /* reducir el stock del equipamiento menos la cantidad prestada */
        Equipamiento equipamiento = equipamientoRepository.findById(alquiler.getEquipamiento().getIdEquipamiento())
                .orElse(null);
        equipamiento.setStockEquipamiento(equipamiento.getStockEquipamiento() - alquiler.getCantidadAlquiler());
        equipamientoRepository.save(equipamiento);
        return alquilerRepository.save(alquiler);
    }

    @Override
    public Alquiler buscarPorId(Long id) {
        return alquilerRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarAlquiler(Long id) {
        alquilerRepository.deleteById(id);
    }

    @Override
    public Alquiler actualizarAlquiler(Alquiler alquiler, Long id) {
        Alquiler alquilerActual = alquilerRepository.findById(id).orElse(null);
        alquilerActual.setCantidadAlquiler(alquiler.getCantidadAlquiler());
        alquilerActual.setDiasAlquiler(alquiler.getDiasAlquiler());
        alquilerActual.setEstadoAlquiler(alquiler.getEstadoAlquiler());
        alquilerActual.setFechaAlquiler(alquiler.getFechaAlquiler());
        alquilerActual.setFechaEntrega(alquiler.getFechaEntrega());
        alquilerActual.setCliente(alquiler.getCliente());
        alquilerActual.setEquipamiento(alquiler.getEquipamiento());
        return alquilerRepository.save(alquilerActual);
    }

    @Override
    public Alquiler devolverAlquiler(Long id) {
        Alquiler alquilerActual = alquilerRepository.findById(id).orElse(null);
        alquilerActual.setEstadoAlquiler("Devuelto");

        /* aumentar el stock del equipamiento menos la cantidad prestada */
        Equipamiento equipamiento = equipamientoRepository.findById(alquilerActual.getEquipamiento().getIdEquipamiento())
                .orElse(null);
        equipamiento.setStockEquipamiento(equipamiento.getStockEquipamiento() + alquilerActual.getCantidadAlquiler());
        equipamientoRepository.save(equipamiento);
        return alquilerRepository.save(alquilerActual);
    }

    @Override
    public List<Alquiler> buscarPorCliente(Long id) {
        return alquilerRepository.listAlquilerCliente(id);
    }

    
}