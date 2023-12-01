package com.proyecto.gym.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.service.IClienteService;
import com.proyecto.gym.models.service.IEmpleadoService;
import com.proyecto.gym.models.service.ISuscripcion;
import com.proyecto.gym.models.service.IVisitaService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IEmpleadoService empleadoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ISuscripcion suscripcionService;

    @Autowired
    private IVisitaService visitaService;

    @GetMapping("/dashboard")
    public String dashboard(){
        return "/view/administrador/inicio";
    }
    
    @GetMapping({"/","","/index"})
    public String index(Authentication auth, Model model){
        //obtener el id del usuario logueado
        Empleado empleado = empleadoService.buscarPorUsuario(auth.getName());
        Double diaria = suscripcionService.ventaDiaria();
        Double mensual = suscripcionService.ventaMensual();
        String ventaDiaria, ventaMensual;
        if(diaria == null){
            ventaDiaria = "0.00";
        }else{
            ventaDiaria = String.format("%.2f", suscripcionService.ventaDiaria());
        }

        if(mensual == null){
            ventaMensual = "0.00";
        }else{
            ventaMensual = String.format("%.2f", suscripcionService.ventaMensual());
        }

        model.addAttribute("empleado", empleado);
        model.addAttribute("clientes", clienteService.listarClientesRecientes());
        model.addAttribute("ventaDiaria", ventaDiaria);
        model.addAttribute("ventaMensual", ventaMensual);
        model.addAttribute("visitas", visitaService.visitasTotales());
        model.addAttribute("totalSus", suscripcionService.suscripcionesTotales());
        return "/view/administrador/index";
    }

    @GetMapping("/perfil")
    public String perfil(){
        return "/view/administrador/perfil";
    }

    @GetMapping("/clientes")
    public String clientes(){
        return "/view/administrador/cliente/index";
    }

    @GetMapping("/empleados")
    public String empleados(){
        return "/view/administrador/empleado/index";
    }

    @GetMapping("/entrenadores")
    public String entrenador(){
        return "/view/administrador/entrenador/index";
    }

    @GetMapping("/membresias")
    public String membresias(){
        return "/view/administrador/membresia/index";
    }

    @GetMapping("/roles")
    public String rol(){
        return "/view/administrador/rol/index";
    }

    @GetMapping("/suscripcion")
    public String suscripcion(){
        return "/view/administrador/suscripcion/index";
    }

    @GetMapping("/talleres")
    public String talleres(){
        return "/view/administrador/taller/index";
    }
    
    @GetMapping("/visitas")
    public String visitas(){
        return "/view/administrador/visita/index";
    }

    @GetMapping("/equipamiento")
    public String equipamiento(){
        return "/view/administrador/equipamiento/index";
    }

    @GetMapping("/alquiler")
    public String alquiler(){
        return "/view/administrador/alquiler/index";
    }
}
