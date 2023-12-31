package com.proyecto.gym.Controller.APIs;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        suscripcion.getCliente().getUsuario().setBloqueo(1);
        suscripcion.getCliente().getUsuario().setEstado(1);
        suscripcion.getCliente().getUsuario().setDesc_bloq("Pago pendiente");

        usuarioServiceImpl.guardar(suscripcion.getCliente().getUsuario());

        clienteServiceImpl.guardarCliente(suscripcion.getCliente());
        
        return suscripcionServiceImpl.guardarSuscripcion(suscripcion);
    }

    @GetMapping("/buscar/{id}")
    public Suscripcion buscarPorId(@PathVariable("id") Long id) {
        return suscripcionServiceImpl.buscarPorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarSuscripcion(@PathVariable("id") Long idSuscripcion) {

        Long idCliente = suscripcionServiceImpl.buscarPorId(idSuscripcion).getCliente().getId_cli();
        Long idUsuario = suscripcionServiceImpl.buscarPorId(idSuscripcion).getCliente().getUsuario().getId();

        suscripcionServiceImpl.eliminarSuscripcion(idSuscripcion);


        clienteServiceImpl.eliminarCliente(idCliente);
        
        usuarioServiceImpl.eliminar(idUsuario);
    }

    @PutMapping("/actualizar/{id}")
    public Suscripcion actualizarSuscripcion(@RequestBody Suscripcion suscripcion, @PathVariable("id") Long idSuscripcion) {
        return suscripcionServiceImpl.actualizarSuscripcion(suscripcion, idSuscripcion);
    }

    @GetMapping("/boucher")
    public ResponseEntity<byte[]> generarReporte(@RequestParam Long idSus){
        return suscripcionServiceImpl.exportarBoucher(idSus);
    }
}
