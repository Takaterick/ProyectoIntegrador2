package com.proyecto.gym.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.service.IUsuarioService;

@Controller

//Hacemos un mapeo general para llamar a todos los metodos involucrados en la clase
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;


    //listar
    @GetMapping("/lista")
    @ResponseBody
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarTodos();
    }

    //Guardar
    @PostMapping("/guardar")
    @ResponseBody
    public Usuario guardarUsuarios(@RequestBody Usuario usuario){
        return usuarioService.guardar(usuario);
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
