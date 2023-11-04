package com.proyecto.gym.Controller.APIs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gym.models.entity.Asistencia;
import com.proyecto.gym.models.service.IAsistenciaService;

@RestController
@RequestMapping("/api/v1/asistencias")
public class AsistenciaControllerREST {
    
    @Autowired
    private IAsistenciaService asistenciaService;

    @GetMapping("/lista")
    public List<Asistencia> listarAsistencia() {
        return asistenciaService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Asistencia buscarAsistencia(@PathVariable("id") Long id) {
        return asistenciaService.buscarPorId(id);
    }

    @GetMapping("/buscar/taller/{id}")
    public List<Asistencia> buscarPorTaller(@PathVariable("id") Long id) {
        return asistenciaService.buscarPorTaller(id);
    }

    @PostMapping("/guardar")
    public Asistencia guardarAsistencia(@RequestBody Asistencia asistencia) {
        return asistenciaService.guardarAsistencia(asistencia);
    }

    @PutMapping("/actualizar/{id}")
    public Asistencia actualizarAsistencia(@RequestBody Asistencia asistencia, @PathVariable("id") Long id) {
        return asistenciaService.actualizarAsistencia(asistencia, id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarAsistencia(@PathVariable("id") Long id) {
        asistenciaService.eliminarAsistencia(id);
    }

    @GetMapping("/marcar/{id}/{asistencia}")
    public String marcar(@PathVariable("id") Long id, @PathVariable("asistencia") boolean asistencia) {
        Asistencia asis = asistenciaService.buscarPorId(id);
        String mensaje = "";

        if(asistencia) {
            asis.setAsistencia("Asistió");
            mensaje = "Asistió";
        } else {
            asis.setAsistencia("No asistió");
            mensaje = "No asistió";
        }
        asistenciaService.marcarAsistencia(asis);
        return mensaje;
    }
}
