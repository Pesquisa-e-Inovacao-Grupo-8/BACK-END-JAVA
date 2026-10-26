package sptech.school.BACK_END_JAVA.agendamento.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.config.RabbitMQConsumerConfig;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PagamentoAprovadoListener {

    private final AgendamentoRepository agendamentoRepository;

    @RabbitListener(queues = RabbitMQConsumerConfig.FILA_ATUALIZAR_AGENDAMENTO)
    @Transactional
    public void receberPagamentoAprovado(Map<String, String> evento) {
        String agendamentoIdStr = evento.get("agendamentoId");

        try {
            UUID agendamentoId = UUID.fromString(agendamentoIdStr);

            agendamentoRepository.findById(agendamentoId).ifPresent(agendamento -> {
                // Idempotência: só atualiza se ainda não estiver pago
                if (!"PAGO".equals(agendamento.getStatus())) {
                    agendamento.setStatus("PAGO");
                    agendamentoRepository.save(agendamento);
                    System.out.println("Agendamento " + agendamentoId + " atualizado para PAGO via RabbitMQ!");
                }
            });

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem do RabbitMQ: " + e.getMessage());
            // tem que jogar essa mensagem para uma DLQ (Dead Letter Queue)
            throw e;
        }
    }
}