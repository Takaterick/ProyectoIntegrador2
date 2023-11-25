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

import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.entity.Mensaje;
import com.proyecto.gym.models.service.IEmpleadoService;
import com.proyecto.gym.models.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoControllerREST {
    
    @Autowired
    private IEmpleadoService empleadoService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/lista")
    public List<Empleado> listarEmpleados(){
        return empleadoService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Empleado buscarEmpleado(@PathVariable("id") Long id){
        return empleadoService.buscarPorId(id);
    }

    //metodo para guardar
    @PostMapping("/guardar")
    @ResponseBody
    public Mensaje guardarEmpleado(@Valid @RequestBody Empleado empleado, BindingResult result){
        if(result.hasErrors()){
            Mensaje mensaje = new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
            return mensaje;
        }

        empleado.getUsuario().setBloqueo(0);
        empleado.getUsuario().setEstado(1);
        
        /* si el rol 1 entonces colocar como descripcion que ninguno porque es administrador */
        if (empleado.getRol().getId_rol() == 1) {
            empleado.getUsuario().setDesc_bloq("Ninguno porque es administrador");
        }else{
            empleado.getUsuario().setDesc_bloq("Registro de empleado");
        }
        
        usuarioService.guardar(empleado.getUsuario());

        empleadoService.guardarEmpleado(empleado);
        return new Mensaje("¡El empleado se guardó con éxito!", "success");
    }

    //metodo para actualizar
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public Mensaje actualizarEmpleado(@Valid @RequestBody Empleado empleado, BindingResult result, @PathVariable("id") Long idEmpleado){
        if(result.hasErrors()){
            return new Mensaje(result.getFieldError().getDefaultMessage(), "warning");
        }
        empleadoService.actualizarEmpleado(empleado, idEmpleado);
        return new Mensaje("¡Los datos de "+ empleado.getNombreEmpl()+" se actualizó con éxito!", "success");
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarEmpleados(@PathVariable("id") Long idEmpleado){
        Long id = empleadoService.buscarPorId(idEmpleado).getUsuario().getId();

        empleadoService.eliminarEmpleado(idEmpleado);

        usuarioService.eliminar(id);
    }
}

