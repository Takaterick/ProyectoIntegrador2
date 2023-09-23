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

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.entity.Mensaje;
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
    public Mensaje guardarCliente(@Valid @RequestBody Cliente cliente, BindingResult result){
        if(result.hasErrors()){
            Mensaje mensaje = new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
            return mensaje;
        }
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
    @ResponseBody
    public void eliminarCliente(@PathVariable("id") Long id){
        clienteService.eliminarCliente(id);
    }
}
