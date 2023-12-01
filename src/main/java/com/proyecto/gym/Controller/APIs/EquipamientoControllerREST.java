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

import com.proyecto.gym.models.entity.Equipamiento;
import com.proyecto.gym.models.service.IEquipamientoService;

@RestController
@RequestMapping("/api/v1/equipamientos")
public class EquipamientoControllerREST {

    @Autowired
    private IEquipamientoService equipamientoService;

    @GetMapping("/lista")
    public List<Equipamiento> listarEquipamientos(){
        return equipamientoService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Equipamiento buscarEquipamiento(@PathVariable("id") Long id){
        return equipamientoService.buscarPorId(id);
    }

    @PostMapping("/guardar")
    public Equipamiento guardarEquipamiento(@RequestBody Equipamiento equipamiento){
        return equipamientoService.guardarEquipamiento(equipamiento);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarEquipamiento(@PathVariable("id") Long id){
        equipamientoService.eliminarEquipamiento(id);
    }

    @PutMapping("/actualizar/{id}")
    public Equipamiento actualizarEquipamiento(@RequestBody Equipamiento equipamiento, @PathVariable("id") Long id){
        return equipamientoService.actualizarEquipamiento(equipamiento, id);
    }
}
