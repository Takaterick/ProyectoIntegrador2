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

import com.proyecto.gym.models.entity.Alquiler;
import com.proyecto.gym.models.service.IAlquilerService;

@RestController
@RequestMapping("/api/v1/alquileres")
public class AlquilerControllerREST {
    
    @Autowired
    private IAlquilerService alquilerService;

    @GetMapping("/lista")
    public List<Alquiler> listarAlquileres() {
        return alquilerService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Alquiler buscarAlquiler(@PathVariable("id") Long id) {
        return alquilerService.buscarPorId(id);
    }

    @GetMapping("/buscar/cliente/{id}")
    public List<Alquiler> buscarAlquileresPorCliente(@PathVariable("id") Long id) {
        return alquilerService.buscarPorCliente(id);
    }

    @PostMapping("/guardar")
    public Alquiler guardarAlquiler(@RequestBody Alquiler alquiler) {
        return alquilerService.guardarAlquiler(alquiler);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarAlquiler(@PathVariable("id") Long id) {
        alquilerService.eliminarAlquiler(id);
    }

    @PutMapping("/actualizar/{id}")
    public Alquiler actualizarAlquiler(@RequestBody Alquiler alquiler, @PathVariable("id") Long id) {
        return alquilerService.actualizarAlquiler(alquiler, id);
    }

    @PutMapping("/devolver/{id}")
    public Alquiler devolverAlquiler(@PathVariable("id") Long id) {
        return alquilerService.devolverAlquiler(id);
    }
}
