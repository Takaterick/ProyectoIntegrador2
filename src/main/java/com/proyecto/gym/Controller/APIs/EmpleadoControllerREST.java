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

import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.service.IEmpleadoService;
import com.proyecto.gym.models.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoControllerREST {
    
    @Autowired
    private IEmpleadoService empleadoService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/lista")
    public List<Empleado> listarEmpleados(){
        return empleadoService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Empleado buscarEmpleado(@PathVariable("id") Long id){
        return empleadoService.buscarPorId(id);
    }

    @PostMapping("/guardar")
    public Empleado guardarEmpleados(@RequestBody Empleado empleado){

        usuarioService.guardar(empleado.getUsuario());
        
        return empleadoService.guardarEmpleado(empleado);
    }

    @PutMapping("/actualizar/{id}")
    public Empleado actualizarEmpleados(@RequestBody Empleado empleado, @PathVariable("id") Long idEmpleado){
        return empleadoService.actualizarEmpleado(empleado, idEmpleado);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarEmpleados(@PathVariable("id") Long idEmpleado){
        Long id = empleadoService.buscarPorId(idEmpleado).getUsuario().getId();

        empleadoService.eliminarEmpleado(idEmpleado);

        usuarioService.eliminar(id);
    }
}

