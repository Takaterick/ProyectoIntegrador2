package com.proyecto.gym.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.gym.models.entity.Mensaje;
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
    public Mensaje guardarRol(@Valid @RequestBody Rol rol, BindingResult result){
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        rolServiceImpl.guardarRol(rol);
        return new Mensaje("El rol " + rol.getNom_rol() +" se guardó con exito!", "success");
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public void eliminarRol(@PathVariable("id") Long id){
        rolServiceImpl.eliminarRol(id);
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public Rol buscarRol(@PathVariable("id") Long id){
        return rolServiceImpl.buscarPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Mensaje actualizarRol(@Valid @RequestBody Rol rol, BindingResult result, @PathVariable("id") Long id){
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        rolServiceImpl.actualizarRol(rol, id);
        return new Mensaje("El rol "+ rol.getNom_rol()+" se actualizó con exito!", "success");
    }
}
