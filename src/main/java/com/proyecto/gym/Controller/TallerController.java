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
    public Mensaje guardarTaller(@Valid @RequestBody Taller taller, BindingResult result){
        if(tallerService.buscarPorNombre(taller.getNomTaller()) != null){
            return new Mensaje("El taller "+taller.getNomTaller()+" ya existe!", "error");
        }
        if(result.hasErrors()){
            Mensaje mensaje = new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
            return mensaje;
        }
        tallerService.guardarTaller(taller);
        return new Mensaje("El taller se guardó con exito!", "success");
    }

    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Mensaje actualizarTaller(@Valid @RequestBody Taller taller, BindingResult result, @PathVariable("id") Long id){
        //Taller tallerEcontrado = tallerService.buscarPorNombre(taller.getNomTaller());
        if(tallerService.buscarPorNombre(taller.getNomTaller()) != null && tallerService.buscarPorNombre(taller.getNomTaller()).getId_taller() != taller.getId_taller().longValue()){
            return new Mensaje("El taller "+taller.getNomTaller()+" ya existe!", "error");
        }
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        tallerService.actualizarTaller(taller, id);
        return new Mensaje("El taller "+ taller.getNomTaller()+" se actualizó con exito!", "success");
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public void eliminarTaller(@PathVariable("id") Long id){
        tallerService.eliminarTaller(id);
    }
}
