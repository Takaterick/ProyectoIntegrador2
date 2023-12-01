package com.proyecto.gym.Config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

@Component
public class SuccessRedirect implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UrlPathHelper path = new UrlPathHelper();
        String contextpath = path.getContextPath(request);

        /* Usuario usuario = usuarioRepository.findByUsuario(authentication.getName());

        if(usuario.getBloqueo() == 1){
            response.sendRedirect(contextpath+"/login?logout="+usuario.getDesc_bloq());
            return;
            //eliminar el usuario de la sesion

        } */

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            switch (authority.getAuthority()) {
                case "ROLE_CLIENTE":
                    response.sendRedirect(contextpath+"/clientes/");
                    break;
                case "ROLE_Administrador":
                    response.sendRedirect(contextpath+"/administrador/dashboard");
                    break;
                case "ROLE_Entrenador":
                    response.sendRedirect(contextpath+"/entrenador/");
                    break;
                default:
                    break;
            }
        }

        /* if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            response.sendRedirect(contextpath+"/clientes");
        }else if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))){
            response.sendRedirect(contextpath+"/clientes/home");
        }else{
            response.sendRedirect(contextpath+"/login");
        } */

    }

}
