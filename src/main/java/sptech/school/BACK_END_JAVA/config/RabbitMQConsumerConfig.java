package sptech.school.BACK_END_JAVA.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

    public static final String FILA_ATUALIZAR_AGENDAMENTO = "agendamento.atualizar_status.queue";
    public static final String EXCHANGE_NAME = "pagamento.exchange";

    @Bean
    public Queue atualizarAgendamentoQueue() {
        return new Queue(FILA_ATUALIZAR_AGENDAMENTO, true);
    }

    @Bean
    public TopicExchange pagamentoExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingAtualizarAgendamento(Queue atualizarAgendamentoQueue, TopicExchange pagamentoExchange) {
        return BindingBuilder.bind(atualizarAgendamentoQueue).to(pagamentoExchange).with("pagamento.aprovado");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}