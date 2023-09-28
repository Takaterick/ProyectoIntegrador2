package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @GetMapping({"/","","/index","/incio"})
    public String index(){
        return "view/administrador/empleado/index";
    }
}
