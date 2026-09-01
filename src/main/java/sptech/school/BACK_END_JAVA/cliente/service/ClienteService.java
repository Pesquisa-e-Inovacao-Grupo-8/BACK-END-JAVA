package sptech.school.BACK_END_JAVA.cliente.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Cliente> listar() {return clienteRepository.findAll();}

    public Cliente buscarPorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente criar(Cliente cliente, UUID usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        cliente.setUsuario(usuario);

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(UUID id, Cliente cliente) {

        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }

        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void deletar(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }

        clienteRepository.deleteById(id);
    }

    //restante das funções


}
