package sptech.school.BACK_END_JAVA.pagamento.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;
import sptech.school.BACK_END_JAVA.pagamento.entity.dto.request.PagamentoRequestDto;
import sptech.school.BACK_END_JAVA.pagamento.entity.dto.request.PagamentoUpdateDto;
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

    public Pagamento criar(PagamentoRequestDto dto, UUID agendamentoId) {
        var agendamento = agendamentoService.buscarPorId(agendamentoId);
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.getValor());
        pagamento.setMetodo(dto.getMetodo());
        pagamento.setStatus(dto.getStatus());
        pagamento.setData(dto.getData());
        pagamento.setAgendamento(agendamento);
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizar(UUID id, PagamentoUpdateDto dto) {
        Pagamento pagamento = buscarPorId(id);
        pagamento.setValor(dto.getValor());
        pagamento.setMetodo(dto.getMetodo());
        pagamento.setStatus(dto.getStatus());
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
