package sptech.school.BACK_END_JAVA.agendamento.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;

@Service
@RequiredArgsConstructor
public class AgendamentoClienteStrategy implements AgendamentoStrategy {

    private final ClienteRepository clienteRepository;

    @Override
    public void aplicar(Agendamento agendamento, AgendamentoRequestDto dto) {

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        agendamento.setCliente(cliente);
        agendamento.setNomeClienteAvulso(null);
        agendamento.setTelefoneClienteAvulso(null);
    }
}