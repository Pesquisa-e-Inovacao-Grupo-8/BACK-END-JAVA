package sptech.school.BACK_END_JAVA.agendamento.strategy;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;

@Service
public class AgendamentoStrategyFactory {

    private final AgendamentoClienteStrategy comCliente;
    private final AgendamentoAvulsoStrategy avulso;

    public AgendamentoStrategyFactory(
            AgendamentoClienteStrategy comCliente,
            AgendamentoAvulsoStrategy avulso
    ) {
        this.comCliente = comCliente;
        this.avulso = avulso;
    }

    public AgendamentoStrategy escolher(AgendamentoRequestDto dto) {

        if (dto.getClienteId() != null) {
            return comCliente;
        }

        return avulso;
    }
}