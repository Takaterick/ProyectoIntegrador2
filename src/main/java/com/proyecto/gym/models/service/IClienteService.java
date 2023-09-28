package com.proyecto.gym.models.service;

import java.util.List;

import com.proyecto.gym.models.entity.Cliente;

public interface IClienteService {
    //metodo que lista
    public List<Cliente> listarTodos();
    //metodo que guarda
    public Cliente guardarCliente(Cliente cliente);
    //metodo que busca por id
    public Cliente buscarPorId(Long id);
    //metodo que elimina
    public void eliminarCliente(Long id);
    
    //metodo que actualiza
    public Cliente actualizarCliente(Cliente cliente, Long id);
    //metodo que elimina
    public Boolean eliminarDos(Long id);
}
