package sptech.school.BACK_END_JAVA.pagamento.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;
import sptech.school.BACK_END_JAVA.pagamento.repository.PagamentoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final AgendamentoService agendamentoService;

    public PagamentoService(PagamentoRepository pagamentoRepository, AgendamentoService agendamentoService) {
        this.pagamentoRepository = pagamentoRepository;
        this.agendamentoService = agendamentoService;
    }

    public List<Pagamento> listar() {return pagamentoRepository.findAll();}

    public Pagamento buscarPorId(UUID id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public Pagamento criar(Pagamento pagamento, UUID agendamentoId) {
        var agendamento = agendamentoService.buscarPorId(agendamentoId);
        pagamento.setAgendamento(agendamento);
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizar(UUID id, Pagamento pagamento) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        pagamento.setId(id);
        return pagamentoRepository.save(pagamento);
    }

    public void deletar(UUID id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        pagamentoRepository.deleteById(id);
    }
    //restante das funções
}
