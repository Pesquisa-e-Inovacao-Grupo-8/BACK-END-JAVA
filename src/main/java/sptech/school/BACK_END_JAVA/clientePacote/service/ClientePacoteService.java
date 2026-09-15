package sptech.school.BACK_END_JAVA.clientePacote.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;
import sptech.school.BACK_END_JAVA.clientePacote.entity.dto.request.ClientePacoteRequestDto;
import sptech.school.BACK_END_JAVA.clientePacote.entity.dto.request.ClientePacoteUpdateDto;
import sptech.school.BACK_END_JAVA.clientePacote.entity.dto.response.ClientePacoteResponseDto;
import sptech.school.BACK_END_JAVA.clientePacote.repository.ClientePacoteRepository;
import sptech.school.BACK_END_JAVA.clientePacoteServico.entity.ClientePacoteServico;
import sptech.school.BACK_END_JAVA.clientePacoteServico.repository.ClientePacoteServicoRepository;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.pacote.repository.PacoteRepository;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.PacoteServico;
import sptech.school.BACK_END_JAVA.pacoteServico.repository.PacoteServicoRepository;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class ClientePacoteService {
    private final ClientePacoteRepository clientePacoteRepository;
    private final ClienteRepository clienteRepository;
    private final PacoteRepository pacoteRepository;
    private final PacoteServicoRepository pacoteServicoRepository;
    private final ClientePacoteServicoRepository clientePacoteServicoRepository;

    public ClientePacoteService(ClientePacoteRepository clientePacoteRepository, ClienteRepository clienteRepository, PacoteRepository pacoteRepository, PacoteServicoRepository pacoteServicoRepository, ClientePacoteServicoRepository clientePacoteServicoRepository) {
        this.clientePacoteRepository = clientePacoteRepository;
        this.clienteRepository = clienteRepository;
        this.pacoteRepository = pacoteRepository;
        this.pacoteServicoRepository = pacoteServicoRepository;
        this.clientePacoteServicoRepository = clientePacoteServicoRepository;
    }

    public List<ClientePacote> listar() {return clientePacoteRepository.findAll();}

        public List<ClientePacoteResponseDto> listarPorUsuario(UUID usuarioId) {
        return clientePacoteRepository.findByCliente_Usuario_Id(usuarioId).stream()
            .map(clientePacote -> {
                boolean valido = Boolean.TRUE.equals(clientePacote.getAtivo())
                        && clientePacote.getDtExpiracao() != null
                        && clientePacote.getDtExpiracao().isAfter(LocalDateTime.now());
                return new ClientePacoteResponseDto(
                    clientePacote.getId(),
                    clientePacote.getPacote().getNome(),
                    clientePacote.getPacote().getDescricao(),
                    valido,
                    clientePacote.getDtExpiracao(),
                    clientePacoteServicoRepository.findByClientePacote(clientePacote).stream()
                        .map(vinculo -> new ClientePacoteResponseDto.ServicoPacoteResponseDto(
                            vinculo.getServico().getId(),
                            vinculo.getServico().getNome(),
                            vinculo.getServico().getDuracaoMinutos(),
                            vinculo.getQuantidadeDisponivel(),
                            vinculo.getQuantidadeTotal(),
                            vinculo.getId()))
                        .toList());
            })
            .toList();
    }

    public ClientePacote buscarPorId(UUID id) {
        return clientePacoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientePacote não encontrado"));
    }

    public ClientePacote criar(
            ClientePacoteRequestDto dto,
            UUID clienteId,
            UUID pacoteId) {

        ClientePacote clientePacote = new ClientePacote();
        clientePacote.setAtivo(true);
        clientePacote.setDtExpiracao(LocalDateTime.now().plusDays(30));

        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseGet(() -> clienteRepository.findByUsuario_Id(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        clientePacote.setCliente(cliente);
        clientePacote.setPacote(pacote);

        ClientePacote clientePacoteSalvo =
                clientePacoteRepository.save(clientePacote);

        List<PacoteServico> pacoteServicos =
                pacoteServicoRepository.findByPacote(pacote);

        for (PacoteServico pacoteServico : pacoteServicos) {

            ClientePacoteServico clientePacoteServico =
                    new ClientePacoteServico();

            clientePacoteServico.setClientePacote(clientePacoteSalvo);

            clientePacoteServico.setServico(
                    pacoteServico.getServico()
            );
                clientePacoteServico.setQuantidadeDisponivel(pacoteServico.getQuantidade());
                clientePacoteServico.setQuantidadeTotal(pacoteServico.getQuantidade());

            clientePacoteServicoRepository.save(
                    clientePacoteServico
            );
        }

        return clientePacoteSalvo;
    }
    public ClientePacote atualizar(UUID id, ClientePacoteUpdateDto dto) {
        ClientePacote clientePacote = buscarPorId(id);
        clientePacote.setAtivo(dto.getAtivo());
        clientePacote.setDtExpiracao(dto.getDtExpiracao());
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
