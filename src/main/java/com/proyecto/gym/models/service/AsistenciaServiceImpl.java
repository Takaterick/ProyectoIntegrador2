package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Asistencia;
import com.proyecto.gym.models.entity.InscripcionTaller;
import com.proyecto.gym.models.repository.AsistenciaRepository;
import com.proyecto.gym.models.repository.InscripcionTallerRepository;

@Service
public class AsistenciaServiceImpl implements IAsistenciaService{

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private InscripcionTallerRepository inscripcionTallerRepository;

    @Override
    public List<Asistencia> listarTodos() {
        return (List<Asistencia>) asistenciaRepository.findAll();
    }

    @Override
    public Asistencia guardarAsistencia(Asistencia asistencia) {
        InscripcionTaller taller = inscripcionTallerRepository.findById(asistencia.getInscripcionTaller().getIdInsTaller()).orElse(null);
        taller.setCupos(taller.getCupos() - 1);

        inscripcionTallerRepository.save(taller);
        
        asistencia.setAsistencia("No asistió");

        return asistenciaRepository.save(asistencia);
    }

    @Override
    public Asistencia buscarPorId(Long id) {
        return asistenciaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarAsistencia(Long id) {
        Asistencia asis = asistenciaRepository.findById(id).orElse(null);
        InscripcionTaller taller = inscripcionTallerRepository.findById(asis.getInscripcionTaller().getIdInsTaller()).orElse(null);
        taller.setCupos(taller.getCupos() + 1);
        inscripcionTallerRepository.save(taller);
        asistenciaRepository.deleteById(id);
    }

    @Override
    public Asistencia actualizarAsistencia(Asistencia asistencia, Long id) {
        Asistencia asistenciaActualizada = asistenciaRepository.findById(id).orElse(null);
        asistenciaActualizada.setAsistencia(asistencia.getAsistencia());
        asistenciaActualizada.setCliente(asistencia.getCliente());
        asistenciaActualizada.setInscripcionTaller(asistencia.getInscripcionTaller());

        asistenciaRepository.save(asistenciaActualizada);
        return asistenciaActualizada;
    }

    @Override
    public List<Asistencia> listarTalleresNoInscritos(Long Id) {
        return asistenciaRepository.listTallersNoInscript(Id);
    }

    @Override
    public List<Asistencia> listarTalleresInscritos(Long Id) {
        return asistenciaRepository.listTallersInscript(Id);
    }

    @Override
    public List<Asistencia> buscarPorTaller(Long Id) {
        return asistenciaRepository.findByInscripcionTaller_idInsTaller(Id);
    }

    @Override
    public Asistencia marcarAsistencia(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }
    
}
