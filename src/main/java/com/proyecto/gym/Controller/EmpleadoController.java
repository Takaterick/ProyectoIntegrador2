package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entrenador")
public class EmpleadoController {

    @GetMapping({"/","","/index","/inicio"})
    public String index(){
        return "view/entrenador/inicio";
    }
}
