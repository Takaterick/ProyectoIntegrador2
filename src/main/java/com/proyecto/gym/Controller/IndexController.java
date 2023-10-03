package com.proyecto.gym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping("/")
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
