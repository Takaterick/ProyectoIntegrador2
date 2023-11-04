package com.proyecto.gym.Controller.APIs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gym.models.entity.InscripcionTaller;
import com.proyecto.gym.models.entity.Mensaje;
import com.proyecto.gym.models.service.InscripTallerImpl;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionTallerControllerREST {
    
    @Autowired
    private InscripTallerImpl inscripTallerImpl;

    @GetMapping("/lista")
    public List<InscripcionTaller> listarTodos() {
        return inscripTallerImpl.listarTodos();
    }

    @PostMapping("/guardar")
    @ResponseBody
    public Mensaje guardarInscripcion(@Valid @RequestBody InscripcionTaller inscripcion, BindingResult result) {
        if(result.hasErrors()){
            Mensaje mensaje = new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
            return mensaje;
        }

        inscripTallerImpl.guardarInscripcion(inscripcion);
        return new Mensaje("¡La inscripción del taller se guardó con éxito!", "success");
    }

    @GetMapping("/buscar/{id}")
    public InscripcionTaller buscarPorId(@PathVariable("id") Long id) {
        return inscripTallerImpl.buscarPorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarInscripcion(@PathVariable("id") Long id) {
        inscripTallerImpl.eliminarInscripcion(id);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Mensaje actualizarInscripcion(@Valid @RequestBody InscripcionTaller inscripcion, BindingResult result, @PathVariable("id") Long id) {
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        
        inscripTallerImpl.actualizarInscripcion(inscripcion, id);
        return new Mensaje("¡La inscripción del taller se actualizó con éxito!", "success");
    }
}
