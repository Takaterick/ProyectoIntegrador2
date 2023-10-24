package com.proyecto.gym.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.service.IEmpleadoService;
import com.proyecto.gym.models.service.ITallerInscripService;

@Controller
@RequestMapping("/entrenador")
public class EmpleadoController {

    @Autowired
    IEmpleadoService empleadoService;

    @Autowired
    ITallerInscripService inscripcionService;

    @GetMapping({"/","","/index","/inicio"})
    public String index(Authentication auth, Model model){
        Empleado empleado = empleadoService.buscarPorUsuario(auth.getName());

        model.addAttribute("empleado", empleado);
        model.addAttribute("talleres", inscripcionService.buscarPorEmpleado(empleado.getIdEmpl()));
        return "view/entrenador/taller";
    }
}
