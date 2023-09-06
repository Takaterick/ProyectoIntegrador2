package com.proyecto.gym.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.gym.models.entity.Membresia;
import com.proyecto.gym.models.entity.Mensaje;
import com.proyecto.gym.models.service.IMembresiaService;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/api/v1/membresias")
public class MembresiaControllerApi {

    @Autowired
    private IMembresiaService membresiaService;

    @GetMapping("/")
    public String index(){
        return "view/administrador/membresia/index";
    }

    //Listar
    @GetMapping("/lista")
    @ResponseBody
    public List<Membresia> listarMembresia(){
        return membresiaService.listarTodos();
    }

    //Guardar
    @PostMapping("/guardar")
    @ResponseBody
    public Mensaje guardarMembresia(@Valid @RequestBody Membresia membresia, BindingResult result){
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        membresiaService.guardar(membresia);
        return new Mensaje("La membresia se guardó con exito!", "success");
        
    }

    //BuscarPorId
    @GetMapping("/buscar/{id}")
    @ResponseBody
    public Membresia buscarMembresia(@PathVariable("id") Long id){
        return membresiaService.buscarPorId(id);
    }

    //Actualizar
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Membresia actualizarMembresia(@RequestBody Membresia membresia, @PathVariable("id") Long id){
        return membresiaService.actualizarPorId(membresia, id);
    }

    //Eliminar
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminarMembresia(@PathVariable("id") Long id){
        boolean ok = membresiaService.eliminarDos(id);

        if(ok){
            return "La membresia "+id+" se eliminó con exito!";
        }else{
            return "La membresia "+id+" no se eliminó";
        }
    }
    
}
