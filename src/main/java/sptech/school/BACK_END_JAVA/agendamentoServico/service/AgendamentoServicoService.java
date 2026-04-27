package sptech.school.BACK_END_JAVA.agendamentoServico.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.repository.AgendamentoServicoRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AgendamentoServicoService {

    private final AgendamentoServicoRepository agendamentoServicoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoServicoService(AgendamentoServicoRepository agendamentoServicoRepository, AgendamentoRepository agendamentoRepository, ServicoRepository servicoRepository) {
        this.agendamentoServicoRepository = agendamentoServicoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.servicoRepository = servicoRepository;
    }

    public List<AgendamentoServico> listar() {return agendamentoServicoRepository.findAll();}

    public AgendamentoServico buscarPorId(UUID id) {
        return agendamentoServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AgendamentoServico não encontrado"));
    }

    public AgendamentoServico criar(UUID agendamentoId, UUID servicoId) {

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        AgendamentoServico novo = new AgendamentoServico();
        novo.setAgendamento(agendamento);
        novo.setServico(servico);

        return agendamentoServicoRepository.save(novo);
    }

    public AgendamentoServico atualizar(UUID id, UUID agendamentoId, UUID servicoId) {

        if (!agendamentoServicoRepository.existsById(id)) {
            throw new RuntimeException("AgendamentoServico não encontrado");
        }

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        AgendamentoServico atualizado = new AgendamentoServico();
        atualizado.setId(id);
        atualizado.setAgendamento(agendamento);
        atualizado.setServico(servico);

        return agendamentoServicoRepository.save(atualizado);
    }

    public void deletar(UUID id) {
        if (!agendamentoServicoRepository.existsById(id)) {
            throw new RuntimeException("AgendamentoServico não encontrado");
        }
        agendamentoServicoRepository.deleteById(id);
    }
}