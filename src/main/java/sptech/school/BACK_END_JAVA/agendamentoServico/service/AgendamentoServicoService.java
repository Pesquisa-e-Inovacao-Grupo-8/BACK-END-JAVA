package sptech.school.BACK_END_JAVA.agendamentoServico.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.repository.AgendamentoServicoRepository;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AgendamentoServicoService {

    private final AgendamentoServicoRepository agendamentoServicoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final ServicoRepository servicoRepository;
    private final WebClient webClient;

    public AgendamentoServicoService(AgendamentoServicoRepository agendamentoServicoRepository, AgendamentoRepository agendamentoRepository, ServicoRepository servicoRepository, WebClient webClient) {
        this.agendamentoServicoRepository = agendamentoServicoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.servicoRepository = servicoRepository;
        this.webClient = webClient;
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

        enviarConfirmacaoAgendamento(agendamento,servico);

        AgendamentoServico novo = new AgendamentoServico();
        novo.setAgendamento(agendamento);
        novo.setServico(servico);

        return agendamentoServicoRepository.save(novo);
    }

    public void enviarConfirmacaoAgendamento(Agendamento agendamento , Servico servico){

        System.out.println("ENVIANDO CONFIMRAÇÃO");

        Map<String, Object> payload = new HashMap<>();

        payload.put("telefone", agendamento.getCliente().getUsuario().getTelefone());
        payload.put("cliente", agendamento.getCliente().getUsuario().getNome());
        payload.put("servico" , servico);
        payload.put("data", agendamento.getData().toString());
        payload.put("horaInicio", agendamento.getHoraInicio().toString());
        payload.put("ordemPedido", agendamento.getOrdemPedido());

        webClient.post()
                .uri("http://localhost:5000/notify/agendamento")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
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