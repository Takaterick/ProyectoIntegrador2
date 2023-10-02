package com.proyecto.gym.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.gym.models.entity.Cliente;
import com.proyecto.gym.models.entity.Usuario;
import com.proyecto.gym.models.repository.ClienteRepository;
import com.proyecto.gym.models.repository.UsuarioRepository;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public List<Cliente> listarTodos() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null); //si no exite el id de cliente que se busca, envía nulo
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente, Long id) {
        //buscar el cliente con el id recibido
        Cliente clienteModificado = clienteRepository.findById(id).orElse(null);

        Usuario usuarioModificado = usuarioRepository.findById(clienteModificado.getUsuario().getId()).orElse(null);
        usuarioModificado.setUsuario(cliente.getUsuario().getUsuario());
        usuarioModificado.setContrasenia(cliente.getUsuario().getContrasenia());
        clienteModificado.setUsuario(usuarioModificado);

        clienteModificado.setNom_cli(cliente.getNom_cli());
        clienteModificado.setApe_cli(cliente.getApe_cli());
        clienteModificado.setDni_cli(cliente.getDni_cli());
        clienteModificado.setTel_cli(cliente.getTel_cli());
        clienteModificado.setCorreo_cli(cliente.getCorreo_cli());
        clienteModificado.setDir_cli(cliente.getDir_cli());

        clienteRepository.save(clienteModificado);

        return clienteModificado;
    }

    @Override
    public Boolean eliminarDos(Long id) {
        try {
            clienteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
