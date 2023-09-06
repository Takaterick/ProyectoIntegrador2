package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    //Injectamos la clase repository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodos() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long Id) {
        return usuarioRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminar(Long Id) {
        usuarioRepository.deleteById(Id);
    }
    
}
