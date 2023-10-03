package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suscripciones")
public class SuscripcionController {

    @RequestMapping({ "/", "", "/index", "/inicio" })
    public String index() {
        return "view/administrador/suscripcion/index";
    }
}
