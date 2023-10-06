package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @GetMapping({"/","","/index","/inicio"})
    public String index(){
        return "/view/administrador/cliente/index";
    }

    @GetMapping("/home")
    public String inicio(){
        return "/view/cliente/inicio";
    }
}