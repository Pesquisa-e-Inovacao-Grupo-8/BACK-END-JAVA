package sptech.school.BACK_END_JAVA.agendamento.strategy;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;

@Service
public class AgendamentoAvulsoStrategy implements AgendamentoStrategy {

    @Override
    public void aplicar(Agendamento agendamento, AgendamentoRequestDto dto) {

        agendamento.setCliente(null);
        agendamento.setNomeClienteAvulso(dto.getNomeClienteAvulso());
        agendamento.setTelefoneClienteAvulso(dto.getTelefoneClienteAvulso());
    }
}