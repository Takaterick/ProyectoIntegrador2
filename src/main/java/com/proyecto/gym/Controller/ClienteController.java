package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @GetMapping({"/","","/index","/inicio"})
    public String index(){
        return "/view/cliente/inicio";
    }

    @GetMapping("/perfil")
    public String perfil(){
        return "/view/cliente/perfil";
    }

    @GetMapping("/rutinas")
    public String rutinas(){
        return "/view/cliente/rutinas";
    }

    @GetMapping("/talleres")
    public String talleres(){
        return "/view/cliente/talleres";
    }

    @GetMapping("/dietas")
    public String dietas(){
        return "/view/cliente/dietas";
    }

    @GetMapping("/ejercicios")
    public String ejercicios(){
        return "/view/cliente/ejercicios";
    }

    @GetMapping("/horarios")
    public String horarios(){
        return "/view/cliente/horarios";
    }

    @GetMapping("/pagos")
    public String pagos(){
        return "/view/cliente/pagos";
    }
}