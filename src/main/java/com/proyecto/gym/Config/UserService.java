package com.proyecto.gym.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.entity.Empleado;
import com.proyecto.gym.models.repository.ClienteRepository;
import com.proyecto.gym.models.repository.EmpleadoRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsuario_Usuario(username);
        Empleado empleado = empleadoRepository.findByUsuario_Usuario(username);



        if (cliente != null) {
            return User.builder()
                    .username(cliente.getUsuario().getUsuario())
                    .password(cliente.getUsuario().getContrasenia())
                    .roles("CLIENTE")
                    .accountLocked(cliente.getUsuario().getBloqueo() == 1 ? true : false)
                    .build();
        } else if (empleado != null) {
            return User.builder()
                    .username(empleado.getUsuario().getUsuario())
                    .password(empleado.getUsuario().getContrasenia())
                    .roles(empleado.getRol().getNom_rol())
                    .accountLocked(empleado.getUsuario().getBloqueo() == 1 ? true : false)
                    .build();
        } else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

}
