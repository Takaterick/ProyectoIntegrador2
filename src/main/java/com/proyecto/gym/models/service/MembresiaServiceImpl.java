package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Membresia;
import com.proyecto.gym.models.repository.MembresiaRepository;

@Service
public class MembresiaServiceImpl implements IMembresiaService{

    @Autowired
    private MembresiaRepository membresiaRepository;

    //1
    @Override
    public List<Membresia> listarTodos() {
        return (List<Membresia>)membresiaRepository.findAll();
    }

    //3
    @Override
    public Membresia guardar(Membresia membresia) {
        return membresiaRepository.save(membresia);
    }

    //2
    @Override
    public Membresia buscarPorId(Long Id) {
        return membresiaRepository.findById(Id).orElse(null);
    }

    //4
    @Override
    public void eliminar(Long Id) {
        membresiaRepository.deleteById(Id);
    }

    @Override
    public Membresia actualizarPorId(Membresia membresia, Long Id) {
        Membresia membresia2 = membresiaRepository.findById(Id).orElse(null);

        membresia2.setNom_sus(membresia.getNom_sus());
        membresia2.setPrecio_sus(membresia.getPrecio_sus());

        membresiaRepository.save(membresia2);

        return membresia2;
    }

    @Override
    public Boolean eliminarDos(Long Id) {
        try {
            membresiaRepository.deleteById(Id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
