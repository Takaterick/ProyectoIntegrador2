package com.proyecto.gym.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.gym.models.entity.Taller;
import com.proyecto.gym.models.service.TallerServiceImpl;

@Controller
@RequestMapping("/api/v1/talleres")
public class TallerController {

    @Autowired
    private TallerServiceImpl tallerService;

    @GetMapping("/")
    public String index(){
        return "/view/administrador/taller/index";
    }

    @GetMapping("/lista")
    @ResponseBody
    public List<Taller> listarTalleres(){
        return tallerService.listarTalleres();
    }

    //metodo para buscar
    @GetMapping("/buscar/{id}")
    @ResponseBody
    public Taller buscarTaller(@PathVariable("id") Long id){
        return tallerService.buscarPorId(id);
    }

    @PostMapping("/guardar")
    @ResponseBody
    public Taller guardarTaller(@RequestBody Taller taller){
        return tallerService.guardarTaller(taller);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Taller actualizarTaller(@RequestBody Taller taller, @PathVariable("id") Long id){
        return tallerService.actualizarTaller(taller, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminarTaller(@PathVariable("id") Long id){
        boolean ok = tallerService.eliminarDos(id);

        if(ok){
            return "El taller "+id+" se eliminó con exito!";
        }else{
            return "El taller "+id+" no se eliminó";
        }
    }
}
