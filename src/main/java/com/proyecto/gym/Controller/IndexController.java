package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    
    @GetMapping("/inicio")
    public String index(){
        return "view/index";
    }

    @GetMapping("/registro")
    public String registro(){
        return "view/registro";
    }

    @GetMapping("/login")
    public String login(){
        return "view/login";
    }
}
