package com.proyecto.gym.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.gym.models.entity.Rol;
import com.proyecto.gym.models.service.RolServiceImpl;

@Controller
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private RolServiceImpl rolServiceImpl;

    @GetMapping("/")
    public String index(){
        return "view/administrador/rol/index";
    }

    @GetMapping("/lista")
    @ResponseBody
    public List<Rol> listarRoles(){
        return rolServiceImpl.listarRoles();
    }

    @PostMapping("/guardar")
    @ResponseBody
    public Rol guardarRol(@RequestBody Rol rol){
        return rolServiceImpl.guardarRol(rol);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public void eliminarRol(@PathVariable("id") Long id){
        rolServiceImpl.eliminarRol(id);
    }


}
