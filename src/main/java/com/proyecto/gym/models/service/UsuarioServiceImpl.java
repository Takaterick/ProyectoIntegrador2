package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    //Injectamos la clase repository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarTodos() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setBloqueo(1);
        usuario.setEstado(1);
        usuario.setDesc_bloq("Pago pendiente");
        
        usuario.setContrasenia(this.passwordEncoder.encode(usuario.getContrasenia()));
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long Id) {
        return usuarioRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminar(Long Id) {
        usuarioRepository.deleteById(Id);
    }

    @Override
    public Usuario actualizUsuario(Usuario usuario, Long Id) {
        Usuario usuarioModificado = usuarioRepository.findById(Id).orElse(null);
        usuarioModificado.setUsuario(usuario.getUsuario());
        usuarioModificado.setContrasenia(usuario.getContrasenia());
        usuarioModificado.setBloqueo(usuario.getBloqueo());
        usuarioModificado.setEstado(usuario.getEstado());
        usuarioModificado.setDesc_bloq(usuario.getDesc_bloq());
        usuarioRepository.save(usuarioModificado);
        return usuarioModificado;
    }
    
}
