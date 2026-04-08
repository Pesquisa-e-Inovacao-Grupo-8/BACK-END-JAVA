package sptech.school.BACK_END_JAVA.clientePacote.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;
import sptech.school.BACK_END_JAVA.clientePacote.repository.ClientePacoteRepository;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.pacote.repository.PacoteRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ClientePacoteService {
    private final ClientePacoteRepository clientePacoteRepository;
    private final ClienteRepository clienteRepository;
    private final PacoteRepository pacoteRepository;

    public ClientePacoteService(ClientePacoteRepository clientePacoteRepository, ClienteRepository clienteRepository, PacoteRepository pacoteRepository) {
        this.clientePacoteRepository = clientePacoteRepository;
        this.clienteRepository = clienteRepository;
        this.pacoteRepository = pacoteRepository;
    }

    public List<ClientePacote> listar() {return clientePacoteRepository.findAll();}

    public ClientePacote buscarPorId(UUID id) {
        return clientePacoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientePacote não encontrado"));
    }

    public ClientePacote criar(ClientePacote clientePacote, UUID clienteId, UUID pacoteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        clientePacote.setCliente(cliente);
        clientePacote.setPacote(pacote);

        return clientePacoteRepository.save(clientePacote);
    }

    public ClientePacote atualizar(UUID id, ClientePacote clientePacote) {

        if (!clientePacoteRepository.existsById(id)) {
            throw new RuntimeException("ClientePacote não encontrado");
        }

        clientePacote.setId(id);
        return clientePacoteRepository.save(clientePacote);
    }

    public void deletar(UUID id) {

        if (!clientePacoteRepository.existsById(id)) {
            throw new RuntimeException("ClientePacote não encontrado");
        }

        clientePacoteRepository.deleteById(id);
    }
    //restante das funções
}
