package com.proyecto.gym.Controller.APIs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gym.models.entity.InscripcionTaller;
import com.proyecto.gym.models.service.InscripTallerImpl;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionTallerControllerREST {
    
    @Autowired
    private InscripTallerImpl inscripTallerImpl;

    @GetMapping("/lista")
    public List<InscripcionTaller> listarTodos() {
        return inscripTallerImpl.listarTodos();
    }

    @PostMapping("guardar")
    public InscripcionTaller guardarInscripcion(@RequestBody InscripcionTaller inscripcion) {
        return inscripTallerImpl.guardarInscripcion(inscripcion);
    }

    @GetMapping("/buscar/{id}")
    public InscripcionTaller buscarPorId(@PathVariable("id") Long id) {
        return inscripTallerImpl.buscarPorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarInscripcion(@PathVariable("id") Long id) {
        inscripTallerImpl.eliminarInscripcion(id);
    }

    @PutMapping("/actualizar/{id}")
    public InscripcionTaller actualizarInscripcion(@PathVariable("id") Long id, @RequestBody InscripcionTaller inscripcion) {
        return inscripTallerImpl.actualizarInscripcion(inscripcion, id);
    }
}
