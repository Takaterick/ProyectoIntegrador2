package com.proyecto.gym.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @GetMapping("/dashboard")
    public String dashboard(){
        return "view/administrador/inicio";
    }
}
