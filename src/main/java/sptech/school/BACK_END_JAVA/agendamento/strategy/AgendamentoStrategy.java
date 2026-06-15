package sptech.school.BACK_END_JAVA.agendamento.strategy;

import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;

public interface AgendamentoStrategy {

    void aplicar(Agendamento agendamento, AgendamentoRequestDto dto);

}