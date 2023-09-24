package com.proyecto.gym.Controller.REST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.service.IEmpleadoService;
import com.proyecto.gym.models.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoControllerREST {
    
    @Autowired
    private IEmpleadoService empleadoService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    public String index(){
        return "view/administrador/empleado/index";
    }

    @GetMapping("/lista")
    public List<Empleado> listarEmpleados(){
        return empleadoService.listarTodos();
    }

    @PostMapping("/guardar")
    public Empleado guardarEmpleados(@RequestBody Empleado empleado){

        empleado.getUsuario().setEstado(1);
        empleado.getUsuario().setBloqueo(1);
        empleado.getUsuario().setDesc_bloq("Pago pendiente");
        
        Usuario usuario = usuarioService.guardar(empleado.getUsuario());

        empleado.setUsuario(usuario);
        
        return empleadoService.guardarEmpleado(empleado);
    }
}

