package com.proyecto.gym.Controller.APIs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.service.IClienteService;
import com.proyecto.gym.models.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteControllerREST {
    
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    public String index(){
        return "view/administrador/cliente/index";
    }

    @GetMapping("/lista")
    public List<Cliente> listarClientes(){
        return clienteService.listarTodos();
    }

    @PostMapping("/guardar")
    public Cliente guardarClientes(@RequestBody Cliente cliente){

        /* cliente.getUsuario().setEstado(1);
        cliente.getUsuario().setBloqueo(1);
        cliente.getUsuario().setDesc_bloq("Pago cuenta pendiente");
        
        Usuario usuario = usuarioService.guardar(cliente.getUsuario());

        cliente.setUsuario(usuario); */
        
        return clienteService.guardarCliente(cliente);
    }
}
