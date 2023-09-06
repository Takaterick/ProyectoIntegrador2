package com.proyecto.gym.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.gym.models.entity.Membresia;
import com.proyecto.gym.models.service.IMembresiaService;

@Controller
@RequestMapping("/administrador/membresia")
public class MembresiaController {

    @Autowired
    private IMembresiaService membresiaService;
    
    //Listar
    @GetMapping("/")
    public String listarMembresia(Model model){
        List<Membresia> listadoMembresias = membresiaService.listarTodos();

        Membresia membresia = new Membresia();
        model.addAttribute("membresia", membresia);
        model.addAttribute("membresias", listadoMembresias);
        return "/view/administrador/membresia/index";
    }

    //guardar
    @PostMapping("/guardar")
    public String guardarMembresia(@ModelAttribute Membresia membresia){
        membresiaService.guardar(membresia);
        return "redirect:/administrador/membresia/";
    }

    //eliminar
    @GetMapping("/editar/{id}")
    public String editarMembresia(@PathVariable("id") Long idMembresia, Model model){

        Membresia membresia = membresiaService.buscarPorId(idMembresia);
        
        model.addAttribute("membresia", membresia);
        return "redirect:/administrador/membresia/";
    }

    //eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarMembresia(@PathVariable("id") Long idMembresia){

        membresiaService.eliminar(idMembresia);
        return "redirect:/administrador/membresia/";
    }

    
}
