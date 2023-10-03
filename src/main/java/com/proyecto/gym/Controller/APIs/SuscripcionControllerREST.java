package com.proyecto.gym.Controller.APIs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.gym.models.entity.Suscripcion;
import com.proyecto.gym.models.service.ClienteServiceImpl;
import com.proyecto.gym.models.service.SuscripcionServiceImpl;
import com.proyecto.gym.models.service.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/v1/suscripciones")
public class SuscripcionControllerREST {

    @Autowired
    private SuscripcionServiceImpl suscripcionServiceImpl;

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/lista")
    public List<Suscripcion> listarTodos() {
        return suscripcionServiceImpl.listarTodos();
    }

    @PostMapping("/guardar")
    public Suscripcion guardarSuscripcion(@RequestBody Suscripcion suscripcion) {

        usuarioServiceImpl.guardar(suscripcion.getCliente().getUsuario());

        clienteServiceImpl.guardarCliente(suscripcion.getCliente());
        
        return suscripcionServiceImpl.guardarSuscripcion(suscripcion);
    }

    @GetMapping("/buscar/{id}")
    public Suscripcion buscarPorId(@PathVariable("id") Long id) {
        return suscripcionServiceImpl.buscarPorId(id);
    }

    @PostMapping("/eliminar/{id}")
    public void eliminarSuscripcion(@PathVariable("id") Long idSuscripcion) {
        suscripcionServiceImpl.eliminarSuscripcion(idSuscripcion);
    }

    @PostMapping("/actualizar/{id}")
    public Suscripcion actualizarSuscripcion(@RequestBody Suscripcion suscripcion, @PathVariable("id") Long idSuscripcion) {
        return suscripcionServiceImpl.actualizarSuscripcion(suscripcion, idSuscripcion);
    }
}
