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

        // Tenta resolver o cliente considerando que dto.getClienteId() pode
        // ser o id do cliente (id_cliente) ou, por engano, o id do usuário (id_usuario).
        Cliente cliente = null;

        if (dto.getClienteId() != null) {
            // 1) tenta buscar por id_cliente
            cliente = clienteRepository.findById(dto.getClienteId()).orElse(null);

            // 2) se não encontrou, tenta buscar por fk_usuario (tratando o id recebido como usuarioId)
            if (cliente == null) {
                cliente = clienteRepository.findByUsuario_Id(dto.getClienteId()).orElse(null);
            }
        }

        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        agendamento.setCliente(cliente);
        agendamento.setNomeClienteAvulso(null);
        agendamento.setTelefoneClienteAvulso(null);
    }
}