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

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.service.ClienteServiceImpl;

@Controller
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteServiceImpl clienteService;

    @GetMapping("/")
    public String index(){
        return "/view/administrador/cliente/index";
    }

    @GetMapping("/lista")
    @ResponseBody //devolver la respuesta dentro del cuerpo de la página html
    public List<Cliente> listarClientes(){
        return clienteService.listarClientes();
    }

    //metodo para buscar
    @GetMapping("/buscar/{id}")
    @ResponseBody
    public Cliente buscarCliente(@PathVariable("id") Long id){
        return clienteService.buscarPorId(id);
    }
    
    //metodo para guardar
    @PostMapping("/guardar")
    @ResponseBody
    public Cliente guardarCliente(@RequestBody Cliente cliente){
        return clienteService.guardarCliente(cliente);
    }

    //metodo para actualizar
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Cliente actualizarCliente(@RequestBody Cliente cliente, @PathVariable("id") Long id){
        return clienteService.actualizarCliente(cliente, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminarCliente(@PathVariable("id") Long id){
        boolean ok = clienteService.eliminarDos(id);

        if(ok){
            return "El cliente "+id+" se eliminó con exito!";
        }else{
            return "El cliente "+id+" no se eliminó";
        }
    }
}
