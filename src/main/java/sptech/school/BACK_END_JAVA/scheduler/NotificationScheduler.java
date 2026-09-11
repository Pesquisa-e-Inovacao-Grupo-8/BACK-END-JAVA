package sptech.school.BACK_END_JAVA.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;

import java.time.LocalDate;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

@Service
public class NotificationScheduler {

    private final WebClient webClient;
    private final AgendamentoService agendamentoService;

    public NotificationScheduler(WebClient webClient, AgendamentoService agendamentoService) {
        this.webClient = webClient;
        this.agendamentoService = agendamentoService;
    }


   @Scheduled(cron = "*/60 * * * * *")
   public void enviarNotificacao() {

       System.out.println("ENVIANDO!");
       LocalDate alvo = LocalDate.now().plusDays(1);
       List<Agendamento> agendamentos = agendamentoService.consultarPorData(alvo);

       agendamentos.forEach(agendamento -> {

           Map<String, Object> body = new HashMap<>();

           body.put("telefone", agendamento.getCliente().getUsuario().getTelefone());
           body.put("cliente", agendamento.getCliente().getUsuario().getNome());
           body.put("servico", agendamento.getServico().getNome());
           body.put("data", agendamento.getData().toString());
           body.put("horaInicio", agendamento.getHoraInicio().toString());
           body.put("ordemPedido", agendamento.getOrdemPedido());

           webClient.post()
                   .uri("http://api-twillio:8090/notify/agendamento")
                   .contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(body)
                   .retrieve()
                   .bodyToMono(String.class)
                   .subscribe();
       });
   }
}