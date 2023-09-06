package com.proyecto.gym.models.service;

import java.util.List;
import com.proyecto.gym.models.entity.Usuario;

public interface IUsuarioService {
    
    //creamos las operciones crud a utilizar
    public List<Usuario> listarTodos();
    public void guardar(Usuario usuario);
    public Usuario buscarPorId(Long Id);
    public void eliminar(Long Id);
}
