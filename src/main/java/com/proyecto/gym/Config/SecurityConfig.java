package com.proyecto.gym.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private SuccessRedirect successRedirect;

    @Autowired
    private FailureRedirect failureRedirect;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        return http
            .csrf(csrf ->
                csrf
                .disable()
            )
            .userDetailsService(userService)
            .authorizeHttpRequests(authRequest ->
                authRequest
                    .antMatchers("/clientes/**").hasRole("CLIENTE")
                    .antMatchers("/administrador/**").hasRole("Administrador")
                    .antMatchers("/entrenador/**").hasRole("Entrenador")
                    .antMatchers("/assets/**").permitAll()
                    .antMatchers("/login", "/registro", "/inicio").permitAll()
                    .antMatchers("/api/v1/suscripciones/guardar").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(
                formLogin ->
                    formLogin
                        .loginPage("/login")
                        .successHandler(successRedirect)
                        .failureHandler(failureRedirect)
                        .permitAll()
            )
            .logout(
                logout ->
                    logout
                        //eliminar jsesion
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
            )
            .build();
    }
}