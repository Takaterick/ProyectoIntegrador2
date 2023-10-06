package com.proyecto.gym.Config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.repository.UsuarioRepository;

@Component
public class FailureRedirect implements AuthenticationFailureHandler {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        UrlPathHelper path = new UrlPathHelper();
        String contextpath = path.getContextPath(request);

        String error = "";
        String op = "";

        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)){
            error = "Verifique bien sus credenciales";
            op = "1";
        }else if(exception.getClass().isAssignableFrom(LockedException.class)){

            Usuario usuario = usuarioRepository.findByUsuario(request.getParameter("username"));

            if(usuario.getBloqueo() == 1){
                error = "Estimado cliente, usted se encuentra bloqueado por el motivo de: "+usuario.getDesc_bloq();
                op = "2";
            }
        }else{
            error = "unknown";
        }

    
        System.out.println(request.getParameter("username"));

        response.sendRedirect(contextpath+"/login?error="+error+"&op="+op);
    }
}
