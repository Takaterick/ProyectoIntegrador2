package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    
    @GetMapping({"/","","/index","/inicio"})
    public String index(){
        return "/view/administrador/index";
    }

    @GetMapping("/perfil")
    public String perfil(){
        return "/view/administrador/perfil";
    }

    @GetMapping("/clientes")
    public String clientes(){
        return "/view/administrador/cliente/index";
    }

    @GetMapping("/empleados")
    public String empleados(){
        return "/view/administrador/empleado/index";
    }

    @GetMapping("/entrenadores")
    public String entrenador(){
        return "/view/administrador/entrenador/index";
    }

    @GetMapping("/membresias")
    public String membresias(){
        return "/view/administrador/membresia/index";
    }

    @GetMapping("/roles")
    public String rol(){
        return "/view/administrador/rol/index";
    }

    @GetMapping("/suscripcion")
    public String suscripcion(){
        return "/view/administrador/suscripcion/index";
    }

    @GetMapping("/talleres")
    public String talleres(){
        return "/view/administrador/taller/index";
    }
}
