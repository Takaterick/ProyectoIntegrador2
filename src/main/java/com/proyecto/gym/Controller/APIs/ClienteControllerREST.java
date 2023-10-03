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

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.entity.Mensaje;
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

    @GetMapping("/lista")
    public List<Cliente> listarClientes(){
        return clienteService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Cliente buscarCliente(@PathVariable("id") Long id){
        return clienteService.buscarPorId(id);
    }

    //metodo para guardar
    @PostMapping("/guardar")
    @ResponseBody
    public Mensaje guardarCliente(@Valid @RequestBody Cliente cliente, BindingResult result){
        if(result.hasErrors()){
            Mensaje mensaje = new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
            return mensaje;
        }
        cliente.getUsuario().setEstado(1);
        cliente.getUsuario().setBloqueo(1);
        cliente.getUsuario().setDesc_bloq("Pago pendiente");
        
        Usuario usuario = usuarioService.guardar(cliente.getUsuario());

        cliente.setUsuario(usuario);

        clienteService.guardarCliente(cliente);
        return new Mensaje("¡El cliente se guardó con exito!", "success");
    }

    
    //metodo para actualizar
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Mensaje actualizarCliente(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable("id") Long id){
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        clienteService.actualizarCliente(cliente, id);
        return new Mensaje("¡Los datos de "+ cliente.getNom_cli()+" se actualizó con exito!", "success");
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarClientes(@PathVariable("id") Long idCliente){
        Long id = clienteService.buscarPorId(idCliente).getUsuario().getId();

        clienteService.eliminarCliente(idCliente);

        usuarioService.eliminar(id);
    }
}
