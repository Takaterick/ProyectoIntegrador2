package com.proyecto.gym.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.service.IClienteService;
import com.proyecto.gym.models.service.ISuscripcion;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ISuscripcion suscripcionService;

    @GetMapping({"/","","/index","/inicio"})
    public String index(Authentication auth, Model model){
        Cliente cliente = clienteService.buscarPorUsuario(auth.getName());
        model.addAttribute("cliente", cliente);
        model.addAttribute("suscripcion", suscripcionService.buscarPorId(cliente.getId_cli()));
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