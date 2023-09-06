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

import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.service.IUsuarioService;

@Controller

//Hacemos un mapeo general para llamar a todos los metodos involucrados en la clase
@RequestMapping("/view/administrador")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;


    //listar
    @GetMapping("/")
    public String listarUsuarios(Model model){
        List<Usuario> listadoUsuarios = usuarioService.listarTodos(); 

        Usuario user = new Usuario();
        model.addAttribute("usuario", user);
        model.addAttribute("usuarios", listadoUsuarios);
        return "/view/administrador/inicio";
    }

    //Guardar
    @PostMapping("/guardar")
    public String guardarUsuarios(@ModelAttribute Usuario usuario){
        usuarioService.guardar(usuario);


        System.out.println("El usuario se guardo con exito");
        return "redirect:/view/administrador/";
    }

    //editar
    /* @PostMapping("/editar/")
    public String editarUsuarios(@ModelAttribute Usuario usuario){
        usuarioService.guardar(usuario);


        System.out.println("El usuario se guardo con exito");
        return "redirect:/view/administrador/";
    } */


    //eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuarios(@PathVariable("id") Long idUsuario){

        usuarioService.eliminar(idUsuario);
        System.out.println("El usuario se eliminó con exito");
        return "redirect:/view/administrador/";
    }
}
