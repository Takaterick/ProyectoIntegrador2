package com.proyecto.gym.Controller.APIs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gym.models.entity.Visita;
import com.proyecto.gym.models.service.IVisitaService;

@RestController
@RequestMapping("/api/v1/visitas")
public class VisitaControllerREST {
    
    @Autowired
    private IVisitaService visitaService;

    @GetMapping("/lista")
    public List<Visita> listarVisitas() {
        return visitaService.listarTodos();
    }

    @PostMapping("/guardar")
    public Visita guardarVisita(@RequestBody Visita visita) {
        return visitaService.guardarVisita(visita);
    }

    @GetMapping("/boucher")
    public ResponseEntity<byte[]> generarReporte(@RequestParam Long idVis){
        return visitaService.exportarBoucher(idVis);
    }
}
