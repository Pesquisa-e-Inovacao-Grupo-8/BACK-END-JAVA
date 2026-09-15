package sptech.school.BACK_END_JAVA.agendamento.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentLinkService {

    private final AgendamentoRepository agendamentoRepository;
    private final WebClient webClient;
    private final String paymentApiUrl;

    public PaymentLinkService(
            AgendamentoRepository agendamentoRepository,
            WebClient webClient,
            @Value("${payment.api.url:http://localhost:8088}") String paymentApiUrl) {
        this.agendamentoRepository = agendamentoRepository;
        this.webClient = webClient;
        this.paymentApiUrl = paymentApiUrl;
    }

    @Transactional
    public synchronized String gerarOuRecuperar(UUID agendamentoId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        if (agendamento.getLinkPagamento() != null && !agendamento.getLinkPagamento().isBlank()) {
            return agendamento.getLinkPagamento();
        }

        CheckoutResponse checkout = webClient.post()
                .uri(paymentApiUrl + "/flask-infinity-pay/create-checkout")
                .bodyValue(Map.of("id", agendamentoId.toString()))
                .retrieve()
                .bodyToMono(CheckoutResponse.class)
                .block();

        if (checkout == null || checkout.url() == null || checkout.url().isBlank()) {
            throw new IllegalStateException("A API de pagamento não retornou um link válido");
        }

        agendamento.setLinkPagamento(checkout.url());
        agendamentoRepository.save(agendamento);
        return checkout.url();
    }

    private record CheckoutResponse(String orderNsu, String url) { }
}