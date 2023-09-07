package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Taller;
import com.proyecto.gym.models.repository.TallerRepository;

@Service
public class TallerServiceImpl implements ITallerService {

    @Autowired
    private TallerRepository tallerRepository;

    @Override
    public List<Taller> listarTalleres() {
        return (List<Taller>) tallerRepository.findAll();
    }

    @Override
    public Taller guardarTaller(Taller taller) {
        return tallerRepository.save(taller);
    }

    @Override
    public Taller buscarPorId(Long id) {
        return tallerRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarTaller(Long id) {
        tallerRepository.deleteById(id);
    }

    @Override
    public Taller actualizarTaller(Taller taller, Long id) {
        //buscar el taller con el id recibido
        Taller tallerModificado = tallerRepository.findById(id).orElse(null);

        tallerModificado.setNombre_taller(taller.getNom_taller());

        tallerRepository.save(tallerModificado);

        return tallerModificado;
    }

    @Override
    public Boolean eliminarDos(Long id) {
        try {
            tallerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
