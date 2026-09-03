package sptech.school.BACK_END_JAVA.agendamento.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.agendamento.strategy.AgendamentoStrategy;
import sptech.school.BACK_END_JAVA.agendamento.strategy.AgendamentoStrategyFactory;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.repository.AgendamentoServicoRepository;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;

import java.time.LocalDate;
import java.util.List; 
import java.util.UUID;

@Service
public class AgendamentoService {
    private static final Logger logger = LoggerFactory.getLogger(AgendamentoService.class);

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoServicoRepository agendamentoServicoRepository;
    private final AgendamentoStrategyFactory factory;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository, ServicoRepository servicoRepository, AgendamentoServicoRepository agendamentoServicoRepository, AgendamentoStrategyFactory factory) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.servicoRepository = servicoRepository;
        this.agendamentoServicoRepository = agendamentoServicoRepository;
        this.factory = factory;
    }

    public List<Agendamento> listar() {return agendamentoRepository.findAll();}

    public Agendamento buscarPorId(UUID id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    @Transactional
    public Agendamento criar(AgendamentoRequestDto dto) {

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        if ((dto.getServicos() == null || dto.getServicos().isEmpty())
                && dto.getServicoId() != null) {
            dto.setServicos(List.of(dto.getServicoId()));
        }

        if (dto.getServicos() == null || dto.getServicos().isEmpty()) {
            throw new RuntimeException("Informe ao menos um serviço");
        }

        tentarVincularClienteCadastrado(dto);

        logger.info("usuarioId recebido: {}", dto.getUsuarioId());
        logger.info("clienteId após vinculação: {}", dto.getClienteId());

        Agendamento agendamento = new Agendamento();

        agendamento.setData(dto.getData());
        agendamento.setHoraInicio(dto.getHoraInicio());
        agendamento.setHoraFim(dto.getHoraFim());
        agendamento.setStatus(dto.getStatus());
        agendamento.setProfissional(profissional);
        agendamento.setOrdemPedido(UUID.randomUUID().toString());
        agendamento.setValorTotal(0.0);

        AgendamentoStrategy strategy = factory.escolher(dto);
        strategy.aplicar(agendamento, dto);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        Double valorTotal = 0.0;
        for (UUID servicoId : dto.getServicos()) {

            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + servicoId));

            valorTotal += servico.getPreco();

            AgendamentoServico agendamentoServico = new AgendamentoServico();
            agendamentoServico.setAgendamento(agendamentoSalvo);
            agendamentoServico.setServico(servico);

            agendamentoServicoRepository.save(agendamentoServico);
        }

        agendamentoSalvo.setValorTotal(valorTotal);
        agendamentoRepository.save(agendamentoSalvo);

        return agendamentoSalvo;
    }

    public Agendamento atualizar(UUID id, Agendamento agendamento) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        agendamento.setId(id);
        return agendamentoRepository.save(agendamento);
    }

    public void deletar(UUID id) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        agendamentoRepository.deleteById(id);
    }

    private void tentarVincularClienteCadastrado(AgendamentoRequestDto dto) {




            // Cliente já informado pelo frontend
            if (dto.getClienteId() != null) {
                return;
            }

            // Tenta encontrar o cliente através do usuário
            if (dto.getUsuarioId() != null) {

                clienteRepository.findByUsuario_Id(dto.getUsuarioId())
                        .ifPresent(cliente -> dto.setClienteId(cliente.getId()));

                if (dto.getClienteId() != null) {
                    return;
                }
            }

            // Tenta encontrar pelo telefone
            if (dto.getTelefoneClienteAvulso() != null &&
                    !dto.getTelefoneClienteAvulso().isBlank()) {

                String telefone = dto.getTelefoneClienteAvulso().trim();

                clienteRepository.findByUsuario_Telefone(telefone)
                        .ifPresent(cliente -> dto.setClienteId(cliente.getId()));
        }
    }




    //restante das funções
}
